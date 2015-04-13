<#import "layout.html.ftl" as layout> <@layout.page title="Learning
Analytics" >

<h1 class="page-header">Learning Analytics</h1>

<div class="row">
	<div class="col-md-12">
		
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width: 655px; margin: 0 auto;">

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">

				<#list tools as tool>
			
					<#assign previewImage = (tool["lemo.tool.assets"] + "/" + tool["lemo.tool.image.preview"])!"holder.js/100x100/auto">
					<#assign active><#if tool_index==0>active</#if></#assign>
 
					<div class="item ${active}">
						<img src="${previewImage}" alt="${tool.name}" />
						<div class="carousel-caption"
							style="bottom: 0px; padding-bottom: 0px; color: #333; text-shadow: 0 1px 2px rgba(255, 255, 255, .6);">
							<h3>
								${tool.name}
							</h3>
							<p>${tool["lemo.tool.description.short"]}</p>
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
