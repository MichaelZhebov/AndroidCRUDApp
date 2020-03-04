package com.example.crudtest.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudtest.R;
import com.example.crudtest.activity.EmpDetails;
import com.example.crudtest.model.Employee;

import java.util.List;

public class EmployeeRecyclerAdapter extends RecyclerView.Adapter<EmployeeRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Employee> employees;

    public EmployeeRecyclerAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setTag(employees.get(position));

        Employee employee = employees.get(position);

        holder.employeeFirstName.setText(employee.getFirstName());
        holder.employeeLastName.setText(employee.getLastName());
        holder.employeeEmail.setText(employee.getEmail());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = position;
                Intent intent = new Intent(context, EmpDetails.class);
                intent.putExtra("id",employees.get(index).getId());
                intent.putExtra("firstName",employees.get(index).getFirstName());
                intent.putExtra("lastName",employees.get(index).getLastName());
                intent.putExtra("email",employees.get(index).getEmail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        public TextView employeeFirstName;
        public TextView employeeLastName;
        public TextView employeeEmail;
        public LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            employeeFirstName = (TextView) itemView.findViewById(R.id.text_view_fname);
            employeeLastName = (TextView) itemView.findViewById(R.id.text_view_lname);
            employeeEmail = (TextView) itemView.findViewById(R.id.text_view_email);
            parentLayout = itemView.findViewById(R.id.list_item);
        }
    }
}
