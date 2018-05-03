package com.biz.lesson.dao.student;

import com.biz.lesson.dao.common.CommonJpaRepository;
import com.biz.lesson.model.student.Student;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends CommonJpaRepository<Student, String>, StudentDao, JpaSpecificationExecutor<Student> {

}
