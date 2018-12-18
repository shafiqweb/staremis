package com.example.shafiq.staremis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "http://103.251.247.114:3383/webservices/attendance.asmx/";
    @GET("LoadTeacherAttendace?teacher_id=T040")
    Call<List<AttendanceStudentInformation>> getAttendanceStudentInformation();
}
