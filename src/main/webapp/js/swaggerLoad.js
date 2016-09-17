



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
                                             var value = element.id + "_" + SwaggerApp.paramcount;
                                             element.setAttribute('id', value);
                                             //.log( value);
                                         });

                $clone.find("select").each(function(index, element){
                                             var value = element.id + "_" + SwaggerApp.paramcount;
                                             element.setAttribute('id', value);
                                             //console.log(name);
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
                                           var value = element.id + "_" + SwaggerApp.responsecount;
                                           element.setAttribute('id', value);
                                           //console.log(value);
                                       });

              $clone.find("select").each(function(index, element){
                                           var value = element.id + "_" + SwaggerApp.responsecount;
                                           element.setAttribute('id', value);
                                           //console.log(value);
                                       });
              SwaggerApp.responsecount++;
              $('#responseCount').attr("value", SwaggerApp.responsecount);

      })

  });