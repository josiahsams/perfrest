
  <div class="navbar  navbar-inverse  navbar-fixed-top" role="navigation">
    <div class="container-fluid">

      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
          data-target="#bs-example-navbar-collapse-1" aria-expanded="true">
              <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">MappeR</a>
      </div>

      <form id="collapseCFuncForm" method="post" action="${pageContext.request.contextPath}/">
        <input type="hidden" name="tabActive" value="collapseCFunc">
      </form>

      <c:if test="${tabactive=='collapseCFunc'}">
        <c:set var="CFuncTab" value="in active"></c:set>
      </c:if>
      <c:if test="${tabactive=='collapseSwagger'}">
        <c:set var="SwaggerTab" value="in active"></c:set>
      </c:if>


      <div class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav navbar-left">

          <!--li><a href="#listPath" aria-controls="listPath"
                role="tab" data-toggle="tab">Paths</a></li>
          <li><a href="#" data-toggle="modal" data-target="#myModal">New Route</a></li-->

          <li class="${SwaggerTab}"><a href="#collapseSwagger" aria-controls="collapseSwagger"
                role="tab" data-toggle="tab" aria-expanded="true">REST APIs</a></li>

          <li class="dropdown ${CFuncTab}">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
              aria-haspopup="true" aria-expanded="false">Libraries<span class="caret"></span></a>
              <ul class="dropdown-menu">
                  <c:forEach var="lib" items="${listLibraries}">
                    <li><a href='#' id="${lib}" class="libraryList">${lib}</a></li>
                  </c:forEach>
              </ul>
          </li>

          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
              aria-haspopup="true" aria-expanded="false">Config<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li>
                  <a href='#' data-toggle="modal" data-target="#swaggerRoute">
                    Add Path Information
                  </a>
                </li><li>
                  <a href='#'
                  data-toggle="modal" data-target="#swaggerSchema">
                    Add Parameter Schema Definitions
                  </a>
                </li><li>
                  <a href='#'
                  data-toggle="modal" data-target="#swaggerResponse">
                    Add Response Definitions
                  </a>
                </li><li>
                  <a href='#'
                  data-toggle="modal" data-target="#swaggerTag">
                    Add Tag Information
                  </a>
                </li>
              </ul>
          </li>

          <!--li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li-->
          <!--li><a href="${pageContext.request.contextPath}/swagger" >Swagger</a></li-->
          <!--li><a data-toggle="collapse" href="#collapseSwagger" aria-expanded="false"
                  aria-controls="collapseSwagger" data-parent="#displayMain">Swagger</a></li-->
        </ul>
        <p class="navbar-text navbar-right">IBM PConnect </p>

        <form action="/searchroute" method="get" id="searchRoute" role="form" class="navbar-form navbar-right">
          <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
          <div class="form-group">
            <input type="text" name="routePath" id="routePath" class="form-control"
            placeholder="Type a Route Path">
          </div>
          <button type="submit" class="btn btn-info">
          <span class="glyphicon glyphicon-search"></span> Search</button>
        </form>
      </div>

    </div>
  </div>


  <%@ include file="./_addTagDesc.jsp" %>

  <%@ include file="./_addResponseSchema.jsp" %>

  <%@ include file="./_addParameterSchema.jsp" %>

  <%@ include file="./_addPathSchema.jsp" %>

  <%@ include file="./_addPath.jsp" %>

