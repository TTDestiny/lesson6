package com.biz.lesson.model.student;

import com.biz.lesson.model.Persistent;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "student_student_subject")
public class StudentSubject extends Persistent{

    @JoinColumn(name = "score")
    private int score;//学科分数

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;



    public StudentSubject() {
    }

    public StudentSubject(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
