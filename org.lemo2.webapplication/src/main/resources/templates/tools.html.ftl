<#import "layout.html.ftl" as layout> <@layout.page title="Analysis">

<h1 class="page-header">Available Analyses</h1>

<ul class="media-list">
	<#list tools as tool>

	<#assign previewImage = (tool.assets + "/" + tool["lemo.tool.image.preview"])!"holder.js/100x100/auto">
	<#assign iconPath =	(tool.assets + "/" + tool["lemo.tool.image.icon.color"])!"holder.js/100x100/auto">

	<li class="media">
		<div class="media-left">
			<a href="${analysisPage!""}"> <img
				class="media-object" src="${iconPath}" width="96" height="96"
				alt="${tool.name}" />
			</a>
		</div>
		<div class="media-body">
			<h4 class="media-heading">
				<a href="${analysisPage!""}">${tool.name}</a> <#if
				tool["lemo.tool.description.short"]??><small> &ndash;
					${tool["lemo.tool.description.short"]}</small></#if>
			</h4>
			<p>${tool["lemo.tool.description.long"]!"no description"}</p>
		</div>
	</li>
 
	</#list>
</ul>


</@layout.page>
