package com.example.shafiq.staremis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URLEncoder;

public class ConnectionServer extends AsyncTask<String, Void, String> {
    public String userType;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private AlertDialog alertDialog;
    String type;

    ConnectionServer(Context context) {
        this.context = context;
    }
    ConnectionServer(){}

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        UserLogin userLogin = new UserLogin();
        if (type.equals("login")) {
            String login_url = "http://103.251.247.114:3383/webservices/login.asmx/login";
            try {

                userLogin.setInstitutionCode(params[1]);
                userLogin.setCategoryName(params[2]);
                userLogin.setUserName(params[3]);
                userLogin.setUserPassword(params[4]);
                return executePostMethod(login_url, userLogin);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login status");
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("true")) {
            Toast toast = Toast.makeText(context, "Login is Successful", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(context, DashBoardActivity.class);
            context.startActivity(intent);
        } else if (result.equals("false")) {
            alertDialog.setMessage("Login is not successful!!");
            alertDialog.show();
            Toast toast = Toast.makeText(context, "Invalid Information", Toast.LENGTH_LONG);
            View view = toast.getView();
            TextView view1 = view.findViewById(android.R.id.message);
            view1.setTextColor(Color.YELLOW);
            view.setBackgroundResource(R.color.colorRed);
            toast.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private String executePostMethod(String login_url, UserLogin userLogin) throws IOException {
        Log.d("ConnectUrl  :", login_url);
        URL url = new URL(login_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String post_data = null;
        if (userLogin.getInstitutionCode() != null) {
            post_data += "&" + URLEncoder.encode("institution_code", "UTF-8") + "=" + URLEncoder.encode(userLogin.getInstitutionCode(), "UTF-8");
        }
        if (userLogin.getCategoryName() != null) {
            Log.d("Category", userLogin.getCategoryName());
            switch (userLogin.getCategoryName()) {
                case "Student":
                    userType = "3";
                    break;
                case "Teacher":
                    userType = "2";
                    break;
                default:
                    userType = "1";
                    break;
            }
            post_data += "&" + URLEncoder.encode("login_as", "UTF-8") + "=" + URLEncoder.encode(userType, "UTF-8");
        }
        if (userLogin.getUserName() != null) {
            Log.d("Category", userLogin.getUserName());
            post_data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userLogin.getUserName(), "UTF-8");
        }
        if (userLogin.getUserPassword() != null) {
            post_data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(userLogin.getUserPassword(), "UTF-8");
        }
        assert post_data != null;
        Log.d("PostData", post_data);

        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result.toString();

    }
}
