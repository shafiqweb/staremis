package com.example.shafiq.staremis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AbsenteeSmsApi {
    String BASE_URL = "http://103.251.247.114:3383/webservices/attendance.asmx/";

    @GET("saveTeacherAttendance")
    Call<List<AttendanceStudentInformation>> getStudentInformationList(
            @Query("shift_id") String shift_id,
            @Query("class_id") String class_id,
            @Query("section_id") String section_id,
            @Query("year") String year,
            @Query("absentees") String absentees
    );
}

