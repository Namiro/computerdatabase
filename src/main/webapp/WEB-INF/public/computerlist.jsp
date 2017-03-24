<%-- Constants block. --%>
<c:set var="COMPUTER_ID" value="<%=Data.COMPUTER_ID%>" />
<c:set var="PAGINATION_CURRENT_PAGE"
    value="<%=Data.PAGINATION_CURRENT_PAGE%>" />
<c:set var="PAGINATION_TOTAL_PAGE"
    value="<%=Data.PAGINATION_TOTAL_PAGE%>" />
<c:set var="PAGINATION_RECORDS_BY_PAGE"
    value="<%=Data.PAGINATION_RECORDS_BY_PAGE%>" />


<%-- Content page. --%>
<div class="row">
    <div class="container">
        <h1 id="homeTitle">${requestScope[SEARCH_NUMBER_RESULTS]}
            Computers found</h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="${SERVLET_COMPUTER_LIST}"
                    method="GET" class="form-inline">
                    <input type="search" id="${SEARCH_WORD}"
                        name="${SEARCH_WORD}" class="form-control"
                        placeholder="Search name" /> <input
                        type="submit" id="${SUBMIT_SEARCH}"
                        value="Search" class="btn btn-primary" />
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer"
                    href="${SERVLET_COMPUTER_MANAGE}">Add Computer</a> <a
                    class="btn btn-default" id="editComputer" href="#"
                    onclick="$.fn.toggleEditMode();">Edit</a>
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
                            <th>Name</th>
                            <th>Introduce</th>
                            <th>Discontinue</th>
                            <th>Company</th>
                        </tr>
                    </thead>
                    <tbody>
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
            uri="${SERVLET_COMPUTER_LIST}?${PAGINATION_CURRENT_PAGE}=##"
            maxLinks="5"
            currPage="${requestScope[PAGINATION_CURRENT_PAGE]}"
            totalPages="${requestScope[PAGINATION_TOTAL_PAGE]}" />

        <form action="${SERVLET_COMPUTER_LIST}" method="GET">
            <div class="pull-right input-group ">
                <select class="form-control"
                    name="${PAGINATION_RECORDS_BY_PAGE}">
                    <option value="20"
                        <c:if test="${20 == requestScope[PAGINATION_RECORDS_BY_PAGE]}"> selected </c:if>>20</option>
                    <option value="50"
                        <c:if test="${50 == requestScope[PAGINATION_RECORDS_BY_PAGE]}"> selected </c:if>>50</option>
                    <option value="100"
                        <c:if test="${100 == requestScope[PAGINATION_RECORDS_BY_PAGE]}"> selected </c:if>>100</option>
                </select> <span class="help-block"></span> <span
                    class="input-group-btn">
                    <button class="btn btn-default" type="submit"
                        tabindex="-1">
                        <span class="glyphicon glyphicon-ok"
                            aria-hidden="true"></span>
                    </button>
                </span>
            </div>
        </form>
    </div>
</div>
