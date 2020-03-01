package com.example.crudtest.rest;

import com.example.crudtest.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {

    @GET("/api/v1/employees")
    public Call<List<Employee>> getAllEmployees();

    @GET("/api/v1/employees/{id}")
    public Call<Employee> getEmployee(@Path("id") long id);

    @POST("/api/v1/employees")
    public Call<Employee> addEmployee(@Body Employee employee);

    @PUT("/api/v1/employees/{id}")
    public Call<Employee> updateEmployee(@Path("id") long id, @Body Employee employee);

    @DELETE("/api/v1/employees/{id}")
    public Call<Employee> deleteEmployee(@Path("id") long id);
}
