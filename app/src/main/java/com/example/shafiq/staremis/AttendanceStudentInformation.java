package com.example.shafiq.staremis;

import java.util.Calendar;
import java.util.TimeZone;

public class AttendanceStudentInformation {
    private String Student_ID;
    private String a_session;
    private String a_year;
    private String campus_id;
    private String shift_id;
    private String shift_name;
    private String class_id;
    private String class_name;
    private String section_id;
    private String section_name;
    private String cYear;
    private String cMonth;
    private String D13;
    private String class_roll_no;
    private String student_name;
    private String contact_phone;
    private int day;


    public AttendanceStudentInformation(String student_ID, String a_session, String a_year, String campus_id, String shift_id, String shift_name, String class_id, String class_name, String section_id, String section_name, String cYear, String cMonth,String D13, String class_roll_no, String student_name, String contact_phone) {
        Student_ID = student_ID;
        this.a_session = a_session;
        this.a_year = a_year;
        this.campus_id = campus_id;
        this.shift_id = shift_id;
        this.shift_name = shift_name;
        this.class_id = class_id;
        this.class_name = class_name;
        this.section_id = section_id;
        this.section_name = section_name;
        this.cYear = cYear;
        this.cMonth = cMonth;
        this.D13 = D13;

        this.class_roll_no = class_roll_no;
        this.student_name = student_name;
        this.contact_phone = contact_phone;

    }

    public int getDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        day = calendar.get(Calendar.DATE);
        return day;
    }

    public String getD13() {
        return D13;
    }

    public void setD13(String d13) {
        D13 = d13;
    }

    public String getStudent_ID() {
        return Student_ID;
    }

    public void setStudent_ID(String student_ID) {
        Student_ID = student_ID;
    }

    public String getA_session() {
        return a_session;
    }

    public void setA_session(String a_session) {
        this.a_session = a_session;
    }

    public String getA_year() {
        return a_year;
    }

    public void setA_year(String a_year) {
        this.a_year = a_year;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getShift_id() {
        return shift_id;
    }

    public void setShift_id(String shift_id) {
        this.shift_id = shift_id;
    }

    public String getShift_name() {
        return shift_name;
    }

    public void setShift_name(String shift_name) {
        this.shift_name = shift_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getcYear() {
        return cYear;
    }

    public void setcYear(String cYear) {
        this.cYear = cYear;
    }

    public String getcMonth() {
        return cMonth;
    }

    public void setcMonth(String cMonth) {
        this.cMonth = cMonth;
    }

    public String getClass_roll_no() {
        return class_roll_no;
    }

    public void setClass_roll_no(String class_roll_no) {
        this.class_roll_no = class_roll_no;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }
}
