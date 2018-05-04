package com.biz.lesson.web.controller.student;

import com.biz.lesson.business.student.GradeService;
import com.biz.lesson.business.student.StudentService;
import com.biz.lesson.business.student.StudentSubjectService;
import com.biz.lesson.business.student.SubjectService;
import com.biz.lesson.exception.BusinessAsserts;
import com.biz.lesson.model.student.Grade;
import com.biz.lesson.model.student.Student;
import com.biz.lesson.model.student.StudentSubject;
import com.biz.lesson.model.student.Subject;
import com.biz.lesson.util.PageControl;
import com.biz.lesson.vo.student.StudentSubjectVo;
import com.biz.lesson.vo.student.StudentVo;
import com.biz.lesson.web.controller.BaseController;
import com.octo.captcha.engine.sound.utils.SoundToFile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("student/student")
public class StudentController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentSubjectService studentSubjectService;

    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('OPT_STUDENT_LIST')")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("student/student/list");
        //分页
        PageControl pc = new PageControl(request,2);
        PageRequest pageRequest = new PageRequest(pc.getCurrentPage() - 1, pc.getPageSize());
        Page<Student> page = studentService.list(pageRequest);
        Map<String,Long> counts = new HashMap<>();
        for(Student student : page.getContent()){
            long count = studentSubjectService.count(student.getId());
            counts.put(student.getId(),count);
        }
        modelAndView.addObject("page", page);
        modelAndView.addObject("counts", counts);
        return modelAndView;
    }

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('OPT_STUDENT_ADD')")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("student/student/add");
        List<Grade> grades = gradeService.list();
        modelAndView.addObject("grades", grades);
        modelAndView.addObject("cmd", "add");
        addReferer(request);
        return modelAndView;
    }

    @RequestMapping("/save_add")
    @PreAuthorize("hasAuthority('OPT_STUDENT_ADD')")
    public ModelAndView save_add( StudentVo vo, BindingResult result, HttpServletRequest request) throws Exception {
        Student student = new Student();
        Grade grade = gradeService.get(vo.getGradeId());
        student.setGrade(grade);
        copyProperties(vo, student);
        studentService.create(student);
        return referer(request);
    }


    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('OPT_STUDENT_EDIT')")
    public ModelAndView edit(String id, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("student/student/add");
        Student student = studentService.get(id);
        BusinessAsserts.exists(student, id);
        List<Grade> grades = gradeService.list();
        modelAndView.addObject("grades", grades);
        modelAndView.addObject("student", student);
        modelAndView.addObject("cmd", "edit");
        addReferer(request);
        return modelAndView;
    }

    @RequestMapping("/save_edit")
    @PreAuthorize("hasAuthority('OPT_STUDENT_EDIT')")
    public ModelAndView save_edit(StudentVo vo, BindingResult result, HttpServletRequest request) throws Exception {
        Student student = studentService.get(vo.getId());
        Grade grade = gradeService.get(vo.getGradeId());
        BusinessAsserts.exists(student, vo.getId());
        BusinessAsserts.exists(grade,vo.getGradeId());
        copyProperties(vo,student);
        student.setGrade(grade);
        studentService.update(student);
        return referer(request);
    }

    @RequestMapping(value = "delete", method = POST)
    @PreAuthorize("hasAuthority('OPT_STUDENT_DELETE')")
    @ResponseBody
    public Boolean save_delete(@RequestParam("id") String studentId) {
        Student student = studentService.get(studentId);
        try {
            studentService.delete(student);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @RequestMapping("/addSubjects")
    @PreAuthorize("hasAuthority('OPT_STUDENT_ADD_SUBJECT')")
    public ModelAndView getSubjects(String id, HttpServletRequest request) {
        //获取所有学科
        List<Subject> subjects = subjectService.list();
        ModelAndView modelAndView = new ModelAndView("student/subject/addSubject");
        Student student = studentService.get(id);
        //获取该学生所选的课程
        List<Subject> selectedSubjects = new ArrayList<>();
        List<StudentSubject> studentSubjectList = studentSubjectService.list(student);
        if (studentSubjectList.size() != 0) {
            for (StudentSubject studentSubjects : studentSubjectList) {
                Subject subject = studentSubjects.getSubject();
                selectedSubjects.add(subject);
            }
        }
        modelAndView.addObject("selectedSubjects", selectedSubjects);
        modelAndView.addObject("subjects", subjects);
        modelAndView.addObject("cmd", "addSubjects");
        modelAndView.addObject("student", student);

        addReferer(request);
        return modelAndView;
    }

    @RequestMapping("/save_addSubjects")
    @PreAuthorize("hasAuthority('OPT_STUDENT_ADD_SUBJECT')")
    public ModelAndView save_getSubjects(StudentSubjectVo vo, BindingResult result, HttpServletRequest request) throws Exception {
        Student student = studentService.get(vo.getStudent_id());
        //查看已经选择的课程
        List<StudentSubject> studentSubjectList = studentSubjectService.list(student);
        List<Subject> subjects = new ArrayList<>();
        if (studentSubjectList.size() != 0){
            for (StudentSubject studentSubject : studentSubjectList) {
                Subject subject = studentSubject.getSubject();
                subjects.add(subject);
            }
            //退选课程
            if (vo.getIds() != null && subjects.size() >= vo.getIds().length){
                List<String> stringIds = Arrays.asList(vo.getIds());
                for (Subject subject : subjects) {
                    if (!stringIds.contains(subject.getId())){
                        studentSubjectService.delete(subject.getId(),vo.getStudent_id());
                    }
                }
            }else {
                for (Subject subject : subjects) {
                    studentSubjectService.delete(subject.getId(),vo.getStudent_id());
                }
            }
        }
        //课程添加
        if (vo.getIds() !=null ) {
            for (String subjectId : vo.getIds()) {
                StudentSubject studentSubject = new StudentSubject();
                studentSubject.setStudent(student);
                //判断是否已经填加该课程
                StudentSubject studentSubjectById = studentSubjectService.get(subjectId,vo.getStudent_id());
                if (subjects.size() != 0 && studentSubjectById != null &&  subjects.contains(studentSubjectById.getSubject())) {
                    continue;
                } else {
                    studentSubject.setSubject(subjectService.get(subjectId));
                    studentSubjectService.create(studentSubject);
                }
            }
        }
        return referer(request);
    }

    /**
     * 学生搜索
     * @param studentId
     * @param name
     * @param starDate
     * @param endDate
     * @return
     */
    @RequestMapping("/search")
    public ModelAndView searchStudent(String studentId,String name,String starDate,String endDate,HttpServletRequest request){
        PageControl pc = new PageControl(request,2 );
        PageRequest pageRequest = new PageRequest(pc.getCurrentPage() - 1, pc.getPageSize());
        Page<Student> page = studentService.search(studentId, name, starDate, endDate,pageRequest);
        ModelAndView modelAndView = new ModelAndView("/student/student/list");
        Map<String,Long> counts = new HashMap<>();
        for(Student student : page.getContent()){
            long count = studentSubjectService.count(student.getId());
            counts.put(student.getId(),count);
        }
        modelAndView.addObject("page", page);
        modelAndView.addObject("counts", counts);
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("name", name);
        modelAndView.addObject("starDate", starDate);
        modelAndView.addObject("endDate", endDate);
        return modelAndView;
    }

}
