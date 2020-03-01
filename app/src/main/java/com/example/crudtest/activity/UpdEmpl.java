package com.example.crudtest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudtest.R;
import com.example.crudtest.model.Employee;
import com.example.crudtest.util.NetworkService;

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
            firstName.setError("Please Enter a first name!");
            return;
        }else {
            employee.setFirstName(fName);
        }
        EditText lastName = (EditText) findViewById(R.id.empLastName);
        String lName = lastName.getText().toString();
        if (TextUtils.isEmpty(lName)){
            lastName.setError("Please Enter a last name!");
            return;
        }else {
            employee.setLastName(lName);
        }
        EditText email = (EditText) findViewById(R.id.empEmail);
        String emailStr = email.getText().toString();
        if (TextUtils.isEmpty(emailStr)){
            email.setError("Please Enter a email!");
            return;
        }else if (isValidEmail(emailStr)) {
            employee.setEmail(emailStr);
        } else {
            email.setError("Please Enter a valid email!");
            return;
        }

        NetworkService.getInstance()
                .getJSONApi()
                .updateEmployee(id, employee)
                .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(@NonNull Call<Employee> call, @NonNull Response<Employee> response) {
                        Toast.makeText(getApplicationContext(), "Employee update success!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Employee> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error occurred while getting request!", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
