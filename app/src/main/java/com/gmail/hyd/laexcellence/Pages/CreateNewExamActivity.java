package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.ConstantManager;
import com.gmail.hyd.laexcellence.Models.CreateNewTestModel.CreateNewTestModel;
import com.gmail.hyd.laexcellence.Models.DataItem;
import com.gmail.hyd.laexcellence.Models.SubCategoryItem;
import com.gmail.hyd.laexcellence.Models.TestForms.TestForms;
import com.gmail.hyd.laexcellence.Models.TopicsListModel.TopicsListModel;
import com.gmail.hyd.laexcellence.MyCategoriesExpandableListAdapter;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;

import me.gujun.android.taggroup.TagGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewExamActivity extends AppCompatActivity {

    ArrayList<Integer> mUserItems = new ArrayList<>();
    HashMap<String,String> stringDatumHashMap = new HashMap<>();
    Button btnForms;
    TextView selectedForms;
    ArrayList<String> items = new ArrayList<>();
    String[] listItems;
    boolean[] checkedItems;
    String services_txt,sid;
    ArrayList<String> tags_arrayList = new ArrayList<>();
    HashMap<Integer,String> ans_map = new HashMap<>();
    ArrayList<String> all_answers = new ArrayList<>();

    DataItem dataItem;
    Integer tutorMode,timeMode;
    Switch tutorSwitch,timeSwitch;
    CheckBox checkbox_all;


    private Button create_new_test_btn;
    private ExpandableListView lvCategory;

    private ArrayList<DataItem> arCategory;
    private ArrayList<SubCategoryItem> arSubCategory;
    private ArrayList<ArrayList<SubCategoryItem>> arSubCategoryFinal;

    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;
    private MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter;
    private EditText no_questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_exam);

        services_txt = "";


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Create New Test");

        }



        tutorSwitch = findViewById(R.id.tutor_switch);
        timeSwitch = findViewById(R.id.timed_switch);
        checkbox_all = findViewById(R.id.checkbox_all);



        create_new_test_btn = findViewById(R.id.create_new_test_btn);
        no_questions = findViewById(R.id.no_questions);
        sid = SharedPrefManager.get_mInstance(getApplicationContext()).getSid();




        get_test_forms();
        setupReferences();
        create_new_test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTopicId();
            }
        });

        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_all.isChecked()){
                  lvCategory.setVisibility(View.GONE);
                }else {
                    lvCategory.setVisibility(View.VISIBLE);
                }
            }
        });


    }



    private void get_test_forms(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait");
        progressDialog.show();

        Call<TestForms> testFormsCall = RetrofitClient.getmInstance().getApi().get_test_forms();
        testFormsCall.enqueue(new Callback<TestForms>() {
            @Override
            public void onResponse(Call<TestForms> call, Response<TestForms> response) {
                progressDialog.dismiss();
                TestForms testForms = response.body();
                if (testForms != null){
                    for (int i = 0; i<testForms.getData().size(); i++){
                        items.add(testForms.getData().get(i).getTitle());
                        stringDatumHashMap.put(testForms.getData().get(i).getTitle(),testForms.getData().get(i).getTitle());
                    }
                    final TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
                    btnForms = findViewById(R.id.forms);

                    listItems = items.toArray(new String[0]);
                    checkedItems = new boolean[listItems.length];

                    btnForms.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreateNewExamActivity.this);
                            mBuilder.setTitle(R.string.dialog_title);
                            mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                    if (isChecked) {
                                        mUserItems.add(position);
                                    } else {
                                        mUserItems.remove((Integer.valueOf(position)));
                                    }
                                }
                            });

                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    tags_arrayList.clear();
                                    String item = "";
                                    for (int i = 0; i < mUserItems.size(); i++) {
                                        tags_arrayList.add(stringDatumHashMap.get(listItems[mUserItems.get(i)]));
                                        item = item + stringDatumHashMap.get(listItems[mUserItems.get(i)]);
                                        if (i != mUserItems.size() - 1) {
                                            item = item + ",";
                                        }
                                    }
                                    mTagGroup.setTags(tags_arrayList);
                                    services_txt = item;
                                }
                            });

                            mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    for (int i = 0; i < checkedItems.length; i++) {
                                        checkedItems[i] = false;
                                        mUserItems.clear();
                                        tags_arrayList.clear();
                                        mTagGroup.setTags(tags_arrayList);
                                        services_txt = "";
                                    }
                                }
                            });

                            AlertDialog mDialog = mBuilder.create();
                            mDialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<TestForms> call, Throwable t) {

            }
        });

    }




    private void setupReferences() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while loading");
        progressDialog.show();

        lvCategory = findViewById(R.id.lvCategory);
        arCategory = new ArrayList<>();
        arSubCategory = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();


        Call<TopicsListModel> topicsListModelCall  = RetrofitClient.getmInstance().getApi().get_subject_topic(sid);
        topicsListModelCall.enqueue(new Callback<TopicsListModel>() {
            @Override
            public void onResponse(Call<TopicsListModel> call, Response<TopicsListModel> response) {
                progressDialog.dismiss();
                TopicsListModel topicsListModel = response.body();
                if (topicsListModel != null){
                    int ans_id = 0;

                    for (int a=0;a<topicsListModel.getData().getSubjects().size();a++){
                        dataItem = new DataItem();
                        dataItem.setCategoryId(topicsListModel.getData().getSubjects().get(a).getSubjectId());
                        dataItem.setCategoryName(topicsListModel.getData().getSubjects().get(a).getSubjectTitle());
                        arSubCategory = new ArrayList<>();
                        for(int i = 0; i < topicsListModel.getData().getSubjectsTopics().size(); i++) {
                            all_answers.add(topicsListModel.getData().getSubjectsTopics().get(i).getTopicId());
                            if (topicsListModel.getData().getSubjects().get(a).getSubjectId().equals(topicsListModel.getData().getSubjectsTopics().get(i).getSubjectId())){
                                ans_id++;
                                ans_map.put(ans_id,topicsListModel.getData().getSubjectsTopics().get(i).getTopicId());
                                SubCategoryItem subCategoryItem = new SubCategoryItem();
                                subCategoryItem.setCategoryId(topicsListModel.getData().getSubjectsTopics().get(i).getTopicId()   );
                                subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                                subCategoryItem.setSubCategoryName(topicsListModel.getData().getSubjectsTopics().get(i).getTitle());
                                arSubCategory.add(subCategoryItem);
                            }
                        }
                        dataItem.setSubCategory(arSubCategory);
                        arCategory.add(dataItem);
                        Log.e("TAG", "setupReferences: "+dataItem.getCategoryName());

                    }
                    Log.e("TAG", "setupReferences: "+arCategory.size());

                    for(DataItem data : arCategory){
//                        Log.i("Item id",item.id);
                        ArrayList<HashMap<String, String>> childArrayList =new ArrayList<HashMap<String, String>>();
                        HashMap<String, String> mapParent = new HashMap<String, String>();

                        mapParent.put(ConstantManager.Parameter.CATEGORY_ID,data.getCategoryId());
                        mapParent.put(ConstantManager.Parameter.CATEGORY_NAME,data.getCategoryName());

                        int countIsChecked = 0;
                        for(SubCategoryItem subCategoryItem : data.getSubCategory()) {

                            HashMap<String, String> mapChild = new HashMap<String, String>();
                            mapChild.put(ConstantManager.Parameter.SUB_ID,subCategoryItem.getSubId());
                            mapChild.put(ConstantManager.Parameter.SUB_CATEGORY_NAME,subCategoryItem.getSubCategoryName());
                            mapChild.put(ConstantManager.Parameter.CATEGORY_ID,subCategoryItem.getCategoryId());
                            mapChild.put(ConstantManager.Parameter.IS_CHECKED,subCategoryItem.getIsChecked());

                            if(subCategoryItem.getIsChecked().equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                                countIsChecked++;
                            }
                            childArrayList.add(mapChild);
                        }

                        if(countIsChecked == data.getSubCategory().size()) {

                            data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                        }else {
                            data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                        }

                        mapParent.put(ConstantManager.Parameter.IS_CHECKED,data.getIsChecked());
                        childItems.add(childArrayList);
                        parentItems.add(mapParent);

                    }

                    ConstantManager.parentItems = parentItems;
                    ConstantManager.childItems = childItems;

                    myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapter(CreateNewExamActivity.this,parentItems,childItems,false);
                    lvCategory.setAdapter(myCategoriesExpandableListAdapter);
                    lvCategory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            setListViewHeight(parent,groupPosition);
                            return false;
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<TopicsListModel> call, Throwable t) {
               progressDialog.dismiss();
            }
        });
    }



    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    private void getTopicId(){
        StringBuilder sb = new StringBuilder();
        String prefix = "";


        if (checkbox_all.isChecked()) {
            for (int i = 0; i <all_answers.size(); i++ ){
                sb.append(prefix);
                prefix = ",";
                sb.append(all_answers.get(i));

            }
        }else {
            int ans_id = 0;
            for (int i = 0; i <parentItems.size(); i++ ){
                for (int j = 0; j < childItems.get(i).size(); j++ ){
                    ans_id++;
                    String isChildChecked = childItems.get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);
                    if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE))
                    {
                        sb.append(prefix);
                        prefix = ",";
                        sb.append(ans_map.get(ans_id));

                    }
                }
            }

        }

        if (tutorSwitch.isChecked()){
            tutorMode = 1;
        }else {
            tutorMode = 0;
        }

        if (timeSwitch.isChecked()){
            timeMode = 1;
        }else {
            timeMode = 0;
        }




        if (TextUtils.isEmpty(services_txt)){
            Toast.makeText(getApplicationContext(),"Choose the forms",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sb)){
            Toast.makeText(getApplicationContext(),"Choose the Topics",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(no_questions.getText().toString().trim())) {
            no_questions.setError("No. of Questions are Required");
            no_questions.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while Loading");
        progressDialog.show();

        Call<CreateNewTestModel> createNewTestModelCall = RetrofitClient.getmInstance().getApi().create_new_test(sid,sb.toString(),no_questions.getText().toString().trim(),services_txt,tutorMode+"",timeMode+"");
        createNewTestModelCall.enqueue(new Callback<CreateNewTestModel>() {
            @Override
            public void onResponse(Call<CreateNewTestModel> call, Response<CreateNewTestModel> response) {
                progressDialog.dismiss();
                CreateNewTestModel createNewTestModel = response.body();
                if (createNewTestModel != null){
                    if (createNewTestModel.getSuccess()){
                        Toast.makeText(getApplicationContext(),createNewTestModel.getMessage(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CreateNewExamActivity.this,QuestionBankActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),createNewTestModel.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"null response from the server",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CreateNewTestModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}
