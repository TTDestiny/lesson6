package com.biz.lesson.dao.student;

import com.biz.lesson.dao.common.CommonJpaRepository;
import com.biz.lesson.model.student.Student;
import com.biz.lesson.model.student.StudentSubject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentSubjectRepository extends CommonJpaRepository<StudentSubject, String>, StudentSubjectDao, JpaSpecificationExecutor<StudentSubject> {

    @Transactional
    void deleteBySubject_IdAndStudent_id(String subject_id,String student_id);

    @Query(value = "SELECT s.name from student s WHERE s.id IN (select sss.student_id from student_student_subject sss where sss.subject_id = ?1)",nativeQuery = true)
    List<String> findStudents(String subjecId);
}
