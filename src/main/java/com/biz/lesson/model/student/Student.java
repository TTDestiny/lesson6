package com.biz.lesson.model.student;

import com.biz.lesson.model.Persistent;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student extends Persistent {

    @Column(length = 20)
    private String studentId;//学号

    @Column(length = 40)
    private String name;//学生名字

    @Column(length =40)
    private String birthday;//出生日期

    @Column(length = 10)
    private String gender;//性别

    @ManyToOne(fetch = FetchType.LAZY)
    private Grade grade;//学生所在的班级

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "student_student_subject",
            joinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "subject_id", referencedColumnName = "id") })
    private List<Subject> subjectList;//所选学科

    @Formula("(select AVG(sss.score) from student_student_subject sss where sss.student_id=id)")
    private Double avgscore;//学生平均分

    public Double getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(Double avgscore) {
        this.avgscore = avgscore;
    }


    public Student() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
