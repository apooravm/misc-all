package com.hgo.employeesqlite;

public class Employee {
    String name, dept, joiningDate;
    double salary;
    public Employee(String name, String dept, String joiningDate, double salary) {
        this.name = name;
        this.dept = dept;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public double getSalary() {
        return salary;
    }
}

