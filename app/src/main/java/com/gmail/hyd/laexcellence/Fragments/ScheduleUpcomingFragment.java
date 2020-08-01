package com.gmail.hyd.laexcellence.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.gmail.hyd.laexcellence.Models.SchedulesModel.SchedulesModel;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;
import com.gmail.hyd.laexcellence.tableview.TableViewAdapter;
import com.gmail.hyd.laexcellence.tableview.model.Cell;
import com.gmail.hyd.laexcellence.tableview.model.ColumnHeader;
import com.gmail.hyd.laexcellence.tableview.model.RowHeader;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleUpcomingFragment extends Fragment {

    public int COLUMN_SIZE;
    public int ROW_SIZE;

    private List<RowHeader> m_jRowHeaderList;
    private List<ColumnHeader> m_jColumnHeaderList;
    private List<List<Cell>> m_jCellList;
    private String sid;


    ArrayList date = new ArrayList();
    ArrayList time = new ArrayList();
    ArrayList subject = new ArrayList();
    ArrayList faculty = new ArrayList();
    ArrayList location = new ArrayList();
    ArrayList hall = new ArrayList();


    private AbstractTableAdapter m_iTableViewAdapter;
    private TableView tableView2;
    private TextView textView;


    public ScheduleUpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSchedule();
        //setFullScreenMode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_upcoming, container, false);

        tableView2 = view.findViewById(R.id.content_container);
        textView = view.findViewById(R.id.msg);


        m_iTableViewAdapter = new TableViewAdapter(getContext());
        initData();
        loadData();

        tableView2.setAdapter(m_iTableViewAdapter);


        return view;
    }


    private void getSchedule() {

        sid = SharedPrefManager.get_mInstance(getContext()).getSid();


        Call<SchedulesModel> schedulesModelCall = RetrofitClient.getmInstance().getApi().get_schedule(sid);

        schedulesModelCall.enqueue(new Callback<SchedulesModel>() {
            @Override
            public void onResponse(Call<SchedulesModel> call, Response<SchedulesModel> response) {
                SchedulesModel schedulesModel = response.body();

                if (schedulesModel != null) {
                    if (schedulesModel.getUpcoming() != null) {

                        for (int i = 0; i < schedulesModel.getUpcoming().size(); i++) {
                            date.add(schedulesModel.getUpcoming().get(i).getScheduleDate());
                            time.add(schedulesModel.getUpcoming().get(i).getSlots());
                            subject.add(schedulesModel.getUpcoming().get(i).getSubject());
                            faculty.add(schedulesModel.getUpcoming().get(i).getFaculty());
                            location.add(schedulesModel.getUpcoming().get(i).getBuilding());
                            hall.add(schedulesModel.getUpcoming().get(i).getHall());
                        }


                        COLUMN_SIZE = getColumnHeaderList().size();
                        ROW_SIZE = date.size();
                        initData();

                        loadData();

                    } else {
                        tableView2.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void onFailure(Call<SchedulesModel> call, Throwable t) {

            }
        });
    }


    private void initData() {
        m_jRowHeaderList = new ArrayList<>();
        m_jColumnHeaderList = new ArrayList<>();
        m_jCellList = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            m_jCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadData() {
        List<RowHeader> rowHeaders = getRowHeaderList();
        List<List<Cell>> cellList = getCellListForSorting(); // getCellList();
        // getRandomCellList(); //
        List<ColumnHeader> columnHeaders = getColumnHeaderList(); //getRandomColumnHeaderList(); //

        m_jRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            m_jCellList.get(i).addAll(cellList.get(i));
        }

        // Load all data
        m_jColumnHeaderList.addAll(columnHeaders);
        m_iTableViewAdapter.setAllItems(m_jColumnHeaderList, m_jRowHeaderList, m_jCellList);

    }

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), "" + (i + 1));
            list.add(header);
        }

        return list;
    }

    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();
        list.add(new ColumnHeader("0", "Date"));
        list.add(new ColumnHeader("1", "time"));
        list.add(new ColumnHeader("2", "subject"));
        list.add(new ColumnHeader("3", "faculty"));
        list.add(new ColumnHeader("4", "location"));
        list.add(new ColumnHeader("5", "hall"));

        return list;
    }


    private List<List<Cell>> getCellListForSorting() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object strText = "";
                if (j == 0) {
                    strText = date.get(i);
                }
                if (j == 1) {
                    strText = time.get(i);
                }
                if (j == 2) {
                    strText = subject.get(i);
                }
                if (j == 3) {
                    strText = faculty.get(i);
                }

                if (j == 4) {
                    strText = location.get(i);
                }

                if (j == 5) {
                    strText = hall.get(i);
                }


                String strID = j + "-" + i;

                Cell cell = new Cell(strID, strText);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }


}
