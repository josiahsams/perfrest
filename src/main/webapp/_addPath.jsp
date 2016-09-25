


    <!-- Modal for New Routing Path -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Add New Route</h4>
          </div>

          <form action="./routes" method="post"  role="form" data-toggle="validator" class="form-horizontal">
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

            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
          </form>
        </div>
      </div>
    </div>

