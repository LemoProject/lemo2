<#import "layout.html.ftl" as layout> <@layout.page title="Analysis">




<h1 class="page-header">Available Analyses</h1>

<ul class="media-list">
	<!-- -->
	<#list analysisPlugins as plugin>
	<!-- -->
	<#if plugin.iconPath??><#assign
	iconPath="${analysisPath}/${plugin.id}/assets/${plugin.iconPath}"> <#else> <#assign
	iconPath="holder.js/96x96/gray"> </#if>
	<!-- -->
	<li class="media">
		<div class="media-left">
			<a href="${analysisPath}/${plugin.id}"> <img class="media-object"
				src="${iconPath}" width="96" height="96" alt="${plugin.name}">
			</a>
		</div>
		<div class="media-body">
			<h4 class="media-heading">
				<a href="${analysisPath}/${plugin.id}">${plugin.name}</a> <small>
					&ndash; ${plugin.shortDescription}</small>
			</h4>
			<p>${plugin.longDescription}</p>
		</div>
	</li>
	<!-- -->
	</#list>
</ul>


</@layout.page>
