package com.gmail.hyd.laexcellence.Pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.WelcomeGridAdapter;
import com.gmail.hyd.laexcellence.Models.DeviceToken.DeviceToken;
import com.gmail.hyd.laexcellence.Models.Version.Version;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView nav_textView;
    private ImageView nav_imageView;
    private String image, name, id_user_str, sid;

    TextView profile_name, user_id;
    Button buy_now;

    RecyclerView recyclerView;
    GridLayoutManager gridLayout;

    private String names[] = {
            "Tests", "QuestionBank", "Handouts", "Your Doubts", "Notifications",
            "Downloads", "Schedules", "Notice Board"
    };

    private int icon[] = {
            R.drawable.test, R.drawable.about_us, R.drawable.handouts, R.drawable.your_doubts, R.drawable.notification,
            R.drawable.download, R.drawable.schedule, R.drawable.noticeboard
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        checkVersion();

        buy_now = findViewById(R.id.shop_now);
        profile_name = findViewById(R.id.profile_name);
        user_id = findViewById(R.id.user_id);

        recyclerView = findViewById(R.id.recycleView);
        gridLayout = new GridLayoutManager(WelcomeActivity.this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayout);
        WelcomeGridAdapter welcomeGridAdapter = new WelcomeGridAdapter(getApplicationContext(), icon, names);
        recyclerView.setAdapter(welcomeGridAdapter);


        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.drawable.ic_launcher_background);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        image = SharedPrefManager.get_mInstance(this).getImage();
        name = SharedPrefManager.get_mInstance(this).getName();
        id_user_str = SharedPrefManager.get_mInstance(this).getAdmissionId();
        sid = SharedPrefManager.get_mInstance(this).getSid();

        testToken();


        drawerLayout = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        nav_textView = header.findViewById(R.id.nav_header_textView);
        nav_imageView = header.findViewById(R.id.nav_header_imageView);

        if (image != null) {
            Picasso.get().load(image).into(nav_imageView);
        }

        if (name != null) {
            nav_textView.setText(name);
            profile_name.setText(name);
        }

        if (id_user_str != null) {
            user_id.setText(id_user_str);
        }
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));


    }


    private void testToken() {

        if (SharedPrefManager.get_mInstance(getApplicationContext()).getDeviceToken() == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    testToken();
                    Log.e("tokrn", "token till not loaded");

                }
            }, 2000);
        } else {
            Log.e("tokrn", SharedPrefManager.get_mInstance(getApplicationContext()).getDeviceToken() + "");

            send_device_token(SharedPrefManager.get_mInstance(getApplicationContext()).getDeviceToken());
        }


    }


    private void send_device_token(String token) {
        Call<DeviceToken> deviceTokenCall = RetrofitClient.getmInstance().getApi().send_deviceToken(sid, token);

        deviceTokenCall.enqueue(new Callback<DeviceToken>() {
            @Override
            public void onResponse(Call<DeviceToken> call, Response<DeviceToken> response) {
                DeviceToken deviceToken = response.body();

                if (deviceToken != null) {
                    if (deviceToken.getSuccess()) {
                        Log.e("token", "token sent to server");
                    }
                } else {
                    Log.e("token", "error while token sent to server");
                }
            }

            @Override
            public void onFailure(Call<DeviceToken> call, Throwable t) {
                Log.e("token", "error while token sent to server");
            }
        });
    }


    private void checkVersion() {
        Call<Version> versionCall = RetrofitClient.getmInstance().getApi().check_version();

        versionCall.enqueue(new Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, Response<Version> response) {
                Version version = response.body();

                if (version != null) {
                    if (version.getSuccess()) {

                        if (version.getData() != null) {

                            String version_string = version.getData().getAndroidVersion() + "";

                            if (!version_string.equals("1.6")) {


                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WelcomeActivity.this);

                                // Setting Dialog Title
                                alertDialog.setTitle("Update Available");

                                // Setting Dialog Message
                                alertDialog.setMessage(version.getMessage() + "");

                                // Setting Icon to Dialog
                                //alertDialog.setIcon(R.drawable.ic_update_black_24dp);

                                // Setting Positive "Yes" Button
                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gmail.hyd.laexcellence"));
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Redirecting To PlayStore", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                // Setting Negative "NO" Button
                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to invoke NO event
                                        //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });

                                // Showing Alert Message
                                alertDialog.show();
                            }


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_item_one) {
            Intent intent = new Intent(WelcomeActivity.this, Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_item_two) {

            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gmail.hyd.laexcellence"));
            startActivity(i);


        } else if (id == R.id.nav_item_three) {
            Intent intent = new Intent(WelcomeActivity.this, AboutUsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_item_four) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://laex.in/privacy_policy.html"));
            startActivity(i);

        } else if (id == R.id.nav_item_payment) {
            logout();
        }


        /*
        this for new changesjj
        else if (id == R.id.nav_item_handouts) {
            Intent intent = new Intent(WelcomeActivity.this,Handouts.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else if (id == R.id.nav_item_downloads) {
            Intent intent = new Intent(WelcomeActivity.this,Downloads.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else if (id == R.id.nav_item_noticeboard) {
            Intent intent = new Intent(WelcomeActivity.this,NoticeBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else if (id == R.id.nav_item_attendance) {
            Intent intent = new Intent(WelcomeActivity.this,Attendance.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if (id == R.id.nav_item_schedules) {
            Intent intent = new Intent(WelcomeActivity.this,Schedules.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        else if (id == R.id.nav_item_doubts){
            Intent intent = new Intent(WelcomeActivity.this,UserdoubtsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else if (id == R.id.nav_item_notifications){
            Intent intent = new Intent(WelcomeActivity.this,NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    private void Toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    public void logout() {

        SharedPrefManager.get_mInstance(getApplicationContext()).clear();
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
