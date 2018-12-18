package com.example.shafiq.staremis;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttendanceActivity extends AppCompatActivity {
    List<AttendanceStudentInformation> attendanceStudentInformationList, studentInformationList;
    ArrayList<String> studentRoll = new ArrayList<>();
    ArrayList<String> absentRoll;
    ArrayList<String> currentDay = new ArrayList<>();
    String[] student_id_array = new String[100];

    String std_year, std_session, std_shift, std_class, std_section, std_shift_id,
            std_class_id, std_section_id, today, roll_no, absentRollString;
    Button buttonSms, buttonLoad;
    GridView gridView;
    AttendanceAdapter attendanceAdapter;
    TextView textViewYear, textViewSession, textViewShift, textViewClass, textViewSection;
    ProgressBar spinner;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        textViewYear = findViewById(R.id.yearTextViewID2);
        textViewSession = findViewById(R.id.sessionTextViewID2);
        textViewShift = findViewById(R.id.shiftTextViewID2);
        textViewClass = findViewById(R.id.classTextViewID2);
        textViewSection = findViewById(R.id.sectionTextViewID2);
        buttonSms = findViewById(R.id.sendSmsButtonID);
        buttonLoad = findViewById(R.id.loadButtonID);
        gridView = findViewById(R.id.gridViewID);
        spinner = findViewById(R.id.pBar);

        Toolbar toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<AttendanceStudentInformation>> call = api.getAttendanceStudentInformation();
        call.enqueue(new Callback<List<AttendanceStudentInformation>>() {
            @Override
            public void onResponse(Call<List<AttendanceStudentInformation>> call, Response<List<AttendanceStudentInformation>> response) {
                attendanceStudentInformationList = response.body();


                for (AttendanceStudentInformation information : attendanceStudentInformationList) {

                    studentRoll.add(information.getClass_roll_no());
                    std_year = information.getcYear();
                    std_session = information.getA_session();
                    std_shift = information.getShift_name();
                    std_class = information.getClass_name();
                    std_section = information.getSection_name();

                    std_shift_id = information.getShift_id();
                    std_class_id = information.getClass_id();
                    std_section_id = information.getSection_id();

                    roll_no = information.getClass_roll_no();
                    student_id_array[Integer.valueOf(roll_no)] = information.getStudent_ID();
                    if (information.getDay() == 13) {
                        currentDay.add(information.getD13().trim());
                    } else if (information.getDay() == 18) {
                        currentDay.add(information.getD13().trim());
                    }
                    //currentDay.add(information.getD13().trim());


                }
                textViewYear.setText(std_year);
                textViewSession.setText(std_session);
                textViewShift.setText(std_shift);
                textViewClass.setText(std_class);
                textViewSection.setText(std_section);

                attendanceAdapter = new AttendanceAdapter(getApplicationContext(), studentRoll, currentDay);
                gridView.setAdapter(attendanceAdapter);
                attendanceAdapter.notifyDataSetChanged();
                spinner.setVisibility(View.GONE); // to hide
            }

            @Override
            public void onFailure(Call<List<AttendanceStudentInformation>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this);

                builder.setTitle("Do you want to send SMS?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        absentRoll = new ArrayList<>();
                        for (int i = 0; i < attendanceAdapter.posArray.length; i++) {
                            if (attendanceAdapter.posArray[i] != null) {
                                if (attendanceAdapter.posArray[i].equals("1")) {
                                    absentRoll.add(student_id_array[i + 1]); //it's taking student ID
                                }
                            }

                        }
//                        if (absentRoll.size() < 1) {
//                            Toast.makeText(AttendanceActivity.this, "Nothing is selected", Toast.LENGTH_SHORT).show();
//                        }
                        absentRollString = convertToString(absentRoll);
                        Toast.makeText(AttendanceActivity.this, "Absent ids are: " + absentRollString, Toast.LENGTH_SHORT).show();

                        Retrofit retrofitSms = new Retrofit.Builder()
                                .baseUrl(AbsenteeSmsApi.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AbsenteeSmsApi apiSms = retrofitSms.create(AbsenteeSmsApi.class);
                        Call<List<AttendanceStudentInformation>> call = apiSms.getStudentInformationList(
                                std_shift_id,
                                std_class_id,
                                std_section_id,
                                std_year,
                                absentRollString

                        );

                        //URL checking correct or not
//                        String url = call.request().url().toString();
//                        try {
//                            String decoded = URLDecoder.decode(url, "UTF-8");
//                            //Toast.makeText(AttendanceActivity.this, decoded, Toast.LENGTH_SHORT).show();
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }


                        call.enqueue(new Callback<List<AttendanceStudentInformation>>() {

                            @Override
                            public void onResponse(Call<List<AttendanceStudentInformation>> call, Response<List<AttendanceStudentInformation>> response) {
                                studentInformationList = response.body();
                                for (AttendanceStudentInformation information : studentInformationList) {

                                    today = String.valueOf(information.getDay());

                                }
                            }

                            @Override
                            public void onFailure(Call<List<AttendanceStudentInformation>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();


            }
        });
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(getIntent());
                //Toast.makeText(AttendanceActivity.this, "Loading....", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    static String convertToString(ArrayList<String> absentRollNumbers) {
        StringBuilder builder = new StringBuilder();
        // Append all Integers in StringBuilder to the StringBuilder.
        for (String number : absentRollNumbers) {
            builder.append(number);
            builder.append(",");
        }
        // Remove last delimiter with setLength.
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }

        return builder.toString();
    }

}
