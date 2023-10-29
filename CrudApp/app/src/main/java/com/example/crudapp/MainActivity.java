package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
//view all current employees
//can go to add employee
//clicking on employee will

public class MainActivity extends AppCompatActivity {
    Button btn_j_addUser;
    ListView lv_j_employees;
    DatabaseHelper dbHelper;
    ArrayList<Employee> employeeList;
    EmployeeListAdapter adapter;
    Intent addIntent;
    Intent viewIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employeeList = new ArrayList<>();
        //connect java and xml elements
        btn_j_addUser = findViewById(R.id.btn_main_addEmployee);
        lv_j_employees = findViewById(R.id.lv_main_employeeList);

        //intent you come from, intent you want to load
        addIntent = new Intent(MainActivity.this, AddEmployee.class);
        viewIntent = new Intent(MainActivity.this, ViewEmployee.class);
        //make dbHelper
        dbHelper = new DatabaseHelper(this);
        //DON'T REMOVE THIS LOG STATEMENT (initialize default info)
        Log.d("Initialized default entries", dbHelper.initializeDefaultInfo() + "");

        //set employee list to what the database is
        employeeList = dbHelper.getAllRows();
        //set the list view to employee list
        fillListView();

        //for test
        debugAllUsernames();

        addEmployeeButtonEvent();
        deleteEmployeeEvent();
        viewEmployeeEvent();
    }

    public void addEmployeeButtonEvent(){
        btn_j_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we know that we are adding a new user because when we get to the intent
                //there is no "extras" for us to catch like we would for our "edit" intent
                startActivity(addIntent);
            }
        });
    }

    //long click on list view
    public void deleteEmployeeEvent(){
        lv_j_employees.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String username = employeeList.get(i).getUsername();
                dbHelper.deleteEmployee(username);
                employeeList.remove(i);

                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    //click list view
    public void viewEmployeeEvent(){
        lv_j_employees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //pass the Employee at i
                viewIntent.putExtra("Employee", employeeList.get(i));
                //open the intent view intent
                startActivity(viewIntent);
            }
        });
    }

    //called once after employeeList is filled
    public void fillListView() {
        //set adapter to the employeeListAdapter
        adapter = new EmployeeListAdapter(this, employeeList);
        lv_j_employees.setAdapter(adapter);
    }

    public void debugAllUsernames(){
        for (int i = 0; i < employeeList.size(); i++){
            Log.d("User at " + i + ":", employeeList.get(i).getUsername());
        }
    }
}