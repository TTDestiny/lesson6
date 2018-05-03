package com.biz.lesson.web.controller.student;

import com.biz.lesson.business.student.GradeService;
import com.biz.lesson.exception.BusinessAsserts;
import com.biz.lesson.model.student.Grade;
import com.biz.lesson.model.student.Student;
import com.biz.lesson.model.user.MainMenu;
import com.biz.lesson.model.user.User;
import com.biz.lesson.vo.student.GradeVo;
import com.biz.lesson.vo.student.StudentVo;
import com.biz.lesson.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping("student/grade")
public class GradeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(GradeController.class);
    @Autowired
    private GradeService gradeService;

    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('OPT_GRADE_LIST')")
    public ModelAndView list() throws Exception {
        ModelAndView modelAndView = new ModelAndView("student/grade/list");
        List<Grade> grades = gradeService.list();
        modelAndView.addObject("grades", grades);
        return modelAndView;
    }

    @RequestMapping("/select_list")
    @PreAuthorize("hasAuthority('OPT_GRADE_LIST')")
    public ModelAndView select_list() throws Exception {
        ModelAndView modelAndView = new ModelAndView("student/student/add");
        List<Grade> grades = gradeService.list();
        modelAndView.addObject("grades", grades);
        return modelAndView;
    }

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('OPT_GRADE_ADD')")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("student/grade/add");
        modelAndView.addObject("cmd", "add");
        addReferer(request);
        return modelAndView;
    }

    @RequestMapping("/save_add")
    @PreAuthorize("hasAuthority('OPT_GRADE_ADD')")
    public ModelAndView save_add(GradeVo vo, BindingResult result, HttpServletRequest request) throws Exception {
        Grade grade = new Grade();
        copyProperties(vo, grade);
        gradeService.create(grade);
        return referer(request);
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('OPT_GRADE_EDIT')")
    public ModelAndView edit(String id, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("student/grade/add");
        Grade grade = gradeService.get(id);
        BusinessAsserts.exists(grade, id);
        modelAndView.addObject("grade", grade);
        modelAndView.addObject("cmd", "edit");
        addReferer(request);
        return modelAndView;
    }

    @RequestMapping("/save_edit")
    @PreAuthorize("hasAuthority('OPT_GRADE_EDIT')")
    public ModelAndView save_edit(GradeVo vo, BindingResult result, HttpServletRequest request) throws Exception {
        Grade grade = gradeService.get(vo.getId());
        BusinessAsserts.exists(grade, vo.getId());
        copyProperties(vo, grade);
        gradeService.update(grade);
        return referer(request);
    }

    @RequestMapping(value = "/delete",method =POST)
    @PreAuthorize("hasAuthority('OPT_GRADE_DELETE')")
    @ResponseBody
    public Boolean save_delete(@RequestParam("id") String id) {
        Grade grade = gradeService.get(id);
        try {
            gradeService.delete(grade);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
