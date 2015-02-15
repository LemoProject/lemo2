<#import "layout.html.ftl" as layout> <@layout.page
title=analysis.name scriptBase="/"+analysis.path+"/assets" scripts=analysis.scriptPaths>

<h2>${analysis.name}</h2>

<div class="row">
	<div id="analysis-forms" class="col-md-3 col-md-push-9"></div>
	<div id="analysis-main" class="col-md-9 col-md-pull-3">
		<div id="viz" style="width: 100%;"></div>
	</div>
</div>



</@layout.page>
