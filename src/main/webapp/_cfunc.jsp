<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/json-schema-faker/0.3.6/json-schema-faker.js"></script>

<script>
//var jsf = require('json-schema-faker');
console.log(jsf.version);
</script>
  <div class="panel panel-primary">
    <div class="panel-heading">Create Route Based on PCML</div>



      <c:choose>
        <c:when test="${not empty pcml.functions}">
          <div class="well">
              <b>Library :</b> ${pcml.pcml.library} <br>
              <b>Version :</b> ${pcml.pcml.id}

          </div>

          <form id="pcmlForm" action="${pageContext.request.contextPath}/default" method="post"  role="form"
              data-toggle="validator" class="form-horizontal ">


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

            <div class="form-group ">
              <label for="funcName" class="control-label col-sm-2">Function Name:</label>
              <div class="col-sm-8">
                <select class="form-control" id="tag" onchange="showFunctionParams(this);">
                  <c:forEach var="func" items="${pcml.functions}" varStatus="func_index">
                    <option value="${func_index.index}">${func.key}</option>
                  </c:forEach>
                </select>
              </div>
            </div>

            <div class="form-group ">
              <label for="tag" class="control-label col-sm-2">Parameters: </label>
              <div class="col-sm-8">
               <c:forEach var="function" items="${pcml.functions}" varStatus="cfIndex">
                <div id="functionParams_${cfIndex.index}" style="display: ${cfIndex.index == 0 ? '' : 'none'}">
                  <ul class="nav nav-tabs" role="tablist">
                    <c:forEach var="par" items="${function.value.params}" varStatus="cpIndex">
                      <li role="presentation" class="${cpIndex.index == 0 ? 'active' : ''}">
                        <a href="#cparam_${cfIndex.index}_${cpIndex.count}"
                          aria-controls="cparam_${cfIndex.index}_${cpIndex.count}"
                          role="tab" data-toggle="tab">${par.key}</a>


                      </li>

                    </c:forEach>
                    <li role="presentation">
                      <a href="#cparam_rc_${cfIndex.index}"
                        aria-controls="cparam_rc_${cfIndex.index}"
                        role="tab" data-toggle="tab">ReturnCode</a>
                    </li>

                  </ul>
                  <div class="tab-content" >
                    <div role="tabpanel" class="tab-pane fade" id="cparam_rc_${cfIndex.index}"
                      style="border-right: 1px solid #ccc; border-left: 1px solid #ccc;border-bottom: 1px solid #ccc; padding: 20px;">
                      <div class="checkbox">
                        <label>
                          <input type="checkbox" name="checkbox_rc_${cfIndex.index}"
                             id="checkbox_rc_${cfIndex.index}" checked>
                          Include ReturnCode in the Response Output
    <pre style="border:0px; background-color: #eee;">${obj.writerWithDefaultPrettyPrinter().writeValueAsString(function.value.returnCode)}</pre>
                        </label>
                      </div>
                    </div>

                    <c:forEach var="par" items="${function.value.params}" varStatus="cpIndex">
                      <div role="tabpanel" class="tab-pane fade ${cpIndex.index == 0 ? 'in active' : ''}"
                          id="cparam_${cfIndex.index}_${cpIndex.count}"
                            style="border-right: 1px solid #ccc; border-left: 1px solid #ccc;border-bottom: 1px solid #ccc; padding: 20px;">

                          <div class="checkbox">
                            <label>
                              <input type="checkbox" name="checkbox_${cfIndex.index}_${cpIndex.count}"
                                 id="checkbox_${cfIndex.index}_${cpIndex.count}" value="">
                              Include this parameter in the Response Output
                            </label>
                          </div>

                          <div class="radio">
                            <label>
                              <input type="radio" name="optionsRadios_${cfIndex.index}_${cpIndex.count}"
                                id="optionsRadios1_${cfIndex.index}_${cpIndex.count}" value="option1" checked>
                              Add This Parameter
            <pre style="border:0px; background-color: #eee;">${obj.writerWithDefaultPrettyPrinter().writeValueAsString(par.value)}</pre>
                            </label>
                          </div>

                          <div class="radio">
                            <label>
                              <input type="radio" name="optionsRadios_${cfIndex.index}_${cpIndex.count}"
                                id="optionsRadios2_${cfIndex.index}_${cpIndex.count}" value="option2">
                              Use This Parameter <br><br>

                              <textarea name="cschemaDefn" id="cschemaDefn" data-editor="json" rows="15"
                                    style="width:600px; height:200px;"
                                    class="form-control"  aria-describedby="basic-addon3">
                                    ${obj.writeValueAsString(par.value)}
                              </textarea>
                            </label>
                          </div>
                      </div>
                    </c:forEach>
                  </div>
                </div>
               </c:forEach>
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
