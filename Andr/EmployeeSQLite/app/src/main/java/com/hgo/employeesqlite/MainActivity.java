package com.hgo.employeesqlite;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATABASE_NAME = "myempDB";

    TextView textViewViewEmployees;
    EditText editTextName, editTextSalary;
    Spinner spinnerDepartment;
    SQLiteDatabase mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewViewEmployees = (TextView) findViewById(R.id.textViewViewEmployees);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSalary = (EditText) findViewById(R.id.editTextSalary);
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);

        findViewById(R.id.buttonAddEmployee).setOnClickListener(this);
        findViewById(R.id.textViewViewEmployees).setOnClickListener(this);

        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createEmployeeTable();
    }

    private void createEmployeeTable() {
       mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS employees (\n" +
                        "    name varchar(200),\n" +
                        "    department varchar(200),\n" +
                        "    joiningdate datetime,\n" +
                        "    salary double\n" +
                        ");"
        );
    }

    private void addEmployee() {
        String name=editTextName.getText().toString().trim();
        String salary=editTextSalary.getText().toString().trim();
        String dept=spinnerDepartment.getSelectedItem().toString();

        if (name.isEmpty()) {
            editTextName.setError("Please enter a name");
            editTextName.requestFocus();
            return;
        }
        if (salary.isEmpty()) {
            editTextSalary.setError("Please enter salary");
            editTextSalary.requestFocus();
            return;
        }
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String joiningDate= sdf.format(cal.getTime());

        String sql="INSERT INTO employees(name,department,joiningdate,salary)"+" VALUES\n (?,?,?,?);";
        mDatabase.execSQL(sql,new String[]{name,dept,joiningDate,salary});
        Toast.makeText(this,"Employee added!.",Toast.LENGTH_SHORT).show();
    }

       @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddEmployee:
                addEmployee();
                break;
            case R.id.textViewViewEmployees:
                startActivity(new Intent(this, EmployeeActivity.class));
                break;
        }
    }
}