package com.lp.server;

public class Param {
    private int age1;
    private int age2;

    public Param() {
    }

    public Param(int age1, int age2) {
        this.age1 = age1;
        this.age2 = age2;
    }

    public int getAge1() {
        return age1;
    }

    public void setAge1(int age1) {
        this.age1 = age1;
    }

    public int getAge2() {
        return age2;
    }

    public void setAge2(int age2) {
        this.age2 = age2;
    }

    @Override
    public String toString() {
        return "Param{" +
                "age1=" + age1 +
                ", age2=" + age2 +
                '}';
    }
}
