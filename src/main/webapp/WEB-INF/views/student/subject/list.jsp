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
                                    <a href="/student/subject/add.do" class="btn btn-sm btn-primary"><i
                                            class="ace-icon glyphicon glyphicon-plus"></i>
                                        <spring:message code="button.add"/>
                                    </a>
                                </sec:authorize>
                            	</span>
                            </h3>
                            <table id="dynamic-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>学科名</th>
                                    <th>选修人</th>
                                    <th>平均分</th>
                                    <th></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="subject" items="${subjects}">
                                    <tr id="tr-${subject.id}">
                                        <td>${subject.name}</td>
                                        <td>
                                            <c:if test="${not empty selectedStudent.get(subject.id)}">

                                                <select class="form-control" style="height: 50px;" multiple="multiple">
                                                    <c:forEach var="name" items="${selectedStudent.get(subject.id)}">
                                                        <option>${name}</option>
                                                    </c:forEach>
                                                </select>

                                            </c:if>
                                            <c:if test="${empty selectedStudent.get(subject.id)}">
                                                无
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty subject.avgScore}">
                                            <fmt:formatNumber value="${subject.avgScore}"  maxFractionDigits="2">

                                            </fmt:formatNumber>
                                            </c:if>
                                            <c:if test="${empty subject.avgScore}">
                                               0.00
                                            </c:if>
                                        </td>
                                        <td>
                                            <div class="hidden-sm hidden-xs action-buttons">
                                                <sec:authorize ifAnyGranted="OPT_SUBJECT_ADD">
                                                    <a title="编辑" class="green"
                                                       href="/student/subject/edit.do?id=${subject.id}">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-130"></i>
                                                    </a>
                                                </sec:authorize>
                                                <sec:authorize ifAnyGranted="OPT_SUBJECT_DELETE">
                                                    <a title="删除" class="red btn-delete-modal" data-id="${subject.id}"
                                                       data-title="<spring:message code="button.disable"/>"
                                                       data-url="/student/subject/delete.do">
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
