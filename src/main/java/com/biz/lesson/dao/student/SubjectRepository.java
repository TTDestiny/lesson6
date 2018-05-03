package com.biz.lesson.dao.student;

import com.biz.lesson.dao.common.CommonJpaRepository;
import com.biz.lesson.model.student.Grade;
import com.biz.lesson.model.student.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CommonJpaRepository<Subject, String>, SubjectDao, JpaSpecificationExecutor<Subject> {

}
