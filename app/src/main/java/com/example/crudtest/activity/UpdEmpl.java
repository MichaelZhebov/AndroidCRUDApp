package com.example.crudtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudtest.R;
import com.example.crudtest.model.Employee;
import com.example.crudtest.service.NetworkService;
import com.example.crudtest.util.Help;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdEmpl extends AppCompatActivity {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_empl);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        email = intent.getStringExtra("email");

        EditText firstNameET = (EditText) findViewById(R.id.empFirstName);
        firstNameET.setText(firstName);
        EditText lastNameET = (EditText) findViewById(R.id.empLastName);
        lastNameET.setText(lastName);
        EditText emailET = (EditText) findViewById(R.id.empEmail);
        emailET.setText(email);
    }

    public void updEmployee(View view) {
        Employee employee = new Employee();
        employee.setId(id);
        EditText firstName = (EditText) findViewById(R.id.empFirstName);
        String fName = firstName.getText().toString();
        if (TextUtils.isEmpty(fName)){
            firstName.setError(getString(R.string.enter_first_name));
            return;
        }else {
            employee.setFirstName(fName);
        }
        EditText lastName = (EditText) findViewById(R.id.empLastName);
        String lName = lastName.getText().toString();
        if (TextUtils.isEmpty(lName)){
            lastName.setError(getString(R.string.enter_last_name));
            return;
        }else {
            employee.setLastName(lName);
        }
        EditText email = (EditText) findViewById(R.id.empEmail);
        String emailStr = email.getText().toString();
        if (TextUtils.isEmpty(emailStr)){
            email.setError(getString(R.string.enter_email));
            return;
        }else if (Help.isValidEmail(emailStr)) {
            employee.setEmail(emailStr);
        } else {
            email.setError(getString(R.string.valid_email));
            return;
        }

        NetworkService.getInstance()
                .getJSONApi()
                .updateEmployee(id, employee)
                .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(@NonNull Call<Employee> call, @NonNull Response<Employee> response) {
                        Toast.makeText(getApplicationContext(), getString(R.string.updated_success), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Employee> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
