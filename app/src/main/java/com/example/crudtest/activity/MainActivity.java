package com.example.crudtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudtest.R;
import com.example.crudtest.model.Employee;
import com.example.crudtest.util.EmployeesAdapter;
import com.example.crudtest.util.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Employee> employees;
    private ProgressBar pgsBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        listView = (ListView) findViewById(R.id.listView);

        TextView titleText = new TextView(this);
        titleText.setText(R.string.employees_list);
        listView.addHeaderView(titleText);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - 1;
                Intent intent = new Intent(getApplicationContext(),EmpDetails.class);
                intent.putExtra("id",employees.get(index).getId());
                intent.putExtra("firstName",employees.get(index).getFirstName());
                intent.putExtra("lastName",employees.get(index).getLastName());
                intent.putExtra("email",employees.get(index).getEmail());
                startActivity(intent);
            }
        });

        getAllEmployees();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllEmployees();
    }

    private void getAllEmployees() {
        pgsBar.setVisibility(View.VISIBLE);
        NetworkService.getInstance()
                .getJSONApi()
                .getAllEmployees()
                .enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                        pgsBar.setVisibility(View.GONE);
                        employees = response.body();
                        EmployeesAdapter arrayAdapter = new EmployeesAdapter(getApplicationContext(), employees);
                        listView.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                        pgsBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error occurred while getting request!", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

    public void redirect(View view) {
        Intent intent = new Intent(getApplicationContext(),AddEmpl.class);
        startActivity(intent);
    }
}
