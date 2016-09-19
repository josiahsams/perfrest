
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<c:set var="root" value="#{request.contextPath}/perfrest/" />
<html>
  <head>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
     integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous">
    </script>

    <!--link rel="icon" type="${root}/swaggerui/image/png" href="images/favicon-32x32.png" sizes="32x32" />
    <link rel="icon" type="${root}/swaggerui/image/png" href="images/favicon-16x16.png" sizes="16x16" /-->
    <link href='${root}/swaggerui/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
    <link href='${root}/swaggerui/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
    <link href='${root}/swaggerui/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
    <!--link href='${root}/swaggerui/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
    <link href='${root}/swaggerui/css/print.css' media='print' rel='stylesheet' type='text/css'/-->

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
             integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
             crossorigin="anonymous">


   <!-- Optional theme -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
     integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="http://d1n0x3qji82z53.cloudfront.net/src-min-noconflict/ace.js" type="text/javascript" ></script>

    <script src='${root}/swaggerui/lib/object-assign-pollyfill.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/jquery.slideto.min.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/jquery.wiggle.min.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/handlebars-4.0.5.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/lodash.min.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/backbone-min.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/swagger-ui.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/highlight.9.1.0.pack.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/highlight.9.1.0.pack_extended.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/jsoneditor.min.js' type='text/javascript'></script>
    <script src='${root}/swaggerui/lib/marked.js' type='text/javascript'></script>
    <!--script src='${root}/swaggerui/lib/swagger-oauth.js' type='text/javascript'></script-->

    <script type="text/javascript" src="${root}/js/multipage.js"></script>
    <link rel="stylesheet" href="${root}/css/multipage.css"  type='text/css' />

    <script src="${root}/js/swaggerLoad.js" type="text/javascript"></script>
    <script src="${root}/js/aceLoad.js" type="text/javascript" ></script>


  </head>

  <body>

<div class="navbar navbar-default" role="navigation">
      <div class="container-fluid">
      <div class="navbar-header">

        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
          data-target="#bs-example-navbar-collapse-1" aria-expanded="true">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">URL Routing Utility</a>

      </div>

      <div class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav navbar-left">
          <li><a href="#listPath" aria-controls="listPath" role="tab" data-toggle="tab">Paths</a></li>
          <li><a href="#" data-toggle="modal" data-target="#myModal">New Route</a></li>

          <li><a href="#" aria-controls="collapseSwagger"
                  role="tab" data-toggle="tab" data-target="#collapseSwagger">Swagger</a></li>


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

        <div class="tab-content">
          <div role="tabpanel" class="tab-pane fade" id="collapseSwagger">
            <div class="panel panel-primary">
                  <div class="panel-heading">Swagger Information</div>
                  <div class="swagger-section">
                    <!--div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div-->
                    <div id="swagger-ui-container" class="swagger-ui-wrap"></div>
                  </div>
            </div>

            <script>
            (function() {
                $('a[data-toggle="tab"]').on('hide.bs.tab', function (e) {
                  e.target // newly activated tab
                  e.relatedTarget // previous active tab

                  alert("Tab");
                })
            })();

            </script>
          </div>

      <div role="tabpanel" class="tab-pane fade in active" id="listPath">

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


                    <form action="${pageContext.request.contextPath}/readuser" method="get"
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
      </div>
    </div>
  </body>
</html>