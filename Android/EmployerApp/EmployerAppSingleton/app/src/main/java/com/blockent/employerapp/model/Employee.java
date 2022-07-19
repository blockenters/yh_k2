package com.blockent.employerapp.model;

import java.io.Serializable;

public class Employee implements Serializable {

    public int id;
    public String name;
    public int salary;
    public int age;

    public Employee(int id, String name, int age, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
