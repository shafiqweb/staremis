package com.example.shafiq.staremis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class DashBoardActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //    private String[] imageNameList = new String[]{"Fees", "Results", "Attendance", "Student Info", "Class Routine", "Notices",
//            "Lesson Plan", "SMS"};
    private String[] imageNameList = new String[]{};

    private String[] adminDashboardList = new String[]{"Fees", "Results", "Attendance", "Student Info", "Class Routine", "Notices", "Lesson Plan", "SMS"};
    private String[] teacherDashboardList = new String[]{"Attendance", "Student Info", "Class Routine", "Notices", "Lesson Plan"};
    private String[] studentDashboardList = new String[]{"Fees", "Results", "Attendance", "Student Info", "Class Routine", "Notices", "Lesson Plan", "SMS"};
    private int[] imageList = new int[]{R.drawable.fees, R.drawable.results, R.drawable.attendance, R.drawable.student_info,
            R.drawable.routine, R.drawable.notice, R.drawable.logo_star_soft, R.drawable.sms};
    private int[] colourList = new int[]{R.drawable.background_grid_view_gradient_colour, R.drawable.background_grid_view_gradient_colour_one,
            R.drawable.background_grid_view_gradient_colour_two, R.drawable.background_grid_view_gradient_colour_three,
            R.drawable.background_grid_view_gradient_colour_four, R.drawable.background_grid_view_gradient_colour_five,
            R.drawable.background_grid_view_gradient_colour_six, R.drawable.background_grid_view_gradient_colour_seven};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Toolbar toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ConnectionServer connectionServer = new ConnectionServer();
        if (connectionServer.userType == "1") {
            imageNameList = adminDashboardList;
        } else if (connectionServer.userType == "2") {
            imageNameList = teacherDashboardList;
        } else {
            imageNameList = studentDashboardList;
        }
//        ProgressDialog progress = ProgressDialog.show(this, "dialog title",
//                "dialog message", true);
//        progress.dismiss();

        GridView gridView = findViewById(R.id.gridViewID);
        gridView.setOnItemClickListener(this);
        CustomAdapter customAdapter = new CustomAdapter(this, colourList, imageList, imageNameList);
        gridView.setAdapter(customAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 2) {
            Intent intent = new Intent(DashBoardActivity.this, AttendanceActivity.class);
            startActivity(intent);
        }else if (position == 1){
            Intent intent = new Intent(DashBoardActivity.this, ResultActivity.class);
            startActivity(intent);
        }
    }
}
