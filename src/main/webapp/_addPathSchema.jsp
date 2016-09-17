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