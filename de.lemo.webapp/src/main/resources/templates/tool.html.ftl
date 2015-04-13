<#import "layout.html.ftl" as layout> <@layout.page title=tool.name
scriptBase=tool.assets scripts=tool["lemo.tool.scripts"]>

<h2>${tool.name}</h2>

<div class="row">

	<div id="analysis-forms" class="col-md-3 col-md-push-9">
		<form class="form-horizontal">

			<!--  TODO fake form, remove -->
			<div class="col-xs-6 col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						Zeitraum
						<button type="button" class="pull-right btn btn-xs btn-link"
							data-toggle="popover" title="Zeitraum" data-trigger="focus"
							data-placement="left" data-content="Some info text ...">
							<span class="glyphicon glyphicon-question-sign"
								aria-hidden="true"></span>
						</button>

					</div>
					<div class="panel-body">

						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputName2">Beginn</label> <input type="date"
									class="form-control" id="exampleInputName2" value="2012-01-01">
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail2">Ende</label> <input type="date"
									class="form-control" id="exampleInputEmail2" value="2012-12-31">
							</div>
						</div>

					</div>
				</div>
			</div>


		</form>
	</div>

	<div id="analysis-main" class="col-md-9 col-md-pull-3">
		<div id="viz" style="width: 100%;"></div>
	</div>

</div>


<script>
	// enable boostrap popover
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>


</@layout.page>
