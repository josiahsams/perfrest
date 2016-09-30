


  <div class="panel panel-primary">

    <div class="panel-heading">List Configured Routes</div>

    <c:choose>
      <c:when test="${not empty paths.paths}">
        <table  class="table table-striped">
          <thead >
            <tr class="info">
              <td class="col-sm-1">#</td>
              <td class="col-sm-14">Path</td>
              <!--td>Target Path</td>
              <td>Query Params</td-->
            </tr>
              </thead>
              <c:forEach var="route" items="${paths.paths}" varStatus="index">
                <tr>
                  <td class="col-sm-1">${index.count}</td>
                  <td class="col-sm-14">
                  <a data-toggle="collapse" href="#collapseExample_${index.count}"
                    aria-expanded="false"
                    aria-controls="collapseExample_${index.count}">
                    ${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/proxy${route.route}
                  </a>

                  <div class="collapse" id="collapseExample_${index.count}">
                  <br>
                    <b>Target Path : </b>
                    ${route.routeInfo.scheme}://${route.routeInfo.host}:${route.routeInfo.port}${route.routeInfo.path}
                    </br>
                    <b>Configured QueryParams : </b> ${fn:escapeXml(route.routeInfo.additionalQueryParams)} </br>
                  <br>


                  <form action="${pageContext.request.contextPath}/readuser" method="${route.routeInfo.method}"
                    role="form" data-toggle="validator" class="form-horizontal">

                    <div class="input-group col-sm-10">
                      <span class="input-group-addon" id="basic-addon3">
                      ${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/proxy
                      </span>
                      <input type="hidden" name="prefix"
                      value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/proxy" >
                      <input type="text" class="form-control"  name="userurl"
                        class="form-control"
                        value="${fn:escapeXml(route.route)}" required="true"
                        aria-describedby="basic-addon3">
                    </div>
                    <small>
                      (Replace the pattern string enclosed inside "{}" with parameters and add query parameters if required )
                    </small>
                    <br>
                    <button type="submit" class="btn btn-primary">Try it Out ! </button>
                  </form>
                </div>
              </td>
            </tr>
          </c:forEach>
        </table>
      </c:when>
      <c:otherwise>
        <br>
        <div class="alert alert-info">
          No route found matching your search criteria
        </div>
      </c:otherwise>
    </c:choose>
  </div>
