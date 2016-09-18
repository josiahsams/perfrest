

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

            <form id="swaggerResponseForm" action="${pageContext.request.contextPath}/default" method="post"  role="form"
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
                <div class="col-sm-8" >
                  <textarea name="responseschemaDefn" data-editor="json" rows="15"
                  id="textarea-response"
                  class="form-control" style="width:600px; height:200px; "
                  required="true"  aria-describedby="basic-addon3"></textarea>
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