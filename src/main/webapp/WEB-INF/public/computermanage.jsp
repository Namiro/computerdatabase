<%-- Constants block. --%>
<c:set var="LIST_COMPUTER" value="<%=Data.LIST_COMPUTER%>" />
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
		<h1>Manage Computer</h1>
		<form action="${SERVLET_COMPUTER_MANAGE}" method="POST">
			<fieldset>
				<div class="form-group">
					<label for="computerName">Computer name</label> <input type="text"
						class="form-control" id="${COMPUTER_NAME}" name="${COMPUTER_NAME}" value="${requestScope[COMPUTER_NAME]}"
						placeholder="Computer name"> <span class="help-block"></span>
				</div>
				<div class="form-group">
					<label for="introduced">Introduced date</label> <input type="date"
						class="form-control" id="${COMPUTER_INTRODUCE_DATE}"
						name="${COMPUTER_INTRODUCE_DATE}" placeholder="Introduced date"
						value="${requestScope[COMPUTER_INTRODUCE_DATE]}"> <span
						class="help-block"></span>
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date</label> <input
						type="date" class="form-control" id="${COMPUTER_DISCONTINUE_DATE}"
						placeholder="Discontinued date"
						name="${COMPUTER_DISCONTINUE_DATE}"
						value="${requestScope[COMPUTER_DISCONTINUE_DATE]}"> <span
						class="help-block"></span>
				</div>
				<div class="form-group">
					<select class="form-control" name="${LIST_COMPANY}">
						<option value="0" disabled selected>The company</option>
						<c:forEach items="${requestScope[LIST_COMPANY]}" var="company"
                            varStatus="vs">
							<option value="${company.id}"
								<c:if test="${company.id == requestScope[COMPUTER_COMPANY_ID]}"> selected </c:if>>
								${company.name}</option> 
						</c:forEach>
					</select> 
					<span class="help-block"></span>
				</div>
			</fieldset>
			<div class="actions pull-right">
				<c:choose>
					<c:when test="${not empty requestScope[COMPUTER_ID]}">
						<input type="submit" class="btn btn-success" style="float: right;"
							name="${SUBMIT_SAVE}" value="Save" />
						<input type="submit" class="btn btn-danger" style="float: right;"
							name="${SUBMIT_DELETE}" value="Delete" />
					</c:when>
					<c:otherwise>
						<input type="submit" class="btn btn-primary" style="float: right;"
							name="${SUBMIT_CREATE}" value="Create" />
					</c:otherwise>
				</c:choose>
			</div>
		</form>
		<c:if test="${not empty requestScope[MESSAGE_ERROR]}">
			<label class="alert alert-danger">${requestScope[MESSAGE_ERROR]}</label>
		</c:if>
		<c:if test="${not empty requestScope[MESSAGE_SUCCESS]}">
			<label class="alert alert-success">${requestScope[MESSAGE_SUCCESS]}</label>
		</c:if>
	</div>
</div>