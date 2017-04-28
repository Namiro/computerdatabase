<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ page
    import="com.excilys.burleon.computerdatabase.webapp.constant.Session"%>
<%@ page
    import="com.excilys.burleon.computerdatabase.webapp.constant.Data"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime"%>
<%@ taglib uri="http://burleon.excilys.com/jsp/tlds/mytags"
    prefix="mytags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="Data"
    class="com.excilys.burleon.computerdatabase.webapp.constant.Data"
    scope="application" />
<jsp:useBean id="Servlet"
    class="com.excilys.burleon.computerdatabase.webapp.constant.Servlet"
    scope="application" />
<jsp:useBean id="Session"
    class="com.excilys.burleon.computerdatabase.webapp.constant.Session"
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
            <c:set var="COMPUTER_INTRODUCE_DATE"
                value="<%=Data.COMPUTER_INTRODUCE_DATE%>" />
            <c:set var="COMPUTER_DISCONTINUE_DATE"
                value="<%=Data.COMPUTER_DISCONTINUE_DATE%>" />
            <c:set var="COMPUTER_NAME" value="<%=Data.COMPUTER_NAME%>" />
            <c:set var="COMPUTER_COMPANY_ID"
                value="<%=Data.COMPUTER_COMPANY_ID%>" />

            <%-- Content page. --%>
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>
                        <spring:message code="computermanage_title" />
                    </h1>
                    <form action="${SERVLET_COMPUTER_MANAGE}"
                        method="POST" role="form"
                        data-toggle="validator">
                        <fieldset>
                            <input name="${COMPUTER_ID}" hidden="true"
                                value="${requestScope[COMPUTER_ID]}"
                                id="${COMPUTER_ID}" />

                            <div class="form-group has-feedback">
                                <label for="${COMPUTER_NAME}"><spring:message
                                        code="computermanage_name" /></label> <input
                                    type="text" class="form-control"
                                    id="${COMPUTER_NAME}"
                                    placeholder=<spring:message code="computermanage_name_placeholder"/>
                                    name="${COMPUTER_NAME}"
                                    value="${requestScope[COMPUTER_NAME]}"
                                    maxlength="550"
                                    data-error="You must enter simple
                        character and max 500"
                                    required> <span
                                    class="glyphicon form-control-feedback"
                                    aria-hidden="true"></span>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group has-feedback">
                                <label for="${COMPUTER_INTRODUCE_DATE}"><spring:message
                                        code="computermanage_introduced" /></label>
                                <input type="date" class="form-control"
                                    id="${COMPUTER_INTRODUCE_DATE}"
                                    name="${COMPUTER_INTRODUCE_DATE}"
                                    value="${requestScope[COMPUTER_INTRODUCE_DATE]}"
                                    pattern="^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((1[6-9]|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))|(((((1[26]|2[048])00)|[12]\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|[12]\d))))|((([12]\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|1\d|2[0-8]))))))$"
                                    data-error="You must enter a date with the format dd/MM/aaaa">
                                <span
                                    class="glyphicon form-control-feedback"
                                    aria-hidden="true"></span>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group has-feedback">
                                <label
                                    for="${COMPUTER_DISCONTINUE_DATE}"><spring:message
                                        code="computermanage_discontinued" /></label>
                                <input type="date" class="form-control"
                                    id="${COMPUTER_DISCONTINUE_DATE}"
                                    name="${COMPUTER_DISCONTINUE_DATE}"
                                    value="${requestScope[COMPUTER_DISCONTINUE_DATE]}"
                                    pattern="^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((1[6-9]|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))|(((((1[26]|2[048])00)|[12]\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|[12]\d))))|((([12]\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|1\d|2[0-8]))))))$"
                                    data-error="You must enter a date with the format dd/MM/aaaa">
                                <span
                                    class="glyphicon form-control-feedback"
                                    aria-hidden="true"></span>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label for="${COMPUTER_COMPANY_ID}"><spring:message
                                        code="computermanage_company" /></label>
                                <select class="form-control"
                                    name="${COMPUTER_COMPANY_ID}"
                                    id="${COMPUTER_COMPANY_ID}">
                                    <option value="0" selected
                                        id="${COMPUTER_COMPANY_ID}"><spring:message
                                            code="computermanage_company_placeholder" /></option>
                                    <c:forEach
                                        items="${requestScope[LIST_COMPANY]}"
                                        var="company" varStatus="as">
                                        <option value="${company.id}"
                                            <c:if test="${company.id == requestScope[COMPUTER_COMPANY_ID]}"> selected </c:if>>
                                            ${company.name}</option>
                                    </c:forEach>
                                </select> <span class="help-block"></span>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <c:choose>
                                <c:when
                                    test="${not empty requestScope[COMPUTER_ID]}">

                                    <input type="submit"
                                        class="btn btn-success"
                                        style="float: right;"
                                        id="${SUBMIT_SAVE}"
                                        name="${SUBMIT_SAVE}"
                                        value=<spring:message code="button_save"/> />
                                    <input type="submit"
                                        class="btn btn-danger"
                                        style="float: right;"
                                        id="${SUBMIT_DELETE}"
                                        name="${SUBMIT_DELETE}"
                                        value=<spring:message code="button_delete"/> />
                                </c:when>
                                <c:otherwise>
                                    <input type="submit"
                                        class="btn btn-primary"
                                        style="float: right;"
                                        id="${SUBMIT_CREATE}"
                                        name="${SUBMIT_CREATE}"
                                        value=<spring:message code="button_create"/> />
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </form>
                    <c:if
                        test="${not empty requestScope[MESSAGE_ERROR]}">
                        <label id="${MESSAGE_ERROR}"
                            class="alert alert-danger">${requestScope[MESSAGE_ERROR]}</label>
                    </c:if>
                    <c:if
                        test="${not empty requestScope[MESSAGE_SUCCESS]}">
                        <label id="${MESSAGE_SUCCESS}"
                            class="alert alert-success">${requestScope[MESSAGE_SUCCESS]}</label>
                    </c:if>
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
