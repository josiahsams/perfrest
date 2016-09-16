
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<html>
  <head>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
     integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous">
    </script>

    <link rel="icon" type="swaggerui/image/png" href="images/favicon-32x32.png" sizes="32x32" />
    <link rel="icon" type="swaggerui/image/png" href="images/favicon-16x16.png" sizes="16x16" />
    <link href='swaggerui/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
    <link href='swaggerui/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
    <link href='swaggerui/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
    <link href='swaggerui/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
    <link href='swaggerui/css/print.css' media='print' rel='stylesheet' type='text/css'/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
             integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
             crossorigin="anonymous">


   <!-- Optional theme -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
     integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">


    <script src='swaggerui/lib/object-assign-pollyfill.js' type='text/javascript'></script>
    <script src='swaggerui/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
    <script src='swaggerui/lib/jquery.slideto.min.js' type='text/javascript'></script>
    <script src='swaggerui/lib/jquery.wiggle.min.js' type='text/javascript'></script>
    <script src='swaggerui/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
    <script src='swaggerui/lib/handlebars-4.0.5.js' type='text/javascript'></script>
    <script src='swaggerui/lib/lodash.min.js' type='text/javascript'></script>
    <script src='swaggerui/lib/backbone-min.js' type='text/javascript'></script>
    <script src='swaggerui/swagger-ui.js' type='text/javascript'></script>
    <script src='swaggerui/lib/highlight.9.1.0.pack.js' type='text/javascript'></script>
    <script src='swaggerui/lib/highlight.9.1.0.pack_extended.js' type='text/javascript'></script>
    <script src='swaggerui/lib/jsoneditor.min.js' type='text/javascript'></script>
    <script src='swaggerui/lib/marked.js' type='text/javascript'></script>
    <script src='swaggerui/lib/swagger-oauth.js' type='text/javascript'></script>


    <script src="//d1n0x3qji82z53.cloudfront.net/src-min-noconflict/ace.js"></script>


    <script type="text/javascript">
      $(function(){
        var url = window.location.search.match(/url=([^&]+)/);
                  if (url && url.length > 1) {
                    url = decodeURIComponent(url[1]);
                  } else {
                    url = "./swagger";
                  }

                  hljs.configure({
                    highlightSizeThreshold: 5000
                  });

                  // Pre load translate...
                  if(window.SwaggerTranslator) {
                    window.SwaggerTranslator.translate();
                  }
                  window.swaggerUi = new SwaggerUi({
                    url: url,
                    dom_id: "swagger-ui-container",
                    supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
                    onComplete: function(swaggerApi, swaggerUi){
                      if(typeof initOAuth == "function") {
                        initOAuth({
                          realm: "IBM",
                          appName: "IBM PConnect Utility",
                          scopeSeparator: " ",
                          additionalQueryStringParams: {}
                        });
                      }

                      if(window.SwaggerTranslator) {
                        window.SwaggerTranslator.translate();
                      }
                    },
                    onFailure: function(data) {
                      log("Unable to Load SwaggerUI");
                    },
                    docExpansion: "none",
                    jsonEditor: false,
                    defaultModelRendering: 'schema',
                    showRequestHeaders: false
                  });

                  window.swaggerUi.load();

                  function log() {
                    if ('console' in window) {
                      console.log.apply(console, arguments);
                    }
                  }
      });
    </script>


<script>
    // Hook up ACE editor to all textareas with data-editor attribute
    $(function () {
        $('textarea[data-editor]').each(function () {
            var textarea = $(this);
            var mode = textarea.data('editor');
            var editDiv = $('<div>', {
                position: 'absolute',
                width: textarea.width(),
                height: textarea.height(),
                //                width: "200px",
                //                height: "200px",
                'class': textarea.attr('class')
            }).insertBefore(textarea);
            textarea.css('visibility', 'hidden');
            var editor = ace.edit(editDiv[0]);
            editor.renderer.setShowGutter(false);
            editor.getSession().setValue(textarea.val());
            editor.getSession().setMode("ace/mode/" + mode);
            editor.setTheme("ace/theme/iplastic");

            // copy back to textarea on form submit...
            textarea.closest('form').submit(function () {
                textarea.val(editor.getSession().getValue());
            })
        });
    });
</script>

  <script>

  var SwaggerApp = {};
  SwaggerApp.paramcount = 0;
  SwaggerApp.responsecount = 0;

  $(document).ready (function(){
    function hideMessage() {
      setTimeout(function(){
        $(".messages").slideUp(1000);
        $(".errormessages").slideUp(1000);
        hideMessage();
       }, 30000);
    }
    hideMessage();

        // Add button click handler
        $('#swaggerForm')
          .on('click', '.addParamButton', function() {
            var $template = $('#parameterTemplate'),
                $clone    = $template
                                .clone()
                                .removeClass('hide')
                                .removeAttr('id')
                                .insertBefore($template),
                $option   = $clone.find('[name="parameterTemplate[]"]');

                $clone.find("input").each(function(index, element){
                                             var value = element.id + "_" + SwaggerApp.count;
                                             element.setAttribute('id', value);
                                             console.log(name);
                                         });

                $clone.find("select").each(function(index, element){
                                             var value = element.id + "_" + SwaggerApp.count;
                                             element.setAttribute('id', value);
                                             console.log(name);
                                         });
                SwaggerApp.paramcount++;
                $('#paramCount').attr("value", SwaggerApp.paramcount);

        })
        .on('click', '.addResponseButton', function() {
          var $template = $('#responseTemplate'),
              $clone    = $template
                              .clone()
                              .removeClass('hide')
                              .removeAttr('id')
                              .insertBefore($template),
              $option   = $clone.find('[name="responseTemplate[]"]');

              $clone.find("input").each(function(index, element){
                                           var value = element.id + "_" + SwaggerApp.count;
                                           element.setAttribute('id', value);
                                           console.log(name);
                                       });

              $clone.find("select").each(function(index, element){
                                           var value = element.id + "_" + SwaggerApp.count;
                                           element.setAttribute('id', value);
                                           console.log(name);
                                       });
              SwaggerApp.responsecount++;
              $('#responseCount').attr("value", SwaggerApp.responsecount);

      })

  });

  </script>



  </head>

  <body>

    <nav class="navbar navbar-default">
      <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">URL Routing Utility</a>
      </div>

      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav navbar-left">
          <li><a href="#listPath" aria-controls="listPath" role="tab" data-toggle="tab">Paths</a></li>
          <li><a href="#" data-toggle="modal" data-target="#myModal">New Route</a></li>

          <li><a href="#collapseSwagger" aria-controls="collapseSwagger"
                  role="tab" data-toggle="tab">Swagger</a></li>



          <li><a href="#collapseConfigMenu" aria-controls="collapseConfigMenu"
                  role="collapse" data-toggle="collapse">Config</a></li>

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
    </nav>

    <!-- Modal for New Swagger Tag Description  -->
    <div class="modal fade" id="swaggerTag" tabindex="-1" role="dialog" aria-labelledby="swaggerTagLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="swaggerTagLabel">Configure a Tag Description </h4>
          </div>

          <div class="modal-body">

            <form id="swaggerTagForm" action="#" method="post"  role="form"
                data-toggle="validator" class="form-horizontal">
              <c:if test ="${empty action}">
                <c:set var="action" value="add"/>
              </c:if>
              <input type="hidden" id="action" name="action" value="${action}"/>
              <div class="form-group ">

                <label for="tagName" class="control-label col-sm-2">Tag Name: </label>
                <div class="col-sm-8">
                  <input type="text" name="tagName" id="tagName" class="form-control"
                  value=""
                  required="true"  aria-describedby="basic-addon3"
                  placeholder="Enter unique Tag Name"/>
                </div>
              </div>
              <div class="form-group ">

                <label for="tagDesc" class="control-label col-sm-2">Tag Description: </label>
                <div class="col-sm-8">
                  <input type="text" name="tagName" id="tagDesc" class="form-control"
                  value=""
                  required="true"  aria-describedby="basic-addon3"
                  placeholder="Enter Tag Description"/>
                </div>
              </div>
            </form>

          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-primary">Save changes</button>
          </div>


        </div>
      </div>
    </div>


    <!-- Modal for New Swagger Response Schema Definitions -->
    <div class="modal fade" id="swaggerResponse" tabindex="-1" role="dialog" aria-labelledby="swaggerResponseLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="swaggerResponseLabel">Configure a Response Schema Definitions in JSON format </h4>
          </div>

          <div class="modal-body">

            <form id="swaggerResponseForm" action="#" method="post"  role="form"
                data-toggle="validator" class="form-horizontal">
              <c:if test ="${empty action}">
                <c:set var="action" value="add"/>
              </c:if>
              <input type="hidden" id="action" name="action" value="${action}"/>
              <div class="form-group ">

                <label for="responseKey" class="control-label col-sm-2">Response Schema Key: </label>
                <div class="col-sm-8">
                  <input type="text" name="responseschemaKey" id="responseschemaKey" class="form-control"
                  value=""
                  required="true"  aria-describedby="basic-addon3"

                  placeholder="Enter unique keyword to register this Response Schema Definition"/>
                </div>
              </div>


              <div class="form-group ">
                <label for="responseschemaDefn" class="control-label col-sm-2">Response Schema: </label>
                <div class="col-sm-8">
                  <textarea name="responseschemaDefn" data-editor="json" rows="15" style="width:600px; height:200px;"
                    class="form-control" required="true"  aria-describedby="basic-addon3"
                    placeholder="Enter the Schema Definition"></textarea>
                </div>
              </div>



            </form>

          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-primary">Save changes</button>
          </div>


        </div>
      </div>
    </div>

    <!-- Modal for New Swagger Parameter Schema Definitions -->
    <div class="modal fade" id="swaggerSchema" tabindex="-1" role="dialog" aria-labelledby="swaggerSchemaLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="swaggerSchemaLabel">Configure an Input/Output Parameter Schema Definitions in JSON format </h4>
          </div>

          <div class="modal-body">

            <form id="swaggerSchemaForm" action="#" method="post"  role="form"
                data-toggle="validator" class="form-horizontal">
              <c:if test ="${empty action}">
                <c:set var="action" value="add"/>
              </c:if>
              <input type="hidden" id="action" name="action" value="${action}"/>
              <div class="form-group ">

                <label for="schemaKey" class="control-label col-sm-2">Schema Key: </label>
                <div class="col-sm-8">
                  <input type="text" name="schemaKey" id="schemaKey" class="form-control"
                  value=""
                  required="true"  aria-describedby="basic-addon3"

                  placeholder="Enter unique keyword to register this Schema Definition"/>
                </div>
              </div>


              <div class="form-group ">
                <label for="schemaDefn" class="control-label col-sm-2">Schema : </label>
                <div class="col-sm-8">
                  <textarea name="schemaDefn" data-editor="json" rows="15" style="width:600px; height:200px;"
                    class="form-control" required="true"  aria-describedby="basic-addon3"
                    placeholder="Enter the Schema Definition"></textarea>
                </div>
              </div>



            </form>

          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-primary">Save changes</button>
          </div>


        </div>
      </div>
    </div>

    <!-- Modal for New Swagger Route -->
    <div class="modal fade" id="swaggerRoute" tabindex="-1" role="dialog" aria-labelledby="swaggerRouteLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="swaggerRouteLabel">Configure a Path With Swagger Specification</h4>
          </div>

          <div class="modal-body">

            <form id="swaggerForm" action="/routes" method="post"  role="form"
                data-toggle="validator" class="form-horizontal">
              <c:if test ="${empty action}">
                <c:set var="action" value="add"/>
              </c:if>
              <input type="hidden" id="action" name="action" value="${action}"/>
              <input type="hidden" id="paramCount" name="paramCount" value="0"/>
              <input type="hidden" id="responseCount" name="responseCount" value="0"/>

              <div class="form-group ">
                <label for="route" class="control-label col-sm-2">Path: </label>

                <div class="input-group  col-sm-offset-2  col-sm-10">
                  <span class="input-group-addon" id="basic-addon3">
                  ${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/
                  </span>
                  <input type="text" name="route" id="route" class="form-control"
                  value="${route.route}"
                  required="true"  aria-describedby="basic-addon3"

                  placeholder="Enter the API path to which swagger definition to be added"/>
                </div>
              </div>

              <div class="form-group ">
                <label for="operationType" class="control-label col-sm-2">Operation Type:</label>
                <div class="col-sm-4">
                  <select class="form-control" id="operationType">
                  <option>GET</option>
                  <option>POST</option>
                  <option>UPDATE</option>
                  <option>DELETE</option>
                  <option>PUT</option>
                  </select>
                </div>
              </div>

              <div class="form-group">
                <label for="summary" class="control-label col-sm-2">Summary:</label>
                <div class="col-sm-8">
                  <input type="text" name="summary" id="summary"
                    class="form-control"
                    required="true"
                    placeholder="Provide a short description about the API"/>
                </div>
              </div>

              <div class="form-group">
                <label for="description" class="control-label col-sm-2">description:</label>
                <div class="col-sm-8">
                  <textarea row=3 name="description" id="description"
                    class="form-control"
                    required="true"
                    placeholder="Provide a long description about the API"></textarea>
                </div>
              </div>

              <div class="form-group ">
                <label for="tag" class="control-label col-sm-2">Operation Type:</label>
                <div class="col-sm-4">
                  <select class="form-control" id="tag">
                  <option>perf</option>
                  <option>config</option>
                  </select>
                </div>
              </div>

              <div class="form-group">
                <label for="operationId" class="control-label col-sm-2">operationId:</label>
                <div class="col-sm-8">
                  <input type="text" name="operationId" id="operationId"
                    class="form-control"
                    required="true" placeholder="Provide unique ID among all operations described in the API"/>
                </div>
              </div>

              <div class="form-group">
                <label for="consumes" class="control-label col-sm-2">consumes:</label>
                <div class="col-sm-8">
                  <select class="form-control" id="consumes" multiple>
                  <option>application/json</option>
                  <option>application/xml</option>
                  <option>text/html</option>
                  <option>text/plain</option>
                  </select>
                </div>
              </div>

              <div class="form-group">
                <label for="produces" class="control-label col-sm-2">produces:</label>
                <div class="col-sm-8">
                  <select class="form-control" id="produces" multiple>
                  <option>application/json</option>
                  <option>application/xml</option>
                  <option>text/html</option>
                  <option>text/plain</option>
                  </select>
                </div>
              </div>
              <hr/>
              <div class="hide" id="parameterTemplate">
                <div class="form-group">
                  <label for="paramName" class="control-label col-sm-2">Name:</label>
                  <div class="col-sm-8">
                    <input type="text" name="paramName" id="paramName"
                      class="form-control"
                      required="true" placeholder="Provide parameter name as given the path for query & path parameters"/>
                  </div>
                </div>
                <div class="form-group">
                  <label for="paramIn" class="control-label col-sm-2">Search In:</label>
                  <div class="col-sm-8">
                    <select class="form-control" id="paramIn">
                    <option>path</option>
                    <option>query</option>
                    <option>body</option>
                    <option>form</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="paramDesc" class="control-label col-sm-2">Description:</label>
                  <div class="col-sm-8">
                    <input type="text" name="paramDesc" id="paramDesc"
                      class="form-control"
                      required="true" placeholder="Provide parameter name as given the path for query & path parameters"/>
                  </div>
                </div>

                <div class="form-group">
                  <label for="paramSchemaType" class="control-label col-sm-2">Schema Type:</label>
                  <div class="col-sm-8">
                      <select class="form-control" id="paramSchemaType">
                        <option>String</option>
                        <option>Array</option>
                        <option>$ref</option>
                      </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="paramSchema" class="control-label col-sm-2">Schema:</label>
                  <div class="col-sm-8">
                      <select class="form-control" id="paramSchema">
                        <option>#/definitions/LparConfig</option>
                        <option>#/definitions/InputFormat</option>
                        <option>#/definitions/Error</option>
                      </select>
                  </div>
                </div>

                <div class="form-group">
                  <label for="paramReq" class="control-label col-sm-2">Required:</label>
                  <div class="col-sm-8">
                    <select class="form-control" id="paramReq">
                    <option>true</option>
                    <option>false</option>
                    </select>
                  </div>
                </div>
              </div>

              <div class="form-group"><label  class="control-label col-sm-2"></label>
                <div class="col-sm-8">
                  <button type="button" class="btn btn-default addParamButton">Add Parameter</button>
                </div>
              </div>

              <hr/>
              <div class="hide" id="responseTemplate">

                <div class="form-group">
                  <label for="responseKey" class="control-label col-sm-2">Response:</label>
                  <div class="col-sm-8">
                    <select class="form-control" id="responseKey">
                    <option>200</option>
                    <option>400</option>
                    <option>403</option>
                    <option>404</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="responseDesc" class="control-label col-sm-2">Description:</label>
                  <div class="col-sm-8">
                    <input type="text" name="responseDesc" id="responseDesc"
                      class="form-control"
                      required="true" placeholder="Provide response description"/>
                  </div>
                </div>
                <div class="form-group">
                  <label for="responseSchemaType" class="control-label col-sm-2">Schema Type:</label>
                  <div class="col-sm-8">
                      <select class="form-control" id="responseSchemaType">
                        <option>String</option>
                        <option>Array</option>
                        <option>$ref</option>
                      </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="responseSchema" class="control-label col-sm-2">Schema:</label>
                  <div class="col-sm-8">
                      <select class="form-control" id="responseSchema">
                        <option>#/definitions/LparConfig</option>
                        <option>#/definitions/InputFormat</option>
                        <option>#/definitions/Error</option>
                      </select>
                  </div>
                </div>

              </div>

              <div class="form-group"><label  class="control-label col-sm-2"></label>
                <div class="col-sm-8">
                  <button type="button" class="btn btn-default addResponseButton">Add Response</button>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
          </div>


        </div>
      </div>
    </div>

    <!-- Modal for New Routing Path -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Add New Route</h4>
          </div>

          <div class="modal-body">
            <form action="./routes" method="post"  role="form" data-toggle="validator" class="form-horizontal">
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
                  <input type="text" name="route" id="route" class="form-control"
                  value="${route.route}"
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
                  <input type="text" name="host" id="host" class="form-control"
                    value="${route.routeInfo.host}"
                    required="true"/>
                </div>
              </div>
              <div class="form-group">
                <label for="port" class="control-label col-sm-2">Target Port:</label>
                <div class="col-sm-4">
                  <input type="text" name="port" id="port" class="form-control"
                    value="${route.routeInfo.port}"
                    required="true"/>
                </div>
              </div>
              <div class="form-group">
                <label for="path" class="control-label col-sm-2">Target Path:</label>
                <div class="col-sm-8">
                  <input type="text" name="path" id="path" class="form-control"
                    value="${route.routeInfo.path}"
                    required="true"/>
                </div>
              </div>
              <div class="form-group">
                <label for="queryparam" class="control-label col-sm-2">Additional QueryParams:</label>
                <div class="col-sm-8">
                  <input type="text" name="queryparam" id="queryparam"
                    class="form-control"
                    value="${route.routeInfo.additionalQueryParams}"
                    required="true"/>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
          </div>
        </div>
      </div>
    </div>





    <div class="container">

      <div class="collapse" id="collapseConfigMenu">

        <div class="well">
          <a class="btn btn-primary" role="button" data-toggle="modal" data-target="#swaggerRoute">
            Add Path Information
          </a>
          <a class="btn btn-primary" role="button"
          data-toggle="modal" data-target="#swaggerSchema">
            Add Parameter Schema Definitions
          </a>
          <a class="btn btn-primary" role="button"
          data-toggle="modal" data-target="#swaggerResponse">
            Add Response Definitions
          </a>
          <a class="btn btn-primary" role="button"
          data-toggle="modal" data-target="#swaggerTag">
            Add Tag Information
          </a>
        </div>
      </div>

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
        <c:when test="${not empty routeList}">
          <table  class="table table-striped">
            <thead >
              <tr class="info">
                <td class="col-sm-1">#</td>
                <td class="col-sm-14">Path</td>
                <!--td>Target Path</td>
                <td>Query Params</td-->
              </tr>
                </thead>
                <c:forEach var="route" items="${routeList}" varStatus="index">
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