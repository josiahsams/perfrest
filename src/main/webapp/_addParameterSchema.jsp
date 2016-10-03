    <!-- Modal for New Swagger Parameter Schema Definitions -->
    <div class="modal fade" id="swaggerSchema" tabindex="-1" role="dialog" aria-labelledby="swaggerSchemaLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="swaggerSchemaLabel">Configure an Input/Output Parameter Schema Definitions in JSON format </h4>
          </div>

          <form id="swaggerSchemaForm" action="${pageContext.request.contextPath}/default" method="post"  role="form"
                data-toggle="validator" class="form-horizontal">
          <div class="modal-body">


              <c:if test ="${empty action}">
                <c:set var="action" value="add"/>
              </c:if>
              <input type="hidden" id="action" name="action" value="${action}"/>


              <div class="form-group">
                <label for="psparamSchema" class="control-label col-sm-2">Add/Edit:</label>
                <div class="col-sm-8">
                    <select class="form-control" id="psparamSchema" messageSuffix="psparamSchemaDetails"
                      onchange="psshowSchema(this);">
                      <option value="0">Add New Schema</option>
                      <c:forEach var="defn" items="${paths.definitions}" varStatus="ps_index">
                        <option value="${ps_index.count}">#/definitions/${defn.key}</option>
                      </c:forEach>
                    </select>

                </div>
              </div>

              <div class="form-group" style="display: none">
                <label class="control-label col-sm-2"></label>
                <div class="col-sm-8" >
                  <c:forEach var="defn" items="${paths.definitions}" varStatus="ps_mesgIndex">
                    <div id="${ps_mesgIndex.count}_psdefnName">${defn.key}</div>
                    <div  id="${ps_mesgIndex.count}_psparamSchemaDetails" >${obj.writerWithDefaultPrettyPrinter().writeValueAsString(defn.value)}
                   </div>
                  </c:forEach>
                </div>
              </div>


              <div class="form-group ">
                <label for="schemaKey" class="control-label col-sm-2">Schema Key: </label>
                <div class="col-sm-8">
                  <input type="text" name="schemaKey" id="psschemaKey" class="form-control"
                  value=""
                  required="true"  aria-describedby="basic-addon3"
                  placeholder="Enter unique keyword to register this Schema Definition"/>
                </div>
              </div>


              <div class="form-group ">
                <label for="schemaDefn" class="control-label col-sm-2">Schema : </label>
                <div class="col-sm-8">
                  <textarea name="schemaDefn" id="psschemaDefn" data-editor="json" rows="15"
                    style="width:600px; height:200px;"
                    class="form-control"  aria-describedby="basic-addon3"
                    placeholder="Enter the Schema Definition"></textarea>
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
