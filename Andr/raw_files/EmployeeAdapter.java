package com.hgo.employeesqlite;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    Context mCtx;
    int listLayoutRes;
    List<Employee> employeeList;
    SQLiteDatabase mDatabase;

    public EmployeeAdapter(Context mCtx, int listLayoutRes, List<Employee> employeeList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, employeeList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.employeeList = employeeList;
        this.mDatabase = mDatabase;
    }

    private void updateEmployee(final Employee employee) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(view);

        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextSalary = view.findViewById(R.id.editTextSalary);
        final Spinner spinnerDepartment = view.findViewById(R.id.spinnerDepartment);

        editTextName.setText(employee.getName());
        editTextSalary.setText(String.valueOf(employee.getSalary()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String salary = editTextSalary.getText().toString().trim();
                String dept = spinnerDepartment.getSelectedItem().toString();

                if (name.isEmpty()) {
                    editTextName.setError("Name can't be blank");
                    editTextName.requestFocus();
                    return;
                }

                if (salary.isEmpty()) {
                    editTextSalary.setError("Salary can't be blank");
                    editTextSalary.requestFocus();
                    return;
                }

                String sql = "UPDATE employees \n" +
                        "SET department = ?, \n" +
                        "salary = ? \n" +
                        "WHERE name = ?;\n";

                mDatabase.execSQL(sql, new String[]{dept, salary, name});
                Toast.makeText(mCtx, "Employee Updated", Toast.LENGTH_SHORT).show();
                reloadEmployeesFromDatabase();
                dialog.dismiss();
            }
        });
    }

    private void reloadEmployeesFromDatabase() {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees", null);
        if (cursorEmployees.moveToFirst()) {
            employeeList.clear();
            do {
                employeeList.add(new Employee(
                        cursorEmployees.getString(0),
                        cursorEmployees.getString(0),
                        cursorEmployees.getString(2),
                        cursorEmployees.getDouble(3)
                ));
            } while (cursorEmployees.moveToNext());
        }
        cursorEmployees.close();
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final Employee employee = employeeList.get(position);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);

        textViewName.setText(employee.getName());
        textViewDept.setText(employee.getDept());
        textViewSalary.setText(String.valueOf(employee.getSalary()));
        textViewJoiningDate.setText(employee.getJoiningDate());

        Button buttonDelete = view.findViewById(R.id.buttonDeleteEmployee);
        Button buttonEdit = view.findViewById(R.id.buttonEditEmployee);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateEmployee(employee);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM employees WHERE name = ?";
                        mDatabase.execSQL(sql,new String[]{employee.getName()});
                        reloadEmployeesFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
};
