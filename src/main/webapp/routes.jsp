
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<html>
    <head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script>
$(document).ready (function(){
$("#success-alert").fadeTo(2000, 500).slideUp(500, function(){
    $("#success-alert").slideUp(500);
});
});
</script>
    </head>

    <body>
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">

          <a class="navbar-brand" href="#">
            URL Routing Utility
          </a>
          </div>

          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

          <ul class="nav navbar-nav navbar-left">
                  <li class="active"><a href="#">Home</a></li>

                  <li><a href="#" data-toggle="modal" data-target="#myModal">New Route</a></li>

                </ul>
                <p class="navbar-text navbar-right">IBM PConnect </p>

                      <form action="/searchroute" method="get" id="searchRoute" role="form" class="navbar-form navbar-right">
                      <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                        <div class="form-group">
                          <input type="text" name="routePath" id="routePath" class="form-control" placeholder="Type a Route Path">
                        </div>
                        <button type="submit" class="btn btn-info"><span class="glyphicon glyphicon-search"></span> Search</button>
                      </form>
              </div>


      </div>
    </nav>

        <div class="container">
            <h3>List Configured Routes</h3>
            <c:if test="${not empty message}">
                <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${message}
                </div>
            </c:if>

            <!--Search Form >
            <form action="/searchroute" method="get" id="searchRoute" role="form">
                <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                <div class="form-group col-xs-5">
                    <input type="text" name="routePath" id="routePath" class="form-control" required="true" placeholder="Type the Path of the Route"/>
                </div>
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Search
                </button>
                <br></br>
                <br></br>
            </form -->




<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
     <form action="./routes" method="post"  role="form" data-toggle="validator" class="form-horizontal">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add New Route</h4>
      </div>
      <div class="modal-body">

                <c:if test ="${empty action}">
                    <c:set var="action" value="add"/>
                </c:if>
                <input type="hidden" id="action" name="action" value="${action}"/>


                <div class="form-group ">
                    <label for="route" class="control-label col-sm-2">Path: </label>

                    <div class="input-group  col-sm-offset-2  col-sm-10">
                      <span class="input-group-addon" id="basic-addon3">
                      ${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/
                      </span>
                      <input type="text" name="route" id="route" class="form-control" value="${route.route}"
                                              required="true"  aria-describedby="basic-addon3"/>

                    </div>



</div>
 <div class="form-group ">
  <label for="scheme" class="control-label col-sm-2">Target Scheme:</label>
  <div class="col-sm-4">
  <select class="form-control" id="scheme">
    <option>http</option>
    <option>https</option>
  </select>
  </div>
  </div>
   <div class="form-group ">
                    <label for="host" class="control-label col-sm-2">Target Host:</label>
                    <div class="col-sm-6">
                    <input type="text" name="host" id="host" class="form-control" value="${route.routeInfo.host}"
                        required="true"/>
            </div>
                        </div>
 <div class="form-group">
                    <label for="port" class="control-label col-sm-2">Target Port:</label>
                    <div class="col-sm-4">
                    <input type="text" name="port" id="port" class="form-control" value="${route.routeInfo.port}"
                                                required="true"/>
    </div>
</div>
 <div class="form-group">
                    <label for="path" class="control-label col-sm-2">Target Path:</label>
                    <div class="col-sm-8">
                    <input type="text" name="path" id="path" class="form-control" value="${route.routeInfo.path}"
                                                                        required="true"/>
                                                                        </div>
                                                                        </div>
 <div class="form-group">
                    <label for="queryparam" class="control-label col-sm-2">Additional QueryParams:</label>

                    <div class="col-sm-8">
                    <input type="text" name="queryparam" id="queryparam" class="form-control" value="${route.routeInfo.additionalQueryParams}"
                                                                        required="true"/>
                                                                        </div>
                                                                        </div>

                                       <br></br>




      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Save changes</button>
      </div>

      </form>
    </div>
  </div>
</div>


<div id="newroute" class="collapse">

            <form action="./routes" method="post"  role="form" data-toggle="validator" class="form-horizontal">
                <c:if test ="${empty action}">
                    <c:set var="action" value="add"/>
                </c:if>
                <input type="hidden" id="action" name="action" value="${action}"/>

                <h3>Add New Route</h3>
                <div class="form-group ">
                    <label for="route" class="control-label col-sm-4">Path: </label>

                    <div class="input-group  col-sm-offset-4  col-sm-8">
                      <span class="input-group-addon" id="basic-addon3">
                      ${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/
                      </span>
                      <input type="text" name="route" id="route" class="form-control" value="${route.route}"
                                              required="true"  aria-describedby="basic-addon3"/>

                    </div>



</div>
 <div class="form-group ">
  <label for="scheme" class="control-label col-sm-4">Target Scheme:</label>
  <div class="col-sm-4">
  <select class="form-control" id="scheme">
    <option>http</option>
    <option>https</option>
  </select>
  </div>
  </div>
   <div class="form-group ">
                    <label for="host" class="control-label col-xs-4">Target Host:</label>
                    <div class="col-sm-6">
                    <input type="text" name="host" id="host" class="form-control" value="${route.routeInfo.host}"
                        required="true"/>
            </div>
                        </div>
 <div class="form-group">
                    <label for="port" class="control-label col-xs-4">Target Port:</label>
                    <div class="col-sm-4">
                    <input type="text" name="port" id="port" class="form-control" value="${route.routeInfo.port}"
                                                required="true"/>
    </div>
</div>
 <div class="form-group">
                    <label for="path" class="control-label col-xs-4">Target Path:</label>
                    <div class="col-sm-8">
                    <input type="text" name="path" id="path" class="form-control" value="${route.routeInfo.path}"
                                                                        required="true"/>
                                                                        </div>
                                                                        </div>
 <div class="form-group">
                    <label for="queryparam" class="control-label col-xs-4">Additional QueryParams:</label>

                    <div class="col-sm-8">
                    <input type="text" name="queryparam" id="queryparam" class="form-control" value="${route.routeInfo.additionalQueryParams}"
                                                                        required="true"/>
                                                                        </div>
                                                                        </div>

                                       <br></br>
                    <button type="submit" class="btn btn-primary  btn-md">Add</button>
                    <button type="clear" class="btn btn-default">Clear</button>

            </form>

</div>
                <c:choose>
                    <c:when test="${not empty routeList}">
                        <table  class="table table-striped">
                            <thead >
                                <tr class="info">
                                    <td>#</td>
                                    <td>Path</td>
                                    <td>Target Path</td>
                                    <td>Query Params</td>


                                </tr>
                            </thead>
                            <c:forEach var="route" items="${routeList}" varStatus="index">

                                <tr>
                                    <td>${index.count}</td>
                                    <td>${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}${route.route}</td>
                                    <td>${route.routeInfo.scheme}://${route.routeInfo.host}:${route.routeInfo.port}${route.routeInfo.path}</td>
                                    <td>${fn:escapeXml(route.routeInfo.additionalQueryParams)}</td>

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

<form action ="./newroute.jsp">
    <br></br>

</form>

        </div>
    </body>
</html>