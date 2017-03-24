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

<title>Computer Database</title>
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
            <a class="navbar-brand" href="${SERVLET_COMPUTER_LIST}"><span
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