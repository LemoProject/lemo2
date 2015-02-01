<#macro page title>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        
        <title>Lemo | ${title}</title>
       
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">


        <link rel="stylesheet" href="/lemo/resources/css/normalize.css">
        <link rel="stylesheet" href="/lemo/resources/css/main.css">
        <script src="/lemo/js/vendor/modernizr-2.6.2.min.js"></script>
    </head>
    <body>
    	<h1>${title}</h1>
    
  		<#nested/>
     
    </body>
</html>
</#macro>