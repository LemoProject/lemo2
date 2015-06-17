    (function(d3custom, $, undefined) {
    	var ajaxReturnData;
        $.ajax({url: "http://localhost:8080/lemo/tools/activitytime/",async:false, success: function(result){
			removedObjectNames = result.replace(/({"item":)(\[[0-9]+,[0-9]+\])(})/g, '$2');
        	var json = JSON.parse(removedObjectNames);
			ajaxReturnData = json.activityTimeResult.data;
        }});
        d3custom.data = ajaxReturnData;
        d3custom.data.push(JSON.parse('{"locale" : "%m.%d.%Y","currentlyVisible" : "Currently visible data","exportString" : "Choose the data to download as CSV file.","loadedData" : "All loaded data","close" : "Close"}'));
        
    })(window.d3custom = window.d3custom || {}, jQuery);