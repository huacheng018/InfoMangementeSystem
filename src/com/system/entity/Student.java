package com.system.entity;

/**
 * Student类对象
 * @Author: 枠成
 * @Data:2019-08-18
 * @Description:com.system.entity
 * @version:1.0
 */
public class Student {

    private int id;
    private String name;
    private int age;
    private boolean gender;
    private String major;
    private String academy;
    //头像文件名
    private String headPath ;
    //权限，初始学生为0
    private boolean authority;
    private String password;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public boolean getAuthority() {
        return authority;
    }

    public void setAuthority(boolean authority) {
        this.authority = authority;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", major=" + major +
                ", academy='" + academy + '\'' +
                ", headPath='" + headPath + '\'' +
                ", authority=" + authority +
                ", password='" + password + '\'' +
                '}';
    }
}


