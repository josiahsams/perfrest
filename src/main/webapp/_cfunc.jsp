
  <div class="panel panel-primary">
    <div class="panel-heading">Create Route Based on PCML</div>



      <c:choose>
        <c:when test="${not empty pcml.functions}">
          <div class="well">
              Version : ${pcml.pcml.id} <br>
              Path : ${pcml.pcml.library}
          </div>

          <form id="pcmlForm" action="${pageContext.request.contextPath}/default" method="post"  role="form"
              data-toggle="validator" class="form-horizontal ">
            <div class="form-group ">
              <label for="funcName" class="control-label col-sm-2">Function Name:</label>
              <div class="col-sm-8">
                <select class="form-control" id="tag">
                  <c:forEach var="func" items="${pcml.functions}" varStatus="func_index">
                    <option value="${func_index.count}">${func.key}</option>
                  </c:forEach>
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

            <div class="form-group">
              <label for="consumes" class="control-label col-sm-2">consumes:</label>
              <div class="col-sm-8 ">
                <label class="control-label ">"application/json"</label>
              </div>
            </div>

            <div class="form-group">
              <label for="produces" class="control-label col-sm-2">produces:</label>
              <div class="col-sm-8">
                <label class="control-label "> "application/json"</label>
              </div>
            </div>

            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Save changes</button>
              </div>
            </div>
          </form>



        </c:when>
        <c:otherwise>
          <br>
          <div class="alert alert-info">
            No Functions Defined in this PCML file.
          </div>
        </c:otherwise>
      </c:choose>

  </div>
