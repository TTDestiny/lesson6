package com.biz.lesson.model.student;


import com.biz.lesson.model.Persistent;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "grade")
public class Grade extends Persistent {
    @Column(length = 20)
    private String name;//班级名称


    @OneToMany(cascade =CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "grade")
    private List<Student> studentList;//班级学生

    @Formula("(select COUNT(s.grade_id) from student s where s.grade_id =id )")
    private int count;//班级人数

    @Formula("(SELECT AVG(sts.score) from student_student_subject sts WHERE sts.student_id IN(SELECT s.id from student  s where s.grade_id = id))")
    private Double avgScore;

    public Grade() {
    }

    public Grade(String name, List<Student> studentList) {
        this.name = name;
        this.studentList = studentList;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
