// 编辑器整合CKFinder
//检查CKEditor是否正确加载
if ( typeof CKEDITOR == 'undefined' )
{
    document.write(
        '<strong><span style="color: #ff0000">Error</span>: CKEditor not found</strong>.' +
        'This sample assumes that CKEditor (not included with CKFinder) is installed in' +
        'the "/ckeditor/" path. If you have it installed in a different place, just edit' +
        'this file, changing the wrong paths in the &lt;head&gt; (line 5) and the "BasePath"' +
        'value (line 32).' ) ;
}
else
{
    var editor = CKEDITOR.replace( 'editor1' );
    //editor.setData( '<p>Just click the <b>Image</b> or <b>Link</b> button, and then <b>&quot;Browse Server&quot;</b>.</p>' );
    // Just call CKFinder.setupCKEditor and pass the CKEditor instance as the first argument.
    // The second parameter (optional), is the path for the CKFinder installation (default = "/ckfinder/").
    CKFinder.setupCKEditor( editor, 'adminskin/js/plugins/ckfinder/' ) ;

    // It is also possible to pass an object with selected CKFinder properties as a second argument.
    // CKFinder.setupCKEditor( editor, { basePath : '../', skin : 'v1' } ) ;
}







//为日期输入框加载日期
function inputTime() {
    if ($("#datepicker").val() == '')
    {
        var t = new Date();
        var year = t.getFullYear();
        var month = t.getMonth()+1;
        var day = t.getDate();
        month = checkDate(month) 
        day = checkDate(day) 
        $("#datepicker").val(year + "/" + month + "/" + day);
    }
}

//add a zero in front of numbers which<10 
function checkDate(i) { 
    if (i<10) 
    { 
        i="0" + i 
    } 
    return i 
} 

$(document).ready(function() {
    inputTime();
});



