<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    <%@ page
    import="com.excilys.burleon.computerdatabase.webapp.constant.Session"%>
        <%@ page
    import="com.excilys.burleon.computerdatabase.webapp.constant.Data"%>
            <%@ page
    import="com.excilys.burleon.computerdatabase.webapp.constant.View"%>
                <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
                    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
                        <%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime"%>
                            <%@ taglib uri="http://burleon.excilys.com/jsp/tlds/mytags"
    prefix="mytags"%>
                                <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


                                    <%-- Content for all page. --%>
                                        <c:set var="MESSAGE_SUCCESS" value="<%=Data.MESSAGE_SUCCESS%>" />
                                        <c:set var="MESSAGE_ERROR" value="<%=Data.MESSAGE_ERROR%>" />
                                        <c:set var="MESSAGE_DEFAULT" value="<%=Data.MESSAGE_DEFAULT%>" />
                                        <c:set var="MESSAGE_INFO" value="<%=Data.MESSAGE_INFO%>" />
                                        <c:set var="MESSAGE_WARRNING" value="<%=Data.MESSAGE_WARRNING%>" />
                                        <c:set var="SUBMIT_CREATE" value="<%=Data.SUBMIT_CREATE%>" />
                                        <c:set var="SUBMIT_SAVE" value="<%=Data.SUBMIT_SAVE%>" />
                                        <c:set var="SUBMIT_DELETE" value="<%=Data.SUBMIT_DELETE%>" />
                                        <c:set var="SUBMIT_SEARCH" value="<%=Data.SUBMIT_SEARCH%>" />
                                        <c:set var="SUBMIT_SIGNUP" value="<%=Data.SUBMIT_SIGNUP%>" />
                                        <c:set var="SUBMIT_LOGIN" value="<%=Data.SUBMIT_LOGIN%>" />

                                        <%-- Name of all servlets. --%>
                                            <c:set var="VIEW_COMPUTER_LIST" value="<%=View.VIEW_COMPUTER_LIST%>" />
                                            <c:set var="VIEW_COMPUTER_MANAGE" value="<%=View.VIEW_COMPUTER_MANAGE%>" />

                                            <%-- The list. --%>
                                                <c:set var="LIST_COMPUTER" value="<%=Data.LIST_COMPUTER%>" />
                                                <c:set var="LIST_COMPANY" value="<%=Data.LIST_COMPANY%>" />

                                                <%-- For this page. --%>
                                                    <c:set var="SEARCH_WORD" value="<%=Data.SEARCH_WORD%>" />
                                                    <c:set var="SEARCH_NUMBER_RESULTS" value="<%=Data.SEARCH_NUMBER_RESULTS%>" />
                                                    <c:set var="ORDER_BY" value="<%=Data.ORDER_BY%>" />
                                                    <c:set var="ORDER_BY_1" value="<%=Data.ORDER_BY_1%>" />
                                                    <c:set var="ORDER_BY_2" value="<%=Data.ORDER_BY_2%>" />
                                                    <c:set var="ORDER_BY_3" value="<%=Data.ORDER_BY_3%>" />
                                                    <c:set var="ORDER_BY_4" value="<%=Data.ORDER_BY_4%>" />
                                                    <c:set var="PAGINATION_CURRENT_PAGE" value="<%=Data.PAGINATION_CURRENT_PAGE%>" />
                                                    <c:set var="PAGINATION_TOTAL_PAGE" value="<%=Data.PAGINATION_TOTAL_PAGE%>" />
                                                    <c:set var="PAGINATION_RECORDS_BY_PAGE" value="<%=Data.PAGINATION_RECORDS_BY_PAGE%>" />

                                                    <c:set var="USER_PASSWORD" value="<%=Data.USER_PASSWORD%>" />
                                                    <c:set var="USER_PASSWORD2" value="<%=Data.USER_PASSWORD2%>" />
                                                    <c:set var="USER_USERNAME" value="<%=Data.USER_USERNAME%>" />
                                                    <c:set var="POPUP" value="<%=Data.POPUP%>" />

                                                    <!DOCTYPE html>
                                                    <html>

                                                    <head>
                                                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                                                        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
                                                        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
                                                        <link href="css/main.css" rel="stylesheet" type="text/css" />

                                                        <title>
                                                            <spring:message code="title" />
                                                        </title>
                                                    </head>

                                                    <body>
                                                        <input name="${POPUP}" hidden="true" value="${requestScope[POPUP]}" id="${POPUP}" />
                                                        <nav class="navbar navbar-default" role="navigation">
                                                            <!-- Brand and toggle get grouped for better mobile display -->
                                                            <div class="navbar-header">
                                                                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
                                                                <a class="navbar-brand" href="${VIEW_COMPUTER_LIST}?${SEARCH_WORD}=&${ORDER_BY}=&${PAGINATION_CURRENT_PAGE}=1&${PAGINATION_RECORDS_BY_PAGE}=20"><span
                class="glyphicon glyphicon-home"></span></a>
                                                            </div>

                                                            <!-- Collect the nav links, forms, and other content for toggling -->
                                                            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                                                                <ul class="nav navbar-nav navbar-right">
                                                                    <sec:authorize access="!isAuthenticated()">
                                                                        <li><a href="#" data-toggle="modal" data-target="#myModalSignup"><span
							class="glyphicon glyphicon-user"></span> <spring:message
								code="computerlist_button_signup" /></a></li>
                                                                        <li><a href="#" data-toggle="modal" data-target="#myModalLogin"><span
							class="glyphicon glyphicon-log-in"></span> <spring:message
								code="computerlist_button_login" /></a></li>
                                                                    </sec:authorize>
                                                                    <sec:authorize access="isAuthenticated()">
                                                                        <li><a href="#" onclick="document.getElementById('logout-form').submit(); return false;"><span
							class="glyphicon glyphicon-log-out"></span> Logout</a>
                                                                    </sec:authorize>
                                                                </ul>
                                                            </div>
                                                            <form id="logout-form" action="logout" method="post">
                                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                            </form>
                                                            <!-- /.navbar-collapse -->
                                                        </nav>

                                                        <div style="background-color: #eee; padding: 30px;">
                                                            <div class="container">



                                                                <%-- MODAL Sign up --%>
                                                                    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel" id="myModalSignup">
                                                                        <form class="form-horizontal" method="POST" action="${VIEW_COMPUTER_LIST}">
                                                                            <div class="modal-dialog" role="document">
                                                                                <div class="modal-content">
                                                                                    <div class="modal-header">
                                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                                                                        <span class="h2 modal-title" id="gridSystemModalLabel"><spring:message
                                        code="computerlist_modal_signup_title" /></span>
                                                                                        <c:if test="${not empty requestScope[MESSAGE_ERROR]}">
                                                                                            <label id="${MESSAGE_ERROR}" class="alert alert-danger pull-right">${requestScope[MESSAGE_ERROR]}</label>
                                                                                        </c:if>
                                                                                    </div>
                                                                                    <div class="modal-body">
                                                                                        <fieldset>
                                                                                            <div class="form-group">
                                                                                                <div class="col-md-12">
                                                                                                    <input type="text" class="form-control" name="${USER_USERNAME}" placeholder="<spring:message code=" computerlist_modal_placeholder_username " />" />
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group">
                                                                                                <div class="col-md-12">
                                                                                                    <input type="password" class="form-control" name="${USER_PASSWORD}" placeholder="<spring:message code=" computerlist_modal_placeholder_password " />" />
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group">
                                                                                                <div class="col-md-12">
                                                                                                    <input type="password" class="form-control" name="${USER_PASSWORD2}" placeholder="<spring:message code=" computerlist_modal_placeholder_passwordrepeate " />" />
                                                                                                </div>
                                                                                            </div>
                                                                                        </fieldset>
                                                                                    </div>
                                                                                    <div class="modal-footer">
                                                                                        <input type="submit" class="btn btn-success" name="${SUBMIT_SIGNUP}" id="${SUBMIT_SIGNUP}" value="<spring:message code=" computerlist_modal_button_signup " />" style="float: right;" />
                                                                                    </div>
                                                                                </div>
                                                                                <!-- /.modal-content -->
                                                                            </div>
                                                                        </form>
                                                                        <!-- /.modal-dialog -->
                                                                    </div>
                                                                    <!-- /.modal -->


                                                                    <%-- MODAL Login --%>
                                                                        <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel" id="myModalLogin">
                                                                            <form class="form-horizontal" method="POST" action="${VIEW_COMPUTER_LIST}">
                                                                                <div class="modal-dialog" role="document">
                                                                                    <div class="modal-content">
                                                                                        <div class="modal-header">
                                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                                                                            <span class="h2 modal-title" id="gridSystemModalLabel"><spring:message
                                        code="computerlist_modal_login_title" /></span>
                                                                                            <c:if test="${not empty requestScope[MESSAGE_ERROR]}">
                                                                                                <label id="${MESSAGE_ERROR}" class="alert alert-danger pull-right">${requestScope[MESSAGE_ERROR]}</label>
                                                                                            </c:if>
                                                                                        </div>
                                                                                        <div class="modal-body">
                                                                                            <fieldset>
                                                                                                <div class="form-group">
                                                                                                    <div class="col-md-12">
                                                                                                        <input type="text" class="form-control" name="${USER_USERNAME}" placeholder="<spring:message code=" computerlist_modal_placeholder_username " />" />
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="form-group">
                                                                                                    <div class="col-md-12">
                                                                                                        <input type="password" class="form-control" name="${USER_PASSWORD}" placeholder="<spring:message code=" computerlist_modal_placeholder_password " />" />
                                                                                                    </div>
                                                                                                </div>
                                                                                            </fieldset>
                                                                                        </div>
                                                                                        <div class="modal-footer">
                                                                                            <input type="submit" class="btn btn-success" name="${SUBMIT_LOGIN}" id="${SUBMIT_LOGIN}" value="<spring:message code=" computerlist_modal_button_login " />" style="float: right;" />
                                                                                        </div>
                                                                                    </div>
                                                                                    <!-- /.modal-content -->
                                                                                </div>
                                                                            </form>
                                                                            <!-- /.modal-dialog -->
                                                                        </div>
                                                                        <!-- /.modal -->