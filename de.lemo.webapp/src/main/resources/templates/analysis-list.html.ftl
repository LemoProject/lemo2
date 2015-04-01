<#import "layout.html.ftl" as layout> <@layout.page title="Analysis">

<h1 class="page-header">Available Analyses</h1>

<ul class="media-list">
	<#list analysisPlugins.entrySet() as entry>
	<!--  -->
	<#assign analysisPage = analyticsPagePath + "/" + entry.key> <#assign analysis = entry.value>
	<#assign pluginAssets = analysisPluginPath + "/" + analysis.path + "/assets">
	<!--  -->
	<!-- -->
	<#assign iconPath =	(pluginAssets+"/"+analysis.properties.icon_color)!"holder.js/100x100/auto">

	<!-- -->
	<li class="media">
		<div class="media-left">
			<a href="${analysisPage}"> <img
				class="media-object" src="${iconPath}" width="96" height="96"
				alt="${analysis.name}" />
			</a>
		</div>
		<div class="media-body">
			<h4 class="media-heading">
				<a href="${analysisPage}">${analysis.name}</a> <#if
				analysis.properties.description_short??><small> &ndash;
					${analysis.properties.description_short}</small></#if>
			</h4>
			<p>${analysis.properties.description_long!}</p>
		</div>
	</li>
	<!-- -->
	</#list>
</ul>


</@layout.page>
