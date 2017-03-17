<%-- Constants block. --%>
    <c:set var="LIST_GAME" value="<%=Data.LIST_GAME%>" />

    <%-- Content page. --%>
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Manage Computer</h1>
                <form action="editComputer" method="POST">
                    <input type="hidden" value="0" id="id" />
                    <!-- TODO: Change this value with the computer id -->
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName">Computer name</label>
                            <input type="text" class="form-control" id="computerName" placeholder="Computer name">
                        </div>
                        <div class="form-group">
                            <label for="introduced">Introduced date</label>
                            <input type="date" class="form-control" id="introduced" placeholder="Introduced date">
                        </div>
                        <div class="form-group">
                            <label for="discontinued">Discontinued date</label>
                            <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date">
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company</label>
                            <select class="form-control" id="companyId">
                                    <option value="0">--</option>
                                </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Edit" class="btn btn-primary"> or
                        <a href="dashboard.html" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>