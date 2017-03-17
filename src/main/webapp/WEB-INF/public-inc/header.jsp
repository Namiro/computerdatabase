<%@page import="com.sgs.webclient.constant.Session"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.sgs.webclient.constant.Data" %>
<jsp:useBean id="Data" class="com.sgs.webclient.constant.Data" scope="application"/>
<jsp:useBean id="Servlet" class="com.sgs.webclient.constant.Servlet" scope="application"/>
<jsp:useBean id="Session" class="com.sgs.webclient.constant.Session" scope="application"/>
<jsp:useBean id="Utility" class="com.sgs.service.tool.Utility" scope="application"/>

<%-- Content for all page. --%>
<c:set var="MESSAGE_SUCCESS" value="<%=Data.MESSAGE_SUCCESS%>" /> 
<c:set var="MESSAGE_ERROR" value="<%=Data.MESSAGE_ERROR%>" /> 
<c:set var="MESSAGE_DEFAULT" value="<%=Data.MESSAGE_DEFAULT%>" /> 
<c:set var="MESSAGE_INFO" value="<%=Data.MESSAGE_INFO%>" /> 
<c:set var="MESSAGE_WARRNING" value="<%=Data.MESSAGE_WARRNING%>" /> 
<c:set var="SUBMIT_CREATE" value="<%=Data.SUBMIT_CREATE%>" />
<c:set var="SUBMIT_SAVE" value="<%=Data.SUBMIT_SAVE%>" />
<c:set var="SUBMIT_DELETE" value="<%=Data.SUBMIT_DELETE%>" />
<c:set var="SUBMIT_MEET" value="<%=Data.SUBMIT_MEET%>" />

<c:set var="SESSION_PLAYER" value="<%=Session.PLAYER%>" />
<c:set var="FILE_PICTURE_ANONYMOUS" value="<%=Data.FILE_PICTURE_ANONYMOUS%>" />

<%-- Name of all servlets. --%>
<c:set var="SERVLET_AUTHENTIFICATION" value="<%=Servlet.SERVLET_AUTHENTIFICATION%>" /> 


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
                    <c:choose>
                        <c:when test="${not empty sessionScope[SESSION_PLAYER]}">
                            <%-- Add the test for choosing player or administrator. --%>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope[SESSION_PLAYER].firstname} ${sessionScope[SESSION_PLAYER].name} <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="${SERVLET_PLAYER}?${PLAYER_ID}=${sessionScope[SESSION_PLAYER].id}"><span class="glyphicon glyphicon-th-large"></span> Wall</a></li>
                                    <li><a href="${SERVLET_PROFIL_MANAGE}"><span class="glyphicon glyphicon-user"></span> Profil</a></li>
                                    
                                    <li><a href="${SERVLET_LIST_FAVORITE}"> Favoris </a></li>
                                </ul>
                            </li>
                            <li><a href="${SERVLET_MESSENGER}"><span class="glyphicon glyphicon-envelope"></span> Messenger</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Organization <b class="caret"></b></a>
                                <ul class="dropdown-menu">


                                    <c:if test="${sessionScope[SESSION_PLAYER].accessLevel == 'ORGANIZATION' or (sessionScope[SESSION_PLAYER].accessLevel == 'ADMINISTRATOR' and not empty sessionScope[SESSION_PLAYER].organization)}">
                                        <li><a href="${SERVLET_ORGANIZATION}?${ORGANIZATION_ID}=${sessionScope[SESSION_PLAYER].organization.id}"><span class="glyphicon glyphicon-th-large"></span> Wall</a></li>
                                    </c:if>
                                    <li><a href="${SERVLET_ORGANIZATION_MANAGE}"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
                                    <c:if test="${sessionScope[SESSION_PLAYER].accessLevel == 'ORGANIZATION' or (sessionScope[SESSION_PLAYER].accessLevel == 'ADMINISTRATOR' and not empty sessionScope[SESSION_PLAYER].organization)}">
                                        <li><a href="${SERVLET_GAME_TABLE}">Game Table</a></li>
                                    </c:if>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Meetings <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="${SERVLET_MEETING_MANAGE}">Meeting</a></li>
                                    <li><a href="${SERVLET_MEETING_LIST}">Meetings list</a></li>
                                    <li><a href="${SERVLET_MY_MEETING}">My mettings</a></li>
                                </ul>
                            </li>

                            <c:if test="${sessionScope[SESSION_PLAYER].accessLevel == 'ADMINISTRATOR'}">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Administration <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="${SERVLET_GAME_MANAGE}">Game</a></li>
                                    </ul>
                                </li>    
                            </c:if>


                        </c:when>
                        <c:otherwise>
                            <li><a href="${SERVLET_REGISTER}">Register</a></li>
                            <li><a href="${SERVLET_AUTHENTIFICATION}">Log in</a></li>
                            <li><a href="${SERVLET_GAME_LIST}">Game list</a></li>
                            </c:otherwise>
                        </c:choose>
                </ul>
                <c:if test="${not empty sessionScope[SESSION_PLAYER]}">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${SERVLET_LOGOUT}"><span class="glyphicon glyphicon-off"></span> Log out</a></li>
                    </ul>
                    <form class="navbar-form navbar-right" role="search" method="post" action="${SERVLET_SEARCH_PLAYER_ORGANIZATION}">
                        <div class="form-group">
                            <input type="text" name="${SEARCH_FORM}" class="form-control" placeholder="Search player or organization" />
                        </div>
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> Search</button>
                    </form>
                </c:if>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div style="background-color: #eee; padding: 30px;">
            <div class="container">
