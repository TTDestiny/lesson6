<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lesson" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="lessonTag" uri="http://com.biz.lesson/tag/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<lesson:page title="user.title.${cmd}">
    <jsp:attribute name="script">
                <script src="/static-resource/ace/assets/js/jquery-1.8.3.js"></script>
        <script src="/static-resource/ace/assets/js/jquery.validate.js"></script>
        <script src="/static-resource/ace/assets/js/messages_zh.min.js"></script>
        <script src="/static-resource/ace/assets/js/bootstrap-datepicker.min.js"></script>
        <script type="application/javascript">
            <c:forEach items="${admin.roles}" var="role" varStatus="status">
            var obj${status.count} = document.getElementById('roleId_${role.id}');
            if (obj${status.count}) obj${status.count}.checked = true;
            </c:forEach>
            jQuery(function ($) {
                $('.date-picker').datepicker({
                    autoclose: true,
                    format: 'yyyy-mm-dd',
                    todayHighlight: true,
                    zIndex: 999
                })
                //show datepicker when clicking on the icon
                    .next().on(ace.click_event, function () {
                    $(this).prev().focus();
                });

            });


            jQuery(document).ready(function () {

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

                <li>
                    <a href="manage/users.do">
                        <spring:message code="user.title.list"/>
                    </a>
                </li>
                <li class="active">
                    <spring:message code="user.title.${cmd}"/>
                </li>
            </ul><!-- /.breadcrumb -->
        </div>

        <div class="page-content">

            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <div class="row">
                        <div class="col-xs-12">
                            <h3 class="header smaller lighter blue">
                                <spring:message code="user.title.${cmd}"/>
                                <span class="hidden-sm hidden-xs btn-group pull-right">
                                <a href="javascript:history.go(-1)" class="btn btn-sm btn-primary"><i
                                        class="ace-icon fa fa-angle-left"></i>
                                    <spring:message code="common.back"/>
                                </a>
                            </span>
                            </h3>

                            <div class="clearfix"></div>
                            <div class="user-profile row col-sm-offset-3">
                                <div class="col-xs-12 col-sm-3 center">
                                    <div>
												<span class="profile-picture">
													<img id="avatar" height="100x" width="100px"
                                                         class="editable img-responsive" alt="Logo"
                                                         src="${empty admin.logo?'static-resource/ace/assets/images/avatars/profile-pic.jpg':admin.logo}"/>
												</span>
                                        <div class="space-4"></div>

                                    </div>
                                </div>
                            </div>

                            <form action="student/student/save_${cmd}.do" method="post" class="form-horizontal"
                                  role="form"  id="add-form">
                                <input type="hidden" value="${cmd}" id="cmd"/>
                                <input type="hidden" value="${student.id}" name="id"/>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="number">
                                        学号：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" autocomplete="off" id="number" name="studentId"
                                               value="${student.studentId}"
                                               class=" form-control"/>
                                        <span> </span>
                                    </div>
                                    <label class="control-label no-padding-left" for="number" id="label_number"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="studentName">
                                        姓名：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" autocomplete="off" id="studentName" name="studentName"
                                               value="${student.name}"
                                               class=" form-control"/>
                                    </div>
                                    <label class="control-label no-padding-left" for="studentName" id="label_name"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right">
                                        性别：
                                    </label>

                                    <div class="radio">
                                        <c:if test="${ empty student.gender}">
                                            <label>
                                                <input name="gender" type="radio"  class="ace "  value="男"/>
                                                <span class="lbl">男</span>
                                            </label>
                                            <label>
                                                <input name="gender" type="radio"  class="ace"  value="女"/>
                                                <span class="lbl">女</span>
                                            </label>
                                        </c:if>
                                        <c:if test="${not empty student.gender}">
                                            <label>
                                                <input name="gender" type="radio"
                                                      <c:if test="${student.gender eq '男'}">checked="checked"</c:if>
                                                       class="ace" value="男" />
                                                <span class="lbl">男</span>
                                            </label>
                                            <label>
                                                <input name="gender" type="radio"
                                                    <c:if test="${student.gender eq '女'}">checked="checked"</c:if>
                                                       class="ace" value="女"/>
                                                <span class="lbl">女</span>
                                            </label>
                                        </c:if>
                                    </div>
                                    <label class="control-label no-padding-left" for="studentName" id="label_gender"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="birthday">
                                        出生日期：
                                    </label>
                                    <div class="col-sm-3">
                                        <div class="input-group">
                                            <input class="form-control date-picker" id="birthday"
                                                   value="${student.birthday}" name="birthday" type="text"
                                                   data-date-format="dd-mm-yyyy"/>
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                        </div>
                                    </div>
                                    <label class="control-label no-padding-left" for="studentName" id="label_birthday"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="grade">
                                        所在班级：
                                    </label>
                                    <div class="col-sm-3">
                                        <select name="gradeId" class="chosen-select form-control "
                                                id="grade" data-placeholder="请选择班级..">
                                            <option></option>
                                            <c:forEach var="grade" items="${grades}">
                                                <option value="${grade.id}"
                                                        <c:if test="${student.grade.id eq grade.id}">selected="selected"</c:if> >${grade.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="control-label no-padding-left" for="studentName" id="label_grade"></label>
                                </div>
                                <div class="clearfix form-group" style="margin-top: 50px;">
                                    <div class="col-md-offset-3 col-md-9">
                                        <button class="btn btn-info" type="submit">
                                            <i class="ace-icon fa fa-check bigger-110"></i>
                                            提交
                                        </button>
                                        &nbsp; &nbsp; &nbsp;
                                        <button class="btn" type="reset">
                                            <i class="ace-icon fa fa-undo bigger-110"></i>
                                            重置
                                        </button>
                                    </div>
                                </div>

                            </form>
                        </div><!-- /.span -->
                    </div><!-- /.row -->

                    <!-- PAGE CONTENT ENDS -->
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div>
    </jsp:body>
</lesson:page>