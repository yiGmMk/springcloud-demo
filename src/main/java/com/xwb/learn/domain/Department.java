package com.xwb.learn.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Department {
    @Id
    private Long id;
    private String dept;
    private Long empId;

    public Department(String dept, int empId) {
        this.dept = dept;
        this.empId = (long) empId;
    }
}
