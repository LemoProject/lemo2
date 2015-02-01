<#import "layout.html.ftl" as layout> <@layout.page title="Learning
Analytics" >

<h1 class="page-header">Learning Analytics</h1>

<div class="row">
	<div class="col-md-12">

		<div id="carousel-example-generic" class="carousel slide"
			data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<#list analysisPlugins as plugin>
				<li data-target="#carousel-example-generic"
					data-slide-to="${plugin_index}"></li>
				<!-- TODO add class="active" -->
				</#list>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">

				<#list analysisPlugins as plugin> <#if plugin.previewImagePath??>
				<#assign
				previewImagePath="${analysisPath}/${plugin.previewImagePath}">
				<#else> <#assign previewImagePath="holder.js/100%x500/social">
				</#if>

				<div class="item active">
					<img src="${previewImagePath}" alt="${plugin.name}">
					<div class="carousel-caption">
						<h3>${plugin.name}</h3>
						<p>${plugin.shortDescription}</p>
					</div>
				</div>
				</#list>
			</div>

			<!-- Controls -->
			<a class="left carousel-control" href="#carousel-example-generic"
				role="button" data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>
</div>


</@layout.page>
