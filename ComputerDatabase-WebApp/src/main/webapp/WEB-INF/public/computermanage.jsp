<%@include file="../public-inc/header.jsp"%>

<%-- For this page. --%>
<c:set var="COMPUTER_ID" value="<%=Data.COMPUTER_ID%>" />
<c:set var="COMPUTER_INTRODUCE_DATE"
    value="<%=Data.COMPUTER_INTRODUCE_DATE%>" />
<c:set var="COMPUTER_DISCONTINUE_DATE"
    value="<%=Data.COMPUTER_DISCONTINUE_DATE%>" />
<c:set var="COMPUTER_NAME" value="<%=Data.COMPUTER_NAME%>" />
<c:set var="COMPUTER_COMPANY_ID" value="<%=Data.COMPUTER_COMPANY_ID%>" />

<%-- Content page. --%>
<div class="row">
    <div class="col-xs-8 col-xs-offset-2 box">
        <h1>
            <spring:message code="computermanage_title" />
        </h1>
        <form action="${VIEW_COMPUTER_MANAGE}" method="POST"
            role="form" data-toggle="validator">
            <fieldset>
            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
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
                        maxlength="250"
                        data-error="You must enter simple
                        character and max 250"
                        required> <span
                        class="glyphicon form-control-feedback"
                        aria-hidden="true"></span>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group has-feedback">
                    <label for="${COMPUTER_INTRODUCE_DATE}"><spring:message
                            code="computermanage_introduced" /></label> <input
                        type="date" class="form-control"
                        id="${COMPUTER_INTRODUCE_DATE}"
                        name="${COMPUTER_INTRODUCE_DATE}"
                        value="${requestScope[COMPUTER_INTRODUCE_DATE]}"
                        pattern="^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((1[6-9]|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))|(((((1[26]|2[048])00)|[12]\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|[12]\d))))|((([12]\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|1\d|2[0-8]))))))$"
                        data-error="You must enter a date with the format dd/MM/aaaa">
                    <span class="glyphicon form-control-feedback"
                        aria-hidden="true"></span>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group has-feedback">
                    <label for="${COMPUTER_DISCONTINUE_DATE}"><spring:message
                            code="computermanage_discontinued"/></label> <input
                        type="date" class="form-control"
                        id="${COMPUTER_DISCONTINUE_DATE}"
                        name="${COMPUTER_DISCONTINUE_DATE}"
                        value="${requestScope[COMPUTER_DISCONTINUE_DATE]}"
                        pattern="^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((1[6-9]|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((1[6-9]|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))|(((((1[26]|2[048])00)|[12]\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|[12]\d))))|((([12]\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\d|30)))|(02-(0[1-9]|1\d|2[0-8]))))))$"
                        data-error="You must enter a date with the format dd/MM/aaaa">
                    <span class="glyphicon form-control-feedback"
                        aria-hidden="true"></span>
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                    <label for="${COMPUTER_COMPANY_ID}"><spring:message
                            code="computermanage_company" /></label> <select
                        class="form-control"
                        name="${COMPUTER_COMPANY_ID}"
                        id="${COMPUTER_COMPANY_ID}">
                        <option value="0" selected
                            id="${COMPUTER_COMPANY_ID}"><spring:message
                                code="computermanage_company_placeholder" /></option>
                        <c:forEach items="${requestScope[LIST_COMPANY]}"
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

                        <input type="submit" class="btn btn-success"
                            style="float: right;" id="${SUBMIT_SAVE}"
                            name="${SUBMIT_SAVE}"
                            value=<spring:message code="button_save"/> />
                        <input type="submit" class="btn btn-danger"
                            style="float: right;" id="${SUBMIT_DELETE}"
                            name="${SUBMIT_DELETE}"
                            value=<spring:message code="button_delete"/> />
                    </c:when>
                    <c:otherwise>
                        <input type="submit" class="btn btn-primary"
                            style="float: right;" id="${SUBMIT_CREATE}"
                            name="${SUBMIT_CREATE}"
                            value=<spring:message code="button_create"/> />
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
        <c:if test="${not empty requestScope[MESSAGE_ERROR]}">
            <label id="${MESSAGE_ERROR}" class="alert alert-danger">${requestScope[MESSAGE_ERROR]}</label>
        </c:if>
        <c:if test="${not empty requestScope[MESSAGE_SUCCESS]}">
            <label id="${MESSAGE_SUCCESS}" class="alert alert-success">${requestScope[MESSAGE_SUCCESS]}</label>
        </c:if>
    </div>
</div>

<%@include file="../public-inc/footer.jsp"%>
