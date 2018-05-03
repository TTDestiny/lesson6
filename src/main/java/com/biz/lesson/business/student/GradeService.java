package com.biz.lesson.business.student;

import com.biz.lesson.business.BaseService;
import com.biz.lesson.dao.student.GradeRepository;
import com.biz.lesson.model.student.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService extends BaseService{
    @Autowired
    private GradeRepository gradeRepository;
    public List<Grade> list() {
       return gradeRepository.findAll();
    }

    public void create(Grade grade) {
        gradeRepository.save(grade);
    }

    public Grade get(String id) {
        return gradeRepository.findOne(id);
    }

    public void update(Grade grade) {
        gradeRepository.save(grade);
    }

    public void delete(Grade grade) {
        gradeRepository.delete(grade);
    }
}
