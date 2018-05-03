package com.biz.lesson.business.student;

import com.biz.lesson.business.BaseService;
import com.biz.lesson.dao.student.SubjectRepository;
import com.biz.lesson.model.student.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService extends BaseService{
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> list() {
        return subjectRepository.findAll();
    }
    public List<Subject> list(String[] id) { return subjectRepository.findAll(id);}

    public void create(Subject subject) {
        subjectRepository.save(subject);
    }

    public Subject get(String id) {
        return subjectRepository.findOne(id);
    }

    public void update(Subject subject) {
        subjectRepository.save(subject);
    }

    public void delete(Subject subject) {
        subjectRepository.delete(subject);
    }
}
