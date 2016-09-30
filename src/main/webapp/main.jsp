
  <%@ include file="./_header.jsp" %>

  <%@ include file="./_navbar_header.jsp" %>

  <div class="container">

    <c:if test="${not empty message}">
      <div class="alert alert-success fade in messages" >
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        ${message}
      </div>
    </c:if>

    <c:if test="${not empty errormessage}">
      <div class="alert alert-danger fade in errormessages" >
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        ${errormessage}
      </div>
    </c:if>

    <c:if test="${not empty infomessage}">
      <div class="alert alert-info fade in infomessages" style="word-wrap: break-word;">
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <pre>
        ${infomessage}
        </pre>
      </div>
    </c:if>
    <c:if test="${tabactive=='listPath'}">
      <c:set var="listPathTab" value="in active"></c:set>
    </c:if>
    <c:if test="${tabactive=='collapseCFunc'}">
      <c:set var="collapseCFuncTab" value="in active"></c:set>
    </c:if>
    <c:if test="${tabactive=='collapseSwagger'}">
      <c:set var="collapseSwaggerTab" value="in active"></c:set>
    </c:if>

    <div class="tab-content">

      <div role="tabpanel" class="tab-pane fade ${collapseSwaggerTab}" id="collapseSwagger">
        <%@ include file="./_swaggerInfo.jsp" %>
      </div>

      <div role="tabpanel"
        class="tab-pane fade ${collapseCFuncTab}"id="collapseCFunc">
        <%@ include file="./_cfunc.jsp" %>
      </div>

      <div role="tabpanel"
        class="tab-pane fade ${listPathTab}" id="listPath">
        <%@ include file="./_routes.jsp" %>
      </div>

    </div>
  </div>

  <%@ include file="./_footer.jsp" %>
