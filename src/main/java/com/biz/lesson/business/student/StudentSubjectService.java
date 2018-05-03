package com.biz.lesson.business.student;

import com.biz.lesson.business.BaseService;
import com.biz.lesson.dao.student.StudentSubjectRepository;
import com.biz.lesson.model.student.Student;
import com.biz.lesson.model.student.StudentSubject;
import com.biz.lesson.model.student.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;


@Service
public class StudentSubjectService extends BaseService {

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    public void create(StudentSubject studentSubject) {
        studentSubjectRepository.save(studentSubject);
    }


    public List<StudentSubject> list(Student studentParameter) {
        List<StudentSubject> studentSubjectList = studentSubjectRepository.findAll(new Specification<StudentSubject>() {
            @Override
            public Predicate toPredicate(Root<StudentSubject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Student> student = root.get("student");
                Predicate predicate = criteriaBuilder.equal(student.get("id"), studentParameter.getId());
                return predicate;
            }
        });
        return studentSubjectList;
    }

    public void delete(String subject_id,String studnet_id) {
        studentSubjectRepository.deleteBySubject_IdAndStudent_id(subject_id,studnet_id);
    }

    public StudentSubject get(String subjectId, String studentId) {
        return studentSubjectRepository.findOne(new Specification<StudentSubject>() {
            @Override
            public Predicate toPredicate(Root<StudentSubject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Subject> subject = root.get("subject");
                Path<Student> student = root.get("student");
                Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(subject.get("id"), subjectId), criteriaBuilder.equal(student.get("id"), studentId));
                return predicate;
            }
        });
    }

    public long count(String studentId) {

        long count = studentSubjectRepository.count(new Specification<StudentSubject>() {
            @Override
            public Predicate toPredicate(Root<StudentSubject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Student> student = root.get("student");
                Predicate predicate = criteriaBuilder.equal(student.get("id"), studentId);
                return predicate;
            }
        });
        return count;
    }




    public void update(StudentSubject studentSubject) {
        studentSubjectRepository.save(studentSubject);
    }

    public List<String> list(String subjectId){
        return   studentSubjectRepository.findStudents(subjectId);
    }

}

