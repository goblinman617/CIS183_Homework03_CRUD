package com.example.crudapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//This is an adapter for the default list view
public class EmployeeListAdapter extends BaseAdapter {
    Context context; //where we came from
    ArrayList<Employee> employeeList; //our arraylist of employees

    //constructor
    public EmployeeListAdapter(Context context, ArrayList<Employee> employeeList){
        this.context = context;
        this.employeeList = employeeList;
    }


    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int i) {
        return employeeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //sets cell to our custom cell
        if (view == null){
            LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = myInflater.inflate(R.layout.custom_cell, null);
        }
        //find gui elements
        TextView firstname = view.findViewById(R.id.tv_cell_firstname);
        TextView lastname = view.findViewById(R.id.tv_cell_lastname);
        TextView username = view.findViewById(R.id.tv_cell_username);

        //get the Employee at i
        Employee curEmployee = employeeList.get(i);
        //set the gui for the custom cell
        firstname.setText(curEmployee.getFirstname());
        lastname.setText(curEmployee.getLastname());
        username.setText(curEmployee.getUsername());

        //return finished view
        return view;
    }
}
