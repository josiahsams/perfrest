
      <div class="panel panel-primary">
        <div class="panel-heading">List of configured REST APIs with Swagger Definitions</div>
          <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active">
              <a href="#sui" aria-controls="sui" role="tab" data-toggle="tab">Swagger UI</a>
            </li>
            <li role="presentation">
              <a href="#sraw" aria-controls="sraw" role="tab" data-toggle="tab">Raw Swagger Definitions</a>
            </li>
          </ul>
          <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="sui">
              <div class="swagger-section">
                <!--div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div-->
                <div id="swagger-ui-container" class="swagger-ui-wrap"></div>
              </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="sraw">
            <pre>${obj.writerWithDefaultPrettyPrinter().writeValueAsString(rawswagger)}
            </pre>
            </div>
          </div>
      </div>