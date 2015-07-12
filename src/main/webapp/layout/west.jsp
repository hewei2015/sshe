<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<body>
	<script type="text/javascript">
		$(function() {
			$('#layout_west_tree').tree({///如果写在html"body"部分会有警告
				url : '${pageContext.request.contextPath}/menuAction!getAllNode4Tree.action',
				parentField : 'pid',
				lines : true,
				onClick : function(node) {
					if (node.attributes.url) {//如果有attributes.url属性才继续往下面执行
						var url = '${pageContext.request.contextPath}' + node.attributes.url;
						addTab({
							title : node.text,
							href : url,
							closable : true,
						});
					}
				}
			});
		});
	</script>
	<!-- border:false表示不显示边框 -->
	<div class="easyui-panel" data-options="title:'功能导航',border:false,fit:true">
		<div id="aa" class="easyui-accordion" data-options="fit:true,border:false">
			<div title="系统菜单" data-options="iconCls:'icon-save'">
				<!-- Begin：tree============================================================ -->
				<!-- 表示变为"+";parentField:'pid'（Ztree模式扩展的easyUI中Tree属性） -->
				<ul id="layout_west_tree" class="easyui-tree" data-options="url:'${pageContext.request.contextPath}/menuAction!getAllNode4Tree.action',
						parentField:'pid',lines:true,
						//onLoadSuccess:function(node,data){$(this).tree('collapseAll');},
						onClick: function(node){
							if(node.attributes.url){//如果有attributes.url属性才继续往下面执行
								var url= '${pageContext.request.contextPath}'+node.attributes.url;
								addTab({title:node.text,href:url,closable:true});//★★href引入页面时只引入目标页面的body标签内的内容
							}
						}">
				</ul>
				<!-- End：tree============================================================ -->
			</div>
			<div title="" data-options="iconCls:'icon-reload'">content2</div>
		</div>
	</div>
</body>
