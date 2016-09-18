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

            <form id="swaggerTagForm" action="${pageContext.request.contextPath}/default" method="post"  role="form"
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