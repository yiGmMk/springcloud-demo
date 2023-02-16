package com.xwb.learn.domain;

import org.springframework.data.annotation.Id;

public class Department {
    @Id
    private Long id;
    private String dept;
    private Long empId;

    public Department() {
    }

    public Department(Long id, String dept, Long empId) {
        this.id = id;
        this.dept = dept;
        this.empId = empId;
    }

    public Department(String dept, Long empId) {
        this.dept = dept;
        this.empId = empId;
    }

    public Department(String dept, int empId) {
        this.dept = dept;
        this.empId = (long) empId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {

        return String.format("shop[%d],name=%s,emp_id=%d", id, dept, empId);
    }
}
