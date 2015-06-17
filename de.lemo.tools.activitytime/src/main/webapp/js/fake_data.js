    (function(d3custom, $, undefined) {
    	var ajaxReturnData;
        $.ajax({url: "http://localhost:8080/lemo/tools/activitytime/",async:false, success: function(result){
			var json = JSON.parse(result);
			ajaxReturnData = json.activityTimeResult.data;
        }});
        d3custom.data = ajaxReturnData;
    })(window.d3custom = window.d3custom || {}, jQuery);