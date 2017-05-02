<%@include file="../public-inc/header.jsp"%>

<%-- For this page. --%>
<c:set var="COMPUTER_ID" value="<%=Data.COMPUTER_ID%>" />

<%-- Content page. --%>
<div class="row">
    <div class="container">
        <h1 id="homeTitle">${requestScope[SEARCH_NUMBER_RESULTS]}
            <spring:message code="computerlist_label_title" />
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="${SERVLET_COMPUTER_LIST}"
                    method="GET" class="form-inline">
                    <input type="search" id="${SEARCH_WORD}"
                        name="${SEARCH_WORD}" class="form-control"
                        placeholder=<spring:message code="computerlist_placeholder"/>
                        value="${requestScope[SEARCH_WORD]}" /> <input
                        type="submit" name="${SUBMIT_SEARCH}"
                        id="${SUBMIT_SEARCH}"
                        value=<spring:message code="button_search"/>
                        class="btn btn-primary" /> <input type="hidden"
                        name="${ORDER_BY}"
                        value="${requestScope[ORDER_BY]}" /> <input
                        type="hidden"
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
                    class="btn btn-default" id="editComputer" href="#"
                    onclick="$.fn.toggleEditMode();"><spring:message
                        code="computerlist_button_edit" /></a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="${SERVLET_COMPUTER_LIST}"
        method="POST">
        <input type="hidden" name="${SUBMIT_DELETE}" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <c:choose>
            <c:when test="${not empty requestScope[LIST_COMPUTER]}">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th class="editMode"
                                style="width: 60px; height: 22px;"><input
                                type="checkbox" id="selectall" /> <span
                                style="vertical-align: top;"> - <a
                                    href="#" id="deleteSelected"
                                    onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
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
                                    type="checkbox" name="cb" class="cb"
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

        <div class="btn-group btn-group-sm pull-right" role="group">
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

<%@include file="../public-inc/footer.jsp"%>



<%-- MODAL Sign up --%>
<div class="modal fade" tabindex="-1" role="dialog"
    aria-labelledby="gridSystemModalLabel" id="myModalSignup">
    <form class="form-horizontal" method="post"
        action="${SERVLET_LIST_COMPUTER}">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                        data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h2 class="modal-title" id="gridSystemModalLabel">
                        <spring:message
                            code="computerlist_modal_signup_title" />
                    </h2>
                </div>
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="text" class="form-control"
                                    name="${USER_USERNAME}"
                                    placeholder="<spring:message code="computerlist_modal_placeholder_username" />" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="password"
                                    class="form-control"
                                    name="${USER_PASSWORD}"
                                    placeholder="<spring:message code="computerlist_modal_placeholder_password" />" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="password"
                                    class="form-control"
                                    name="${USER_PASSWORD2}"
                                    placeholder="<spring:message code="computerlist_modal_placeholder_passwordrepeate" />" />
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-success"
                        name="${SUBMIT_SIGNUP}" id="${SUBMIT_SIGNUP}"
                        value="<spring:message code="computerlist_modal_button_signup" />"
                        style="float: right;" />
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
    </form>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<%-- MODAL Login --%>
<div class="modal fade" tabindex="-1" role="dialog"
    aria-labelledby="gridSystemModalLabel" id="myModalLogin">
    <form class="form-horizontal" method="post"
        action="${SERVLET_LIST_COMPUTER}">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                        data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h2 class="modal-title" id="gridSystemModalLabel">
                        <spring:message
                            code="computerlist_modal_login_title" />
                    </h2>
                </div>
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="text" class="form-control"
                                    name="${USER_USERNAME}"
                                    placeholder="<spring:message code="computerlist_modal_placeholder_username" />" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="password"
                                    class="form-control"
                                    name="${USER_PASSWORD}"
                                    placeholder="<spring:message code="computerlist_modal_placeholder_password" />" />
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-success"
                        name="${SUBMIT_LOGIN}" id="${SUBMIT_LOGIN}"
                        value="<spring:message code="computerlist_modal_button_login" />"
                        style="float: right;" />
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
    </form>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
