<%-- Constants block. --%>
<c:set var="LIST_COMPUTER" value="<%=Data.LIST_COMPUTER%>" />

<%-- Content page. --%>
<div class="row">
	<div class="col-md-8 col-md-offset-2">

		<c:choose>
			<c:when test="${not empty requestScope[LIST_COMPUTER]}">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Name Game</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope[LIST_COMPUTER]}" var="computer"
							varStatus="vs">
							<tr>
								<td>${computer.name}</td>
								<td>${computer.intoduced}</td>
								<td>${computer.discontinued}</td>
								<td>${computer.company.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>

			<c:otherwise>
				<div class="alert alert-info">
					<p>There is no games</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>