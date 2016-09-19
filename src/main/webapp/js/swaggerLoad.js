
// Globals
var SwaggerApp = {};
SwaggerApp.paramcount = 0;
SwaggerApp.responsecount = 0;

// Swagger UI Initialization with configurations

$(function(){
  var url = window.location.search.match(/url=([^&]+)/);
  if (url && url.length > 1) {
    url = decodeURIComponent(url[1]);
  } else {
    url = "/perfrest/swagger";
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



function showSchema(sel) {
  _id = sel.value;

  var messageSuffix = $(sel).attr('messageSuffix');
  console.log("look for "+_id+"_"+messageSuffix);
  $("[id$="+messageSuffix+"]").each(function() {
    $(this).css("display", "none");;
    });

    if (_id == 0 ) {
      return;
    }

  document.getElementById(_id+"_"+messageSuffix).style.display = "";

}


  $(document).ready (function(){
    function hideMessage() {
      setTimeout(function(){
        $(".messages").slideUp(1000);
        $(".errormessages").slideUp(1000);
        hideMessage();
       }, 30000);
    }
    hideMessage();


          $('.multipage').multipage({
          "submitLabel": "Save Changes","hideLegend": false,"hideSubmit": true
          });


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
                    var newmessageSuffix = $(element).attr('messageSuffix')
                         + "_" + SwaggerApp.paramcount;

                   element.setAttribute('id', value);
                   element.setAttribute('messageSuffix', newmessageSuffix);
                   //console.log(name);
               });
               $clone.find("[id$=paramSchemaDetails]").each(function() {
                 var newId = $(this).attr("id") + "_" + SwaggerApp.paramcount;
                 $(this).attr("id", newId);;
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
                 var newmessageSuffix = $(element).attr('messageSuffix')
                      + "_" + SwaggerApp.responsecount;

                 element.setAttribute('id', value);
                 element.setAttribute('messageSuffix', newmessageSuffix);

                 console.log(newmessageSuffix);
             });
              $clone.find("[id$=paramResponseDetails]").each(function() {
                var newId = $(this).attr("id") + "_" + SwaggerApp.responsecount;
                $(this).attr("id", newId);;
              });
              SwaggerApp.responsecount++;
              $('#responseCount').attr("value", SwaggerApp.responsecount);

      })

  });
