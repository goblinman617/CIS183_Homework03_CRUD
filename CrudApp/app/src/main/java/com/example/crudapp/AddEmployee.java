package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AddEmployee extends AppCompatActivity {
    Intent mainIntent;
    EditText et_j_firstname;
    EditText et_j_lastname;
    EditText et_j_username;
    EditText et_j_password;
    EditText et_j_email;
    EditText et_j_age;
    Button btn_j_submit;
    Button btn_j_back;
    TextView tv_j_header;
    TextView tv_j_error;
    boolean addNewEmployee;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // link all xml and java elements
        et_j_firstname = findViewById(R.id.et_add_fname);
        et_j_lastname = findViewById(R.id.et_add_lname);
        et_j_username = findViewById(R.id.et_add_uname);
        et_j_password = findViewById(R.id.et_add_pword);
        et_j_email = findViewById(R.id.et_add_email);
        et_j_age = findViewById(R.id.et_add_age);
        btn_j_submit = findViewById(R.id.btn_add_submit);
        btn_j_back = findViewById(R.id.btn_add_back);
        tv_j_header = findViewById(R.id.tv_add_header);
        tv_j_error = findViewById(R.id.tv_add_error);

        //setUp intent for the back button.
        mainIntent = new Intent(AddEmployee.this, MainActivity.class);

        //make a new DatabaseHelper so we can acccess the database
        dbHelper = new DatabaseHelper(this);

        //get intent we came from
        Intent cameFrom = getIntent();
        //try and grab the bundle called Employee which contains an Employee object
        Employee passedEmployee = (Employee) cameFrom.getSerializableExtra("Employee");

        if (passedEmployee != null){
            //because we received an Employee we are editing an employee
            et_j_firstname.setText(passedEmployee.getFirstname());
            et_j_lastname.setText(passedEmployee.getLastname());

            //don't allow user to edit username
            et_j_username.setText(passedEmployee.getUsername());
            et_j_username.setEnabled(false);
            // if the text box doesn't show do this
            //et_j_username.setKeyListener(null);

            et_j_password.setText(passedEmployee.getPassword());
            et_j_email.setText(passedEmployee.getEmail());
            String age = passedEmployee.getAge() + "";
            et_j_age.setText(age);

            tv_j_header.setText("Edit Employee");
            addNewEmployee = false;

        }else{
            //didn't receive employee we are making a new employee
            tv_j_header.setText("Add Employee");
            addNewEmployee = true;
        }

        submitButtonEvent();
        backButtonEvent();
    }

    public void submitButtonEvent(){
        btn_j_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = et_j_firstname.getText().toString();
                String lname = et_j_lastname.getText().toString();
                String uname = et_j_username.getText().toString();
                String pword = et_j_password.getText().toString();
                String email = et_j_email.getText().toString();
                int age;
                try {
                    //parse age box for an int
                    age = Integer.parseInt(et_j_age.getText().toString());
                }catch (Exception e){
                    //if no int set age to -999 for when we check to see if there have been valid inputs
                    age = -999;
                }

                Employee entry = new Employee(fname,lname,uname,pword,email,age);

                if (correctData(entry, addNewEmployee)){
                    //data is correct
                    if (addNewEmployee){
                        dbHelper.addEmployee(entry);
                        clearAllEditText();
                        tv_j_error.setVisibility(View.INVISIBLE);
                        //don't change view because you can add multiple users
                    }else{
                        dbHelper.updateEmployee(entry);
                        //return user to initial intent
                        startActivity(mainIntent);
                    }
                }else{
                    tv_j_error.setVisibility(View.VISIBLE);
                    Log.d("Error", "failed to pass correctData()");
                }
            }
        });
    }

    public void backButtonEvent(){
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //press back, reload main intent
                startActivity(mainIntent);
            }
        });
    }

    public boolean correctData(Employee e, boolean newEmployee){
        boolean pass = true;

        if (newEmployee){
            ArrayList<Employee> databaseList;
            //get a copy of the database and get the number of rows
            databaseList = dbHelper.getAllRows();
            int listSize = databaseList.size();

            //check to see if any usernames match
            for (int i = 0; i < listSize; i++){
                if (e.getUsername().equals(databaseList.get(i).getUsername())){
                    return false;
                }
            }
        }//edit employee shouldnt be able to update username so we don't need to care

        if (e.getFirstname() == null) {
            pass = false;
        }else if (e.getLastname() == null){
            pass = false;
        }else if (e.getUsername() == null){
            pass = false;
        }else if (e.getPassword() == null){
            pass = false;
        }else if (e.getEmail() == null){
            pass = false;
        }else if (e.getAge() < 0){
            pass = false;
        }

        return pass;
    }

    public void clearAllEditText(){
        et_j_firstname.setText("");
        et_j_lastname.setText("");
        et_j_username.setText("");
        et_j_password.setText("");
        et_j_email.setText("");
        et_j_age.setText("");
    }
}