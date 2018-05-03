package com.biz.lesson.web.controller.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.biz.lesson.business.student.StudentService;
import com.biz.lesson.business.student.StudentSubjectService;
import com.biz.lesson.business.student.SubjectService;
import com.biz.lesson.model.student.Grade;
import com.biz.lesson.model.student.Student;
import com.biz.lesson.model.student.StudentSubject;
import com.biz.lesson.vo.student.StudentSubjectVo;
import com.biz.lesson.vo.student.StudentVo;
import com.biz.lesson.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("student/score")
public class ScoreController extends BaseController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentSubjectService studentSubjectService;

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('OPT_SCORE_ADD')")
    public ModelAndView add(String id, HttpServletRequest request) {
        Student student = studentService.get(id);
        List<StudentSubject> studentSubjectList = studentSubjectService.list(student);
        ModelAndView modelAndView = new ModelAndView("student/score/add");
        modelAndView.addObject("cmd", "add");
        modelAndView.addObject("student_id", id);
        modelAndView.addObject("studentSubjects", studentSubjectList);
        addReferer(request);
        return modelAndView;
    }

    @RequestMapping(value = "/save_add", method = POST)
    @PreAuthorize("hasAuthority('OPT_SCORE_ADD')")
    @ResponseBody
    public Boolean save_add(@RequestBody Map<String, List<StudentSubjectVo>> map, BindingResult result, HttpServletRequest request) throws Exception {
        boolean flag=false;
        for (Map.Entry<String, List<StudentSubjectVo>> entry : map.entrySet()) {
            List<StudentSubjectVo> subjectVos = entry.getValue();
            for (StudentSubjectVo studentSubjectVo : subjectVos) {
                StudentSubject studentSubject = studentSubjectService.get(studentSubjectVo.getSubject_id(), studentSubjectVo.getStudent_id());
                try {
                    if (studentSubject!=null) {
                        studentSubject.setScore(studentSubjectVo.getScore());
                        studentSubjectService.update(studentSubject);
                        flag = true;
                    }
                }catch (Exception e){
                   return flag;
                }
            }
        }
        return flag;
    }

}
