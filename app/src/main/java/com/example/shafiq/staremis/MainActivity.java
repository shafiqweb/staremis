package com.example.shafiq.staremis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayAdapter<String> arrayAdapter;
    Spinner spinner;
    String userInstitutionCode, categorySelected, userName, userPassword;
    String[] categoryName = {"Admin", "Teacher", "Student"};
    Button loginButton;
    EditText institutionCodeEditText, userNameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        institutionCodeEditText = findViewById(R.id.institutionCodeEditTextID);
        userNameEditText = findViewById(R.id.userNameEditTextID);
        passwordEditText = findViewById(R.id.passwordEditTextID);
        loginButton = findViewById(R.id.loginButtonID);
        loginButton.setOnClickListener(this);
        spinner = findViewById(R.id.spinnerID);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.sample_view_spinner, R.id.categorySampleTextViewID, categoryName);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButtonID) {
            userInstitutionCode = institutionCodeEditText.getText().toString();
            userName = userNameEditText.getText().toString();
            userPassword = passwordEditText.getText().toString();

            if (TextUtils.isEmpty(userInstitutionCode)) {
                showToast("Please enter your Institution Code");
            } else if (TextUtils.isEmpty(userName)) {
                showToast("Please enter user Name");
            } else if (TextUtils.isEmpty(userPassword)) {
                showToast("Please enter your Password");
            } else {
                String type = "login";
                ConnectionServer connectionServer = new ConnectionServer(this);
                connectionServer.execute(type, userInstitutionCode, categorySelected, userName, userPassword);

            }
            Intent intent = new Intent(MainActivity.this,DashBoardActivity.class);
            startActivity(intent);
        }
    }

    public void showToast(final String text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
