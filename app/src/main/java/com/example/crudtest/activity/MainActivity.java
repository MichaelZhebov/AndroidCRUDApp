package com.example.crudtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.crudtest.R;
import com.example.crudtest.adapter.EmployeeRecyclerAdapter;
import com.example.crudtest.model.Employee;
import com.example.crudtest.service.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Employee> employees;
    private ProgressBar pgsBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        getAllEmployees();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getAllEmployees();
            }
        });
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
                        setViews();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                        pgsBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

    public void redirect(View view) {
        Intent intent = new Intent(getApplicationContext(),AddEmpl.class);
        startActivity(intent);
    }

    private void setViews() {
        recyclerView=(RecyclerView) findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new EmployeeRecyclerAdapter(this, employees);

        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
