package com.example.shafiq.staremis;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AttendanceConnectionServer extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog alertDialog;
    String type;

    AttendanceConnectionServer(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        UserLogin userLogin = new UserLogin();
        if (type.equals("attendance")) {
            String teacher_login_url = "http://103.251.247.114:3383/webservices/attendance.asmx/LoadTeacherAttendace?teacher_id=T040";
            try {

                //userLogin.setTeacherID(params[1]);
                return executePostMethod(teacher_login_url);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("OK");
    }

    @Override
    protected void onPostExecute(String result) {
        //            String jsonstring = "{"
//                    +"abc"+": ["
//                    +"{"
//                    + "Student_ID"+":"+ "englishisgood"
//                    +"},"
//                    +"{"
//                    +"Student_ID"+":"+ "myenglishisnotsoaccurate"
//                    +"},"
//                    +"{"
//                    + "Student_ID"+":"+ "germanisverynicelanguage"
//                    +"}"+","
//                    +"{"
//                    +"Student_ID"+":"+ "mygermanisabsolutelygood"
//                    +"}"
//                    +"]"+
//                    "}";
        String jsonstring = "{"
                +"abc:"+result+
                "}";
        String parsedObject = returnParsedJsonObject(jsonstring);
        //JSONObject jsnobject = new JSONObject(result);
        //String msg = jsnobject.getString("message");
        //String id = jsnobject.getString("id");
        alertDialog.setMessage(parsedObject);
        alertDialog.show();
//        Intent intent = new Intent(context, AttendanceActivity.class);
//        context.startActivity(intent);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private String executePostMethod(String teacher_login_url) throws IOException, JSONException {
        //Log.d("ConnectUrl  :", connect_url);
        URL url = new URL(teacher_login_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept","application/json");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        JSONObject jsonParam = new JSONObject();



            jsonParam.put("teacher_id","T040");

        bufferedWriter.write(jsonParam.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
        String result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;

        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;
    }
    private String returnParsedJsonObject(String result){


        ArrayList<String> jsonObject = new ArrayList<String>();

        JSONObject resultObject = null;

        JSONArray jsonArray = null;


        try {

            resultObject = new JSONObject(result);

            System.out.println("Testing the water " + resultObject.toString());

            jsonArray = resultObject.optJSONArray("abc");

        } catch (JSONException e) {

            e.printStackTrace();

        }

        //assert jsonArray != null;
        for(int i = 0; i < jsonArray.length(); i++){

            JSONObject jsonChildNode = null;

            try {

                jsonChildNode = jsonArray.getJSONObject(i);

                String Student_ID = jsonChildNode.getString("Student_ID");


                jsonObject.add(Student_ID);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
        System.out.println("Testing the water " + jsonObject.toString());

        return jsonObject.toString();

    }
}
