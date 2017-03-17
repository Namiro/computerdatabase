<%-- Constants block. --%>
<c:set var="LIST_GAME" value="<%=Data.LIST_GAME%>" /> 

<%-- Content page. --%>
<div class="row">
    <div class="col-md-8 col-md-offset-2">

        <c:choose>
            <c:when test="${not empty requestScope[LIST_GAME]}">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Name Game</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope[LIST_GAME]}" var="game" varStatus="vs">
                            <tr>
                                <td>${game.name}</td>
                                <td><input type="button" class="btn btn-default" value="See details" onclick="loadModal(${vs.count});" /></td>

                        <input type="hidden" id="${GAME_AGE_MIN}${vs.count}" value="${game.ageMin}" />
                        <input type="hidden" id="${GAME_NAME}${vs.count}" value="${game.name}" />
                        <input type="hidden" id="${GAME_NUMBER_PLAYER_MIN}${vs.count}" value="${game.numberPlayerMin}" />
                        <input type="hidden" id="${GAME_NUMBER_PLAYER_MAX}${vs.count}" value="${game.numberPlayerMax}" />
                        <input type="hidden" id="${GAME_RULES}${vs.count}" value="${game.rules}" />
                        <input type="hidden" id="${GAME_VERSION}${vs.count}" value="${game.version}" />
                        <input type="hidden" id="${GAME_DESCRIPTION}${vs.count}" value="${game.description}" />
                        <input type="hidden" id="${GAME_VIDEO_URL}${vs.count}" value="${game.video.link}" />
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>

            <c:otherwise>
                <div class="alert alert-info" />
                <p>There is no games</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</div>