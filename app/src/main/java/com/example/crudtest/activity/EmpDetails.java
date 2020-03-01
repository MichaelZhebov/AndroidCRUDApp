package com.example.crudtest.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudtest.R;
import com.example.crudtest.model.Employee;
import com.example.crudtest.util.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpDetails extends AppCompatActivity {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_details);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        email = intent.getStringExtra("email");

        TextView textViewId = (TextView)findViewById(R.id.empId);
        textViewId.append(String.valueOf(id));

        TextView textViewFirstName = (TextView)findViewById(R.id.empFirstName);
        textViewFirstName.append(firstName);

        TextView textViewLastName = (TextView)findViewById(R.id.empLastName);
        textViewLastName.append(lastName);

        TextView textViewEmail = (TextView)findViewById(R.id.empEmail);
        textViewEmail.append(email);
    }

    public void deleteEmployee(final View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Employee")
                .setMessage("Do you really want to delete Employee?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        // deleting code....
                        NetworkService.getInstance()
                                .getJSONApi()
                                .deleteEmployee(id)
                                .enqueue(new Callback<Employee>() {
                                    @Override
                                    public void onResponse(@NonNull Call<Employee> call, @NonNull Response<Employee> response) {
                                        Toast.makeText(getApplicationContext(), "Employee deleted success!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<Employee> call, @NonNull Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Error occurred while getting request!", Toast.LENGTH_SHORT).show();
                                        t.printStackTrace();
                                    }
                                });
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    public void updateEmployee(View view) {
        //updating code.....
        Intent intent = new Intent(getApplicationContext(),UpdEmpl.class);
        intent.putExtra("id",id);
        intent.putExtra("firstName",firstName);
        intent.putExtra("lastName",lastName);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
