package com.example.crudtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.crudtest.R;
import com.example.crudtest.model.Employee;

import java.util.List;

public class EmployeesAdapter extends ArrayAdapter<Employee> {
    public EmployeesAdapter(Context context, List<Employee> employees) {
        super(context, 0, employees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Employee employee = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_employee, parent, false);
        }
        TextView tvId = (TextView) convertView.findViewById(R.id.position);
        TextView tvName = (TextView) convertView.findViewById(R.id.empFirstName);

        tvId.setText(String.valueOf(position + 1));
        tvName.setText(employee.getFirstName());

        return convertView;
    }
}
