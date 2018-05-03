package com.biz.lesson.web.controller.student;

import com.biz.lesson.business.student.GradeService;
import com.biz.lesson.business.student.StudentSubjectService;
import com.biz.lesson.business.student.SubjectService;
import com.biz.lesson.exception.BusinessAsserts;
import com.biz.lesson.model.student.Grade;
import com.biz.lesson.model.student.Student;
import com.biz.lesson.model.student.StudentSubject;
import com.biz.lesson.model.student.Subject;
import com.biz.lesson.vo.student.GradeVo;
import com.biz.lesson.vo.student.SubjectVo;
import com.biz.lesson.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("student/subject")
public class SubjectController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentSubjectService studentSubjectService;

    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('OPT_SUBJECT_LIST')")
    public ModelAndView list() throws Exception {
        ModelAndView modelAndView = new ModelAndView("student/subject/list");
        List<Subject> subjects = subjectService.list();
        Map<String,List<String>> selectedStudent = new HashMap<>();
        for (Subject subject : subjects){
            List<String> students = studentSubjectService.list(subject.getId());
          selectedStudent.put(subject.getId(),students);
        }
        modelAndView.addObject("subjects", subjects);
        modelAndView.addObject("selectedStudent", selectedStudent);
        return modelAndView;
    }

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('OPT_SUBJECT_ADD')")
    public ModelAndView add(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("student/subject/add");
        modelAndView.addObject("cmd", "add");
        addReferer(request);
        return modelAndView;
    }

    @RequestMapping("/save_add")
    @PreAuthorize("hasAuthority('OPT_SUBJECT_ADD')")
    public ModelAndView save_add(SubjectVo vo, BindingResult result, HttpServletRequest request) throws Exception {
        Subject subject = new Subject();
        copyProperties(vo, subject);
        subjectService.create(subject);
        return referer(request);
    }
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('OPT_SUBJECT_EDIT')")
    public ModelAndView edit(String id, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("student/subject/add");
        Subject subject = subjectService.get(id);
        BusinessAsserts.exists(subject,id);
        modelAndView.addObject("subject", subject);
        modelAndView.addObject("cmd", "edit");
        addReferer(request);
        return modelAndView;
    }

    @RequestMapping("/save_edit")
    @PreAuthorize("hasAuthority('OPT_SUBJECT_EDIT')")
    public ModelAndView save_edit(SubjectVo vo,BindingResult result, HttpServletRequest request) throws Exception {
        Subject subject = subjectService.get(vo.getId());
        BusinessAsserts.exists(subject, vo.getId());
        copyProperties(vo, subject);
        subjectService.update(subject);
        return referer(request);
    }
    @RequestMapping(value = "delete", method = POST)
    @PreAuthorize("hasAuthority('OPT_SUBJECT_DELETE')")
    @ResponseBody
    public Boolean save_delete(@RequestParam("id") String subjectId) {
        Subject subject = subjectService.get(subjectId);
        try {
            subjectService.delete(subject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
