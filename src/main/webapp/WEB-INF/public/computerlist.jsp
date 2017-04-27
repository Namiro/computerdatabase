<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ page
    import="com.excilys.burleon.computerdatabase.view.web.constant.Session"%>
<%@ page
    import="com.excilys.burleon.computerdatabase.view.web.constant.Data"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime"%>
<%@ taglib uri="http://burleon.excilys.com/jsp/tlds/mytags"
    prefix="mytags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="Data"
    class="com.excilys.burleon.computerdatabase.view.web.constant.Data"
    scope="application" />
<jsp:useBean id="Servlet"
    class="com.excilys.burleon.computerdatabase.view.web.constant.Servlet"
    scope="application" />
<jsp:useBean id="Session"
    class="com.excilys.burleon.computerdatabase.view.web.constant.Session"
    scope="application" />

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

<%-- Name of all servlets. --%>
<c:set var="SERVLET_COMPUTER_LIST"
    value="<%=Servlet.SERVLET_COMPUTER_LIST%>" />
<c:set var="SERVLET_COMPUTER_MANAGE"
    value="<%=Servlet.SERVLET_COMPUTER_MANAGE%>" />

<%-- The list. --%>
<c:set var="LIST_COMPUTER" value="<%=Data.LIST_COMPUTER%>" />
<c:set var="LIST_COMPANY" value="<%=Data.LIST_COMPANY%>" />

<%-- For this page. --%>
<c:set var="SEARCH_WORD" value="<%=Data.SEARCH_WORD%>" />
<c:set var="SEARCH_NUMBER_RESULTS"
    value="<%=Data.SEARCH_NUMBER_RESULTS%>" />
<c:set var="ORDER_BY" value="<%=Data.ORDER_BY%>" />
<c:set var="ORDER_BY_1" value="<%=Data.ORDER_BY_1%>" />
<c:set var="ORDER_BY_2" value="<%=Data.ORDER_BY_2%>" />
<c:set var="ORDER_BY_3" value="<%=Data.ORDER_BY_3%>" />
<c:set var="ORDER_BY_4" value="<%=Data.ORDER_BY_4%>" />
<c:set var="PAGINATION_CURRENT_PAGE"
    value="<%=Data.PAGINATION_CURRENT_PAGE%>" />
<c:set var="PAGINATION_TOTAL_PAGE"
    value="<%=Data.PAGINATION_TOTAL_PAGE%>" />
<c:set var="PAGINATION_RECORDS_BY_PAGE"
    value="<%=Data.PAGINATION_RECORDS_BY_PAGE%>" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />

<script src="//cdn.jsdelivr.net/webshim/1.14.5/polyfiller.js"></script>
<script>
    webshim.activeLang('en-AU');
    webshims.setOptions('forms-ext', {
        types : 'date'
    });
    webshims.polyfill('forms forms-ext');
</script>

<title><spring:message code="title" /></title>
</head>
<body>
    <nav class="navbar navbar-default" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle"
                data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand"
                href="${SERVLET_COMPUTER_LIST}?${SEARCH_WORD}=&${ORDER_BY}=&${PAGINATION_CURRENT_PAGE}=1&${PAGINATION_RECORDS_BY_PAGE}=20"><span
                class="glyphicon glyphicon-home"></span></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse"
            id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">

            </ul>

        </div>
        <!-- /.navbar-collapse -->
    </nav>

    <div style="background-color: #eee; padding: 30px;">
        <div class="container">

            <%-- Constants block. --%>
            <c:set var="COMPUTER_ID" value="<%=Data.COMPUTER_ID%>" />

            <%-- Content page. --%>
            <div class="row">
                <div class="container">
                    <h1 id="homeTitle">${requestScope[SEARCH_NUMBER_RESULTS]}
                        <spring:message code="computerlist_label_title" />
                    </h1>
                    <div id="actions" class="form-horizontal">
                        <div class="pull-left">
                            <form id="searchForm"
                                action="${SERVLET_COMPUTER_LIST}"
                                method="GET" class="form-inline">
                                <input type="search" id="${SEARCH_WORD}"
                                    name="${SEARCH_WORD}"
                                    class="form-control"
                                    placeholder=<spring:message code="computerlist_placeholder"/>
                                    value="${requestScope[SEARCH_WORD]}" />
                                <input type="submit"
                                    name="${SUBMIT_SEARCH}"
                                    id="${SUBMIT_SEARCH}"
                                    value=<spring:message code="button_search"/>
                                    class="btn btn-primary" /> <input
                                    type="hidden" name="${ORDER_BY}"
                                    value="${requestScope[ORDER_BY]}" />
                                <input type="hidden"
                                    name="${PAGINATION_RECORDS_BY_PAGE}"
                                    value="${requestScope[PAGINATION_RECORDS_BY_PAGE]}" />
                                <input type="hidden"
                                    name="${PAGINATION_CURRENT_PAGE}"
                                    value="${requestScope[PAGINATION_CURRENT_PAGE]}" />
                            </form>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" id="addComputer"
                                href="${SERVLET_COMPUTER_MANAGE}"><spring:message
                                    code="computerlist_button_add" /></a> <a
                                class="btn btn-default"
                                id="editComputer" href="#"
                                onclick="$.fn.toggleEditMode();"><spring:message
                                    code="computerlist_button_edit" /></a>
                        </div>
                    </div>
                </div>

                <form id="deleteForm" action="${SERVLET_COMPUTER_LIST}"
                    method="POST">
                    <input type="hidden" name="${SUBMIT_DELETE}"
                        value="">
                </form>

                <div class="container" style="margin-top: 10px;">
                    <c:choose>
                        <c:when
                            test="${not empty requestScope[LIST_COMPUTER]}">
                            <table
                                class="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th class="editMode"
                                            style="width: 60px; height: 22px;"><input
                                            type="checkbox"
                                            id="selectall" /> <span
                                            style="vertical-align: top;">
                                                - <a href="#"
                                                id="deleteSelected"
                                                onclick="$.fn.deleteSelected();">
                                                    <i
                                                    class="fa fa-trash-o fa-lg"></i>
                                            </a>
                                        </span></th>
                                        <th><a
                                            href="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${ORDER_BY_1}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=${requestScope[PAGINATION_RECORDS_BY_PAGE]}&${PAGINATION_CURRENT_PAGE}=${requestScope[PAGINATION_CURRENT_PAGE]}"><spring:message
                                                    code="computerlist_table_name" /></a></th>
                                        <th><a
                                            href="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${ORDER_BY_2}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=${requestScope[PAGINATION_RECORDS_BY_PAGE]}&${PAGINATION_CURRENT_PAGE}=${requestScope[PAGINATION_CURRENT_PAGE]}"><spring:message
                                                    code="computerlist_table_introduced" /></a></th>
                                        <th><a
                                            href="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${ORDER_BY_3}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=${requestScope[PAGINATION_RECORDS_BY_PAGE]}&${PAGINATION_CURRENT_PAGE}=${requestScope[PAGINATION_CURRENT_PAGE]}"><spring:message
                                                    code="computerlist_table_discontinued" /></a></th>
                                        <th><a
                                            href="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${ORDER_BY_4}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=${requestScope[PAGINATION_RECORDS_BY_PAGE]}&${PAGINATION_CURRENT_PAGE}=${requestScope[PAGINATION_CURRENT_PAGE]}"><spring:message
                                                    code="computerlist_table_company" /></a></th>
                                    </tr>
                                </thead>
                                <tbody id="results">
                                    <c:forEach
                                        items="${requestScope[LIST_COMPUTER]}"
                                        var="computer" varStatus="vs">
                                        <tr>
                                            <td class="editMode"><input
                                                type="checkbox"
                                                name="cb" class="cb"
                                                value='<c:out value="${computer.id}"/>'></td>
                                            <td><a
                                                href="${SERVLET_COMPUTER_MANAGE}?${COMPUTER_ID}=${computer.id}"><c:out
                                                        value="${computer.name}" /></a></td>
                                            <td><c:out
                                                    value='${computer.introduced}' />
                                            <td><c:out
                                                    value='${computer.discontinued}' />
                                            <td><c:out
                                                    value="${computer.company.name}" /></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>

                        <c:otherwise>
                            <div class="alert alert-info">
                                <p>There is no computer</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row container text-center">
                    <mytags:pagination
                        uri="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${requestScope[ORDER_BY]}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=${requestScope[PAGINATION_RECORDS_BY_PAGE]}&${PAGINATION_CURRENT_PAGE}=##"
                        maxLinks="5"
                        currPage="${requestScope[PAGINATION_CURRENT_PAGE]}"
                        totalPages="${requestScope[PAGINATION_TOTAL_PAGE]}" />

                    <div class="btn-group btn-group-sm pull-right"
                        role="group">
                        <ul class="pagination">
                            <li><a id="paginationRecordsByPage20"
                                href="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${requestScope[ORDER_BY]}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=20&${PAGINATION_CURRENT_PAGE}=${requestScope[PAGINATION_CURRENT_PAGE]}">20</a>
                            </li>
                            <li><a id="paginationRecordsByPage50"
                                href="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${requestScope[ORDER_BY]}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=50&${PAGINATION_CURRENT_PAGE}=${requestScope[PAGINATION_CURRENT_PAGE]}">50</a>
                            </li>
                            <li><a id="paginationRecordsByPage100"
                                href="${SERVLET_COMPUTER_LIST}?${ORDER_BY}=${requestScope[ORDER_BY]}&${SEARCH_WORD}=${requestScope[SEARCH_WORD]}&${PAGINATION_RECORDS_BY_PAGE}=100&${PAGINATION_CURRENT_PAGE}=${requestScope[PAGINATION_CURRENT_PAGE]}">100</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br />
    <br />
    <div id="footer">
        <div class="container">
            <p class="text-muted">
                Burléon Junior<br /> <a href="mailto:email@hotmail.com">Contact
                    the webmaster</a>
            </p>
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/dashboard.js"></script>
    <script src="js/sorttable.js"></script>
    <!-- https://www.kryogenix.org/code/browser/sorttable -->
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.js"></script>

</body>
</html>
