<#macro page title scriptBase="" scripts=[]>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<title>Lemo | ${title}</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="${assetPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${assetPath}/css/apps.css">
	<link rel="stylesheet" href="${assetPath}/css/main.css">
	
	<!-- default libraries -->
	<script src="${assetPath}/js/lib/jquery-2.1.3.min.js"></script>
	<script src="${assetPath}/js/lib/bootstrap.min.js"></script>
	
	<script src="${assetPath}/js/lemo.js"></script>
	<script src="${assetPath}/js/lib/packages.js"></script>
	<script src="${assetPath}/js/lib/d3.v2.min.js"></script>
	
	<script src="${assetPath}/js/lib/holder.min.js"></script>
	<script>
		// override default gray theme
		Holder.addTheme("gray", {
			text : " ",
			background : "#f8f8f8"
		});
	</script>

</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${basePath}">Lemo</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
				
					<li><a href="${basePath}">Home</a></li>
	
					<li class="dropdown">
						
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
							Analytic Tools <span class="caret"></span>
						</a>
						
						<ul class="dropdown-menu" role="menu">
							<li><a href="${analyticsPagePath}">Tool Overview</a></li>
							<li class="divider"></li>

								<#list tools as tool>

									<li>
										<a href="${tool.url}">
											<#if tool["lemo.tool.image.icon.monochrome"]??>
												<img width="24" height="24" style="margin-left: -16px;" src="${tool.assets}/${tool["lemo.tool.image.icon.monochrome"]}" />
											</#if>
	 										${tool.name}
 										</a>
									</li>
								</#list>
								
						</ul>
					</li>
				</ul>

				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button class="btn btn-default" type="submit">
						<i class="glyphicon glyphicon-search"></i>
					</button>
				</form>

			</div>
		</div>
	</nav>
 
 
	<div class="container"><#nested/></div>

	<!-- page libraries -->
	<#list scripts as script>
		<script src="${scriptBase}/${script}"></script>
	</#list>


</body>
</html>
</#macro>
