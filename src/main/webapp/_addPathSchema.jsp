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

            <form id="swaggerForm" action="${pageContext.request.contextPath}/default" method="post"  role="form"
                data-toggle="validator" class="form-horizontal multipage">

                  <fieldset id="page_one">
                  <legend>Basic Information</legend>

              <c:if test ="${empty action}">
                <c:set var="action" value="add"/>
              </c:if>
              <input type="hidden" id="action" name="action" value="${action}"/>
              <input type="hidden" id="paramCount" name="paramCount" value="0"/>
              <input type="hidden" id="responseCount" name="responseCount" value="0"/>

                  <div class="form-group">
                    <label for="routingType" class="control-label col-sm-2">Routing Type:</label>
                    <div class="col-sm-8">
                      <label class="radio-inline">
                        <input type="radio" name="routingType" checked value="redirect">Redirect
                      </label>
                      <label class="radio-inline">
                        <input type="radio" name="routingType" value="proxy">Proxy
                      </label>
                      <br>
                      <!-- span id="respSchHelpBlock" class="help-block">
                        Redirect will push the request to the provided hostname and let it response back to clients.<br>
                        Proxy acts as an intermediary for all request from clients and collect data from the provided hostname in the background.
                      </span -->
                      </div>
                    </div>

              <div class="form-group ">
                <label for="route" class="control-label col-sm-2">Path: </label>
                <input type="hidden" id="_pathprefix" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/">
                <div class="input-group  col-sm-offset-2  col-sm-10" style="padding-left: 15px;">
                  <span class="input-group-addon" id="pathprefix">
                  ${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/redirect
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
                <div class="col-sm-8">
                  <select class="form-control" id="tag">
                    <c:forEach var="tg" items="${paths.getTags()}" varStatus="tag_index">
                      <option value="${tag_index.count}">${tg.getName()} - <small>${tg.getDescription()}</small></option>
                    </c:forEach>
                  </select>
                </div>
              </div>
          </fieldset>
          <fieldset id="page_two">
            <legend>More Information</legend>
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
              </fieldset>
                <fieldset id="page_three">
                <legend>Add Parameter Definition</legend>

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
                    <label class="radio-inline">
                      <input type="radio" name="paramSchemaType">String
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="paramSchemaType">Array
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="paramSchemaType">$ref
                    </label>
                    <span id="parSchHelpBlock" class="help-block">All types other than 'String' type needs Schema</span>
                    </div>

                  </div>

                <div class="form-group">
                  <label for="paramSchema" class="control-label col-sm-2">Schema:</label>
                  <div class="col-sm-8">
                      <select class="form-control" id="paramSchema" messageSuffix="paramSchemaDetails"
                        onchange="showSchema(this);">
                        <option value="0">No Schema</option>
                        <c:forEach var="defn" items="${paths.definitions}" varStatus="ps_index">
                          <option value="${ps_index.count}">#/definitions/${defn.key}</option>
                        </c:forEach>
                      </select>

                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-sm-2"></label>
                  <div class="col-sm-8">
                    <c:forEach var="defn" items="${paths.definitions}" varStatus="ps_mesgIndex">
                      <div  id="${ps_mesgIndex.count}_paramSchemaDetails" style="display: none">
			                  <pre>${obj.writerWithDefaultPrettyPrinter().writeValueAsString(defn.value)}</pre>
                     </div>

                    </c:forEach>
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

              <div class="form-group"><label  class="control-label col-sm-2">Parameter: </label>
                <div class="col-sm-8">
                  <button type="button" class="btn btn-default addParamButton" aria-describedby="paramHelpBlock">Add Parameter</button>
                  <span id="paramHelpBlock" class="help-block"></span>
                </div>
              </div>

              </fieldset>
                <fieldset id="page_four">
                <legend>Add Response Definition</legend>
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
                        <label class="radio-inline">
                          <input type="radio" name="responseSchemaType">String
                        </label>
                        <label class="radio-inline">
                          <input type="radio" name="responseSchemaType">Array
                        </label>
                        <label class="radio-inline">
                          <input type="radio" name="responseSchemaType">$ref
                        </label>
                        <span id="respSchHelpBlock" class="help-block">All types other than 'String' type needs Schema</span>
                        </div>
                      </div>


                    <div class="form-group">
                      <label for="responseSchema" class="control-label col-sm-2">Schema:</label>
                      <div class="col-sm-8">
                        <select class="form-control" id="ResponseSchema" messageSuffix="paramResponseDetails"
                          onchange="showSchema(this);">
                          <option value="0">No Schema</option>
                          <c:forEach var="resp" items="${paths.definitions}" varStatus="rs_index">
                            <option value="${rs_index.count}">#/definitions/${resp.key}</option>
                          </c:forEach>
                        </select>
                    </div>
                  </div>


                  <div class="form-group">
                    <label class="control-label col-sm-2"></label>
                    <div class="col-sm-8">
                      <c:forEach var="resp" items="${paths.definitions}" varStatus="rs_mesgIndex">
                        <div id="${rs_mesgIndex.count}_paramResponseDetails" style="display: none">
                          <pre>${obj.writerWithDefaultPrettyPrinter().writeValueAsString(resp.value)}</pre>
                        </div>
                      </c:forEach>
                  </div>
                </div>
              </div>

              <div class="form-group"><label  class="control-label col-sm-2">Response: </label>
                <div class="col-sm-8">
                  <button type="button" class="btn btn-default addResponseButton">Add Response</button>
                </div>
              </div>

            </fieldset>

            <fieldset id="page_five">
                <legend>Add Redirect/Forward Information </legend>
                <div id="redirectTemplate">

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
                  </div>
                </fieldset>

              <input type="submit" value="Save Changes" />

            </form>
          </div>
          <!--div class="modal-footer">
            <button type="button" class="btn btn-default"
              data-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
          </div-->


        </div>
      </div>
    </div>
