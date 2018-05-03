package com.biz.lesson.dao.student;

import com.biz.lesson.dao.common.CommonJpaRepositoryBean;
import com.biz.lesson.model.student.StudentSubject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class StudentSubjectRepositoryImpl extends CommonJpaRepositoryBean<StudentSubject, String> implements StudentSubjectDao {
	@Autowired
	public StudentSubjectRepositoryImpl(EntityManager em) {
		super(StudentSubject.class, em);
	}

}