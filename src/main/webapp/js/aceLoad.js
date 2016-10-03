    // Hook up ACE editor to all textareas with data-editor attribute
    $(function () {

        $('textarea[data-editor]').each(function () {

           var textarea = $(this);

           if (textarea.val() != "") {
              var str = textarea.val().replace(/ /g,'');

           }

           var userRO = textarea.attr("editor-ro");
           var userJSF = textarea.attr("editor-jsf");

           if(typeof str != 'undefined') {
            if( typeof userJSF != 'undefined' && userJSF == "false") {
              var jsonDoc = JSON.parse(str);
              var prtext = JSON.stringify(jsonDoc);
            } else {
              var jsonDoc = JSON.parse(str);
              var sample = sampleData(jsonDoc);
              var prtext = JSON.stringify(sample);
            }
           }

            var mode = textarea.data('editor');
            var editDiv = $('<div>', {
                position: 'absolute',
                width: textarea.width(),
                height: textarea.height(),
                'class': textarea.attr('class')
            }).insertBefore(textarea);
            //textarea.css('visibility', 'hidden');
            textarea.css('display', 'none');
            var editor = ace.edit(editDiv[0]);

            if (typeof userRO != 'undefined' && userRO == "true") {
              editor.setOptions({
                readOnly: true
              });

              editor.renderer.setOptions({
               showGutter: true,
              });
            }

            //editor.renderer.setShowGutter(false);
            editor.$blockScrolling = "Infinity";
            editor.setDisplayIndentGuides(true);
            editor.setShowPrintMargin(true);
            editor.setHighlightActiveLine(true);
            editor.getSession().setUseWrapMode(true);
            editor.renderer.setShowPrintMargin(false);
            editor.setShowInvisibles(false);
            var heightUpdateFunction = function() {
                var newHeight =
                          editor.getSession().getScreenLength()
                          * editor.renderer.lineHeight
                          + editor.renderer.scrollBar.getWidth();

                editDiv.height(newHeight.toString() + "px");
                $('.ace-scroller').height(newHeight.toString() + "px");

                editor.resize();

                var $textArea = $("#textarea-response");
                $textArea.height(newHeight.toString());
            };


            if (typeof prtext == 'undefined') {
              editor.getSession().setValue("{\"InputFormat\":{\"type\":\"object\",\"properties\":{\"parameters\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}");
            } else {
              editor.getSession().setValue(prtext);
            }

            editor.getSession().setMode("ace/mode/" + mode);
            editor.setTheme("ace/theme/eclipse");
            var val = editor.session.getValue()
            var o = JSON.parse(val) // may throw if json is malformed
            val = JSON.stringify(o, null, 2) // 2 is the indent size
            editor.session.setValue(val)

            if (typeof userRO == 'undefined' || userRO == "false") {
              editor.getSession().on('change', heightUpdateFunction);

              // copy back to textarea on form submit...
              textarea.closest('form').submit(function () {
                  textarea.val(editor.getSession().getValue());
              });
            }
        });
    });
