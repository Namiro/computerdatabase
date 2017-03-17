<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.excilys.burleon.computerdatabase.view.web.constant.Session"%>
<%@ page import="com.excilys.burleon.computerdatabase.view.web.constant.Data" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="Data" class="com.excilys.burleon.computerdatabase.view.web.constant.Data" scope="application"/>
<jsp:useBean id="Servlet" class="com.excilys.burleon.computerdatabase.view.web.constant.Servlet" scope="application"/>
<jsp:useBean id="Session" class="com.excilys.burleon.computerdatabase.view.web.constant.Session" scope="application"/>

<%-- Content for all page. --%>
<c:set var="MESSAGE_SUCCESS" value="<%=Data.MESSAGE_SUCCESS%>" /> 
<c:set var="MESSAGE_ERROR" value="<%=Data.MESSAGE_ERROR%>" /> 
<c:set var="MESSAGE_DEFAULT" value="<%=Data.MESSAGE_DEFAULT%>" /> 
<c:set var="MESSAGE_INFO" value="<%=Data.MESSAGE_INFO%>" /> 
<c:set var="MESSAGE_WARRNING" value="<%=Data.MESSAGE_WARRNING%>" /> 
<c:set var="SUBMIT_CREATE" value="<%=Data.SUBMIT_CREATE%>" />
<c:set var="SUBMIT_SAVE" value="<%=Data.SUBMIT_SAVE%>" />
<c:set var="SUBMIT_DELETE" value="<%=Data.SUBMIT_DELETE%>" />

<%-- Name of all servlets. --%>
<c:set var="SERVLET_GAME_LIST" value="<%=Servlet.SERVLET_COMPUTER_LIST%>" /> 
<c:set var="SERVLET_COMPUTER_MANAGE" value="<%=Servlet.SERVLET_COMPUTER_MANAGE%>" /> 


<%-- For this page. --%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <title>Social Network Family Game - SNFG</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${SERVLET_WALL}"><span class="glyphicon glyphicon-home"></span></a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    
                </ul>
                
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div style="background-color: #eee; padding: 30px;">
            <div class="container">
