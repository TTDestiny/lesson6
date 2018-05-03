package com.biz.lesson.vo.student;

import com.biz.lesson.web.controller.student.ScoreController;

import java.util.List;

public class StudentSubjectVo {
    private String id;
    private String[] ids;
    private String name;
    private String student_id;
    private String subject_id;
    private int score;
    private List<StudentSubjectVo> studentSubject;
    public StudentSubjectVo() {
    }

    public StudentSubjectVo(String id, String[] ids, String name, String student_id, String subject_id, int score) {
        this.id = id;
        this.ids = ids;
        this.name = name;
        this.student_id = student_id;
        this.subject_id = subject_id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<StudentSubjectVo> getStudentSubjectVoList() {
        return studentSubject;
    }

    public void setStudentSubjectVoList(List<StudentSubjectVo> studentSubject) {
        this.studentSubject = studentSubject;
    }
}
