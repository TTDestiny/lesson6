<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="lesson" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="lessonTag" uri="http://com.biz.lesson/tag/core" %>
<lesson:page title="user.title.list">
    <jsp:attribute name="css">
        <style type="text/css">
            #name-of-ban-user, #name-of-reset-user {
                font-weight: bold;
                color: red;
            }

            #password-not-match-msg {
                display: none;
                color: #a94442;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="script">

        <script type="application/javascript">
            jQuery(function($) {
                //datepicker plugin
                $('.input-daterange').datepicker(
                    {
                        autoclose:true,
                        format: 'yyyy-mm-dd',
                        todayHighlight: true
                    });
                //to translate the daterange picker, please copy the "examples/daterange-fr.js" contents here before initialization
            });
        </script>

    </jsp:attribute>
    <jsp:body>
        <div class="breadcrumbs ace-save-state" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="welcome.do">
                        <spring:message code="common.homepage"/>
                    </a>
                </li>
                <li class="active">
                    <spring:message code="user.title.list"/>
                </li>
            </ul><!-- /.breadcrumb -->
        </div>

        <div class="page-content">
            <input type="hidden" id="id-of-user">
            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <div class="row">
                        <div class="col-xs-12">
                            <h3 class="header smaller lighter blue">
                                <spring:message code="user.title.list"/>
                                <span class=" btn-group pull-right">
                                <sec:authorize ifAnyGranted="OPT_USER_ADD">
                                    <a href="/student/student/add.do" class="btn btn-sm btn-primary"><i
                                            class="ace-icon glyphicon glyphicon-plus"></i>
                                        <spring:message code="button.add"/>
                                    </a>
                                </sec:authorize>
                            	</span>
                            </h3>
                            <div >
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h4 class="widget-title">学生搜索</h4>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <form action="/student/student/search.do" class="form-inline">
                                                <label class="inline" for="studentNumber">
                                                   学生学号：
                                                </label>
                                                <input type="text" name="studentId" id="studentNumber" value="${studentId}" autocomplete="off" class="input-sm" placeholder="学号" />
                                                <label class="inline" for="studentName">
                                                    学生姓名：
                                                </label>
                                                <input type="text" name="name" id="studentName" value="${name}"  class="input-sm" autocomplete="off" placeholder="姓名" />
                                                <label class="inline" for="studentName">
                                                    出生日期范围：
                                                </label>
                                               <div class="inline">
                                                   <div class="input-daterange input-group ">
                                                       <input type="text"   class="input-sm form-control" value="${starDate}" name="starDate" />
                                                       <span class="input-group-addon">
																		<i class="fa fa-exchange"></i>
																	</span>
                                                       <input type="text" class="input-sm form-control" value="${endDate}" name="endDate" />
                                                   </div>
                                               </div>

                                                <button type="submit" class="btn btn-info btn-sm">
                                                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                                    Search
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            <table id="dynamic-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><spring:message code="students.number"/></th>
                                    <th><spring:message code="students.name"/></th>
                                    <th><spring:message code="students.gender"/></th>
                                    <th><spring:message code="students.birthday"/></th>
                                    <th><spring:message code="students.grade"/></th>
                                    <th><spring:message code="students.count_subject"/></th>
                                    <th><spring:message code="students.avgscore"/></th>
                                    <th></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${students}" var="student">
                                    <c:set value="${counts.get(student.id)}" var="count"/>
                                    <tr id="tr-${student.id}">
                                        <td>${student.studentId}</td>
                                        <td>${student.name}</td>
                                        <td>${student.gender}</td>
                                        <td>${student.birthday}</td>
                                        <td>${student.grade.name}</td>
                                        <td>${count}</td>
                                        <td>
                                            <c:if test="${not empty student.avgscore && student.avgscore !=0}">
                                                <fmt:formatNumber value="${student.avgscore}"
                                                                  maxFractionDigits="2">
                                                </fmt:formatNumber>
                                            </c:if>
                                            <c:if test="${empty student.avgscore || student.avgscore == 0}">
                                                0.00
                                            </c:if>
                                        </td>

                                        <td>
                                            <div class="hidden-sm hidden-xs action-buttons">
                                                <sec:authorize ifAnyGranted="OPT_SCORE_ADD">
                                                    <a title="分数录入" class="purple"
                                                       href="/student/score/add.do?id=${student.id}">
                                                        <i class="ace-icon fa fa-pencil bigger-130"></i>
                                                    </a>
                                                </sec:authorize>
                                                <sec:authorize ifAnyGranted="OPT_STUDENT_ADD_SUBJECT">
                                                    <a title="选课" class="blue"
                                                       href="/student/student/addSubjects.do?id=${student.id}">
                                                        <i class=" ace-icon fa fa-book bigger-130"></i>
                                                    </a>
                                                </sec:authorize>
                                                <sec:authorize ifAnyGranted="OPT_STUDENT_EDIT">
                                                    <a title="编辑" class="green"
                                                       href="/student/student/edit.do?id=${student.id}">
                                                        <i class="ace-icon fa  fa-pencil-square-o bigger-130"></i>
                                                    </a>
                                                </sec:authorize>
                                                <sec:authorize ifAnyGranted="OPT_STUDENT_DELETE">
                                                    <a title="删除" class="btn-delete-modal red"
                                                       data-url="/student/student/delete.do"
                                                       data-id="${student.id}">
                                                        <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                                    </a>
                                                </sec:authorize>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                        </div><!-- /.span -->
                    </div><!-- /.row -->

                    <!-- PAGE CONTENT ENDS -->
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div>
    </jsp:body>
</lesson:page>
