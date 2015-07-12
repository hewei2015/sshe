<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<body>
	<script type="text/javascript">
		function addTab(opts) {
			var t = $('#layout_center_tabs');
			if (t.tabs('exists', opts.title)) {
				t.tabs('select', opts.title);
			} else {
				t.tabs('add', opts);
			}
		}
	</script>

	<!-- Begin:Tab============================================================ -->
	<div id="layout_center_tabs" class="easyui-tabs" data-options="fit:true,border:false" style="overflow:hidden;">
		<div title="首页">
		</div>
	</div>
	<!-- End:Tab============================================================ -->
</body>
