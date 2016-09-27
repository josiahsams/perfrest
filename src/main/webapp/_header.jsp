<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<c:set var="root" value="#{request.contextPath}/perfrest/" />
<html>
  <head>

    <script src="${root}/jquery/dist/jquery.min.js"></script>
    <script src="${root}/bootstrap/dist/js/bootstrap.min.js"></script>


    <link href='${root}/swaggerui/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
    <link href='${root}/swaggerui/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
    <link href='${root}/swaggerui/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>

    <!--link href='${root}/swaggerui/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
    <link href='${root}/swaggerui/css/print.css' media='print' rel='stylesheet' type='text/css'/-->
    <!--link rel="icon" type="${root}/swaggerui/image/png" href="images/favicon-32x32.png" sizes="32x32" />
    <link rel="icon" type="${root}/swaggerui/image/png" href="images/favicon-16x16.png" sizes="16x16" /-->

    <link rel="stylesheet" href="${root}/bootstrap/dist/css/bootstrap.min.css">
   <link rel="stylesheet" href="${root}/bootstrap/dist/css/bootstrap-theme.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.5/ace.js" type="text/javascript" ></script>

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

    <script type="text/javascript" src="${root}/js/jquery.cookie.js"></script>

    <script type="text/javascript"
      src="https://cdnjs.cloudflare.com/ajax/libs/json-schema-faker/0.3.6/json-schema-faker.js"></script>

    <script src="${root}/js/swaggerLoad.js" type="text/javascript"></script>
    <script src="${root}/js/aceLoad.js" type="text/javascript" ></script>


  </head>

  <body>
