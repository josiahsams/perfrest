    // Hook up ACE editor to all textareas with data-editor attribute
    $(function () {

        $('textarea[data-editor]').each(function () {
            var textarea = $(this);
            var mode = textarea.data('editor');
            var editDiv = $('<div>', {
                position: 'absolute',
                width: textarea.width(),
                height: textarea.height(),
                'class': textarea.attr('class')
            }).insertBefore(textarea);
            textarea.css('visibility', 'hidden');
            var editor = ace.edit(editDiv[0]);
            editor.renderer.setShowGutter(false);
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



            editor.getSession().setValue("{\"InputFormat\":{\"type\":\"object\",\"properties\":{\"parameters\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}");
            editor.getSession().setMode("ace/mode/" + mode);
            editor.setTheme("ace/theme/eclipse");
            var val = editor.session.getValue()
            var o = JSON.parse(val) // may throw if json is malformed
            val = JSON.stringify(o, null, 2) // 2 is the indent size
            editor.session.setValue(val)

            editor.getSession().on('change', heightUpdateFunction);

            // copy back to textarea on form submit...
            textarea.closest('form').submit(function () {
                textarea.val(editor.getSession().getValue());
            })
        });
    });
