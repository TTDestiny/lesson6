package com.biz.lesson.business.student;

import com.biz.lesson.business.BaseService;
import com.biz.lesson.dao.student.StudentRepository;
import com.biz.lesson.model.student.Student;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentService extends BaseService{
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

   public List<Student> list() {
       return studentRepository.findAll();

    }

    public void create(Student student){
       studentRepository.save(student);
    }

    public Student get(String id) {
       return studentRepository.findOne(id);
    }

    public void update(Student student) {
        studentRepository.save(student);
    }


    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public List<Student> list(String[] ids) {
        return studentRepository.findAll(ids);
    }

    public List<Student> search(String studentId, String name, String starDate, String endDate) {
        return studentRepository.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                Path<String> number = root.get("studentId");
                Path<String> studentName = root.get("name");
                Path<String> birthday = root.get("birthday");
                if (StringUtils.isNotBlank(studentId)){
                    predicates.add(criteriaBuilder.like(number, studentId + "%"));
                }
                if (StringUtils.isNotBlank(name)){
                    predicates.add(criteriaBuilder.like(studentName,"%"+name+"%"));
                }if (StringUtils.isNotBlank(starDate) && StringUtils.isNotBlank(endDate)){
                    predicates.add(criteriaBuilder.between(birthday,starDate,endDate));
                }
                Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                return predicate;
            }
        });
    }

}
