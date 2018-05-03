package com.biz.lesson.vo.student;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class StudentVo {
    @NotBlank(message = "学号不能为空")
    @Pattern(regexp = "^\\d{10}$",message = "学号由数字组成且长度为10")
    private String studentId;
    private String name;
    @NotBlank(message = "出生日期不能为空！")
    private String birthday;
    @NotBlank(message = "性别不能为空！")
    private String gender;
    private String id;
    private String cmd;
    @NotBlank(message = "班级不能为空！")
    private String gradeId;
    public StudentVo() {
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }



    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
