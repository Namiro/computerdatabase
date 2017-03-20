<%-- Constants block. --%>
<c:set var="LIST_COMPUTER" value="<%=Data.LIST_COMPUTER%>" />
<c:set var="COMPUTER_ID" value="<%=Data.COMPUTER_ID%>" />

<%-- Content page. --%>
<div class="row">
	<div class="col-md-8 col-md-offset-2">

		<c:choose>
			<c:when test="${not empty requestScope[LIST_COMPUTER]}">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Name</th>
							<th>Introduce</th> 
							<th>Discontinue</th>
							<th>Company</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope[LIST_COMPUTER]}" var="computer"
							varStatus="vs">
							<tr> 
								<td><a href="${SERVLET_COMPUTER_MANAGE}?${COMPUTER_ID}=${computer.id}">${computer.name}</a></td>
			 		 			<td><javatime:format value="${computer.introduced}" pattern="yyyy-MM-dd" style="MS" />
								<td><javatime:format value="${computer.discontinued}" pattern="yyyy-MM-dd" style="MS" />
								<td>${computer.company.name}</td>
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
<%-- 		<paginator:display maxLinks="5" currPage="${requestScope[PAGINATION_CURRENT_PAGE]}" totalPages="${PAGINATION_TOTAL_PAGE}" /> --%>
	</div>
</div>