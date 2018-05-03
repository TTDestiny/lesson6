package com.biz.lesson.model.student;

import com.biz.lesson.model.Persistent;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject extends Persistent {

    @Column(length = 20)
    private String name;//学科名称

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_student_subject",
            joinColumns = { @JoinColumn(name = "subject_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") })
    private List<Student> studentList;//选该学科的学生

    @Formula("(SELECT AVG(sss.score) from student_student_subject sss WHERE sss.subject_id = id)")
    private Double avgScore;//科目平均分

    public Subject() {
    }


    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
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
