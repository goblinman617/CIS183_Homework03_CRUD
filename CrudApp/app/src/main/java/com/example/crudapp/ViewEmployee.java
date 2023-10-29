package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewEmployee extends AppCompatActivity {
    TextView tv_j_firstname;
    TextView tv_j_lastname;
    TextView tv_j_username;
    TextView tv_j_password;
    TextView tv_j_email;
    TextView tv_j_age;
    Button btn_j_editEmployee;
    Button btn_j_back;

    Intent mainIntent;
    Intent editIntent;
    Employee e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);
        //link java and xml
        tv_j_firstname = findViewById(R.id.tv_view_firstname);
        tv_j_lastname = findViewById(R.id.tv_view_lastname);
        tv_j_username = findViewById(R.id.tv_view_username);
        tv_j_password = findViewById(R.id.tv_view_password);
        tv_j_email = findViewById(R.id.tv_view_email);
        tv_j_age = findViewById(R.id.tv_view_age);
        btn_j_back = findViewById(R.id.btn_view_back);
        btn_j_editEmployee = findViewById(R.id.btn_view_edit);

        mainIntent = new Intent(ViewEmployee.this, MainActivity.class);
        editIntent = new Intent(ViewEmployee.this, AddEmployee.class);

        Intent cameFrom = getIntent();
        // set Employee e to
        e = (Employee) cameFrom.getSerializableExtra("Employee");

        setTextViews();
        editEmployeeEvent();
        backButtonEvent();
    }

    public void editEmployeeEvent(){
        btn_j_editEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewIntent.putExtra("Employee", employeeList.get(i));
                editIntent.putExtra("Employee", e);
                startActivity(editIntent);
            }
        });
    }

    public void backButtonEvent(){
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainIntent);
            }
        });
    }

    public void setTextViews(){
        tv_j_firstname.setText(e.getFirstname());
        tv_j_lastname.setText(e.getLastname());
        tv_j_username.setText(e.getUsername());
        tv_j_password.setText(e.getPassword());
        tv_j_email.setText(e.getEmail());
        String age = e.getAge() + "";
        tv_j_age.setText(age);
    }
}