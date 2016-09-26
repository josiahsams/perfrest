
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
  //console.log("look for "+_id+"_"+messageSuffix);
  $("[id$="+messageSuffix+"]").each(function() {
    $(this).css("display", "none");;
    });

    if (_id == 0 ) {
      return;
    }

  document.getElementById(_id+"_"+messageSuffix).style.display = "";

}

function sampleData(schema) {
  var sample = jsf(schema);
  //console.log(sample);
  return sample;
}


function showFunctionParams(sel) {
  _id = sel.value;

  //console.log("look for functionParams_"+_id);

  $("[id^=functionParams_]").each(function() {
    $(this).css("display", "none");;
    });

  document.getElementById("functionParams_"+_id).style.display = "";
}

$(document).ready (function(){

  $('.libraryList').on('click', function() {
    var lib = this.id;
    console.log("Clicked item is " + this.id);
    $.cookie("library", lib, { expires : 1, path : '/perfrest' });
    $("#collapseCFuncForm").submit();
    //window.location.href = "/perfrest/main";

  });

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

  var oldParamCount = 0;
  var listPathParameters = [];
  $('#route').on('keyup', function() {
    var textValue = this.value;

    if (textValue.length > 1) {
      var listPathParameters = textValue.match(/\{([^}]+)\}/g);

      if (listPathParameters != null && oldParamCount != listPathParameters.length) {
        $("#paramHelpBlock").html("Hint: There are "+listPathParameters.length+" parameters <b>( ");

        for (var i = 0; i < listPathParameters.length; i++) {
          oldParamCount = listPathParameters.length;
          var paramName = listPathParameters[i].replace("{","").replace("}", "");
          $("#paramHelpBlock").append(paramName+", ");
        }
        $("#paramHelpBlock").append(" )</b> configured in the path. <br>Make sure to add describe it by add new parameters button.");
      }
    }
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

    // Add button click handler
    $('#pcmlForm')
      .on('click', '.addParamButton', function() {

      });

  });

var inputSchema;
var targetSchema;
var targetReferenceTypes = [];

/**
 * Traverses the entire target schema looking for $refs, expanded them as it goes.
 *
 * @param parent {object} The object in which to look for $refs
 * @param loc {string} The current dot-notation location is relation to the parent.
 */
function expandReferences(parent, loc) {
  for (var child in parent) {

    if (parent.hasOwnProperty(child)) {
      if (child === '$ref' && userWantsExpansion(parent[child])) {

        //We found a $reference
        expand(loc, parent[child]);
      }
      else if (typeof parent[child] === 'object') {

        //Check the child object for $refs. Go deep man.
        expandReferences(parent[child], loc + '.' + child);
      }
    }
  }
}

/**
 * Sets the value at a given path to the real value of its reference.
 *
 * @param target
 * @param valueReference
 */
function expand(target, valueReference) {

  var targetPath = target.substr(1);
  var value = getReferenceValue(valueReference);

  setValueAtPath(targetSchema, value, targetPath);
}


/**
 * Set the value of a nested property given it's dot-notation path
 *
 * @param obj
 * @param value
 * @param path
 */
function setValueAtPath(obj, value, path) {

  path = path.split('.');
  for (var i = 0; i < path.length - 1; i++) obj = obj[path[i]];
  obj[path[i]] = value;
}


/**
 * Sloppy copy of the JSON schema.
 *
 * @param input {object} The object to copy
 * @returns {object} The copied object
 */
function cloneSchema(input) {
  return JSON.parse(JSON.stringify(input));
}


/**
 * Get the top level reference value
 *
 */
function getReferenceValue (reference) {

  var meta = getReferenceMeta(reference);
  return targetSchema[meta.type][meta.localName];
}

/**
 * Parses a $ref value into its type and localName
 * @param reference
 * @returns {{type: *, localName: *}}
 */
function getReferenceMeta (reference) {

  var components = reference.split('/');
  console.log("joe " + components[1]);
  console.log("sam " + components[components.length - 1]);
  console.log("count is " + components.length);
  return {type:components[1], localName:components[components.length - 1]};
}

/**
 * A $ref address to check for
 *
 * @param reference
 * @returns {boolean}
 */
function userWantsExpansion (reference) {

  if (targetReferenceTypes.length === 0) {
    return true;
  }
  else {
    var meta = getReferenceMeta(reference);
    return targetReferenceTypes.indexOf(meta.type) !== -1;
  }
}
