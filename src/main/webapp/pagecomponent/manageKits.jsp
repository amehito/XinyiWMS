<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="./js/toExcel.js"></script>
<script>
	var search_type_storage = "none";
	var search_keyWord = "";
	var select_goodsID;
	var select_repositoryID;

	$(function() {
		optionAction();
		searchAction();
		storageListInit();
		importStroageAction();
		exportStorageAction()
		
	})
	function changeNumber(){
		
		var num = $('#change_storage_number').val();
		var id = $('#storage_goodsID').html();
		let data = {
			stockNumber:num,
			materialid:id
		};
		
		
		$.ajax({
			url:"Material/changematerialnumber",
			type:"POST",
			data:data,
			dataType:"json",
			success:function(response){
				console.log(response);
			}
		});
	}

	// 下拉框選擇動作
	function optionAction() {
		$(".dropOption").click(function() {
			var type = $(this).text();
			$("#search_input").val("");
			if (type == "所有") {
				$("#search_input_type").attr("readOnly", "true");
				search_type_storage = "searchAll";
			} else if (type == "货物ID") {
				$("#search_input_type").removeAttr("readOnly");
				search_type_storage = "searchByGoodsID";
			} else if (type == "货物名称") {
				$("#search_input_type").removeAttr("readOnly");
				search_type_storage = "searchByGoodsName";
			} else if(type = "货物类型"){
				$("#search_input_type").removeAttr("readOnly");
				search_type_storage = "searchByGoodsType";
			}else {
				$("#search_input_type").removeAttr("readOnly");
			}

			$("#search_type").text(type);
			$("#search_input_type").attr("placeholder", type);
		})
	}

	// 搜索动作
	function searchAction() {
		$('#search_button').click(function() {
			search_keyWord = $('#search_input_type').val();
			tableRefresh();
		})
	}

	// 分页查询参数
	function queryParams(params) {
		var temp = {
			limit : params.limit,
			offset : params.offset,
			searchType : search_type_storage,
			keyword : search_keyWord
		}
		return temp;
	}
	
	//添加到数据库里
	function addStorage(){
		let date = new Date();
		let data = {
			materialId:$("#materialId").val(),
			viceId:$("#viceId").val(),
			materialName:$("#materialName").val(),
			materialSpec:$("#materialSpec").val(),
			warehousePosition:$("#warehouse").val(),
			plus:$("#plus").val(),
			materialType:$("#materialType").val(),
			materialUnit:$("#materialUnit").val(),
			materialPrice:$("#materialPrice").val(),
			stockNumber:$("#stockNumber").val(),
			stockSafe:$("#stockSafe").val(),
			batchManage:$("#batchManage").val(),
			createTime:date
		};
		$.ajax({
			url:"Material/addmaterial",
			data:data,
			type:"POST",
			dataType:"text",
			success:function(data){
				alert(data);
				console.log({data});
			}
		});
		$("#closeModal").click();
	}
	
	// 表格初始化
	function storageListInit() {
		$('#storageList')
				.bootstrapTable(
						{
							columns : [
									{
										field : 'materialid',
										title : '零件ID',
										visible:false
									//sortable: true
									},
									{
										field : 'modifyname',
										title : '存入/取走'
									},
									{
										field : 'materialname',
										title : '零件名称'
									},									
									{
										field : 'materialunit',
										title : '单位'
									},
									
									{
										field : 'materialnumber',
										title : '数量'
									},
									{
										field : 'modifymanager',
										title : '操作人'
									},
									{
										field : 'modifytime',
										title : '时间'
									},
									{
										field : 'operation',
										title : '操作',
										formatter : function(value, row, index) {
											var s = '<button class="btn btn-info btn-sm edit"><span>详细</span> </button>';
											var fun = '';
											return s;
										},
										events : {
											// 操作列中编辑按钮的动作
											'click .edit' : function(e, value,
													row, index) {
												rowDetailOperation(row);
											},
											
										}
									} ],
							url : 'Material/getmodifyhistory',
							sortable: false,   
					        pageList : [ 1,5,10],
					        pageSize : 10,
					        sidePagination: "client",  
					        clickToSelect : true,
					        pagination : true,
							/* method : 'GET',
							queryParams : queryParams,
							sidePagination : "server",
							dataType : 'json',
							pagination : true,
							pageNumber : 1,
							pageSize : 5,
							pageList : [ 5, 10, 25, 50, 100 ],
							clickToSelect : true */
						});
	}

	// 表格刷新
	function tableRefresh() {
		$('#storageList').bootstrapTable('refresh', {
			query : {}
		});
	}

	// 行编辑操作
	function rowDetailOperation(row) {
		console.log({row});
		$('#detail_modal').modal("show");

		// load info
		$('#storage_goodsID').text(row.materialid);
		$('#storage_goodsName').text(row.modifyname);
		$('#storage_goodsType').text(row.modifytime);
		$('#storage_goodsSize').text(row.modifymanager);
		$('#storage_goodsValue').text(row.materialname);
		$('#storage_repositoryBelong').text(row.materialunit);
		$('#storage_number').text(row.materialnumber);
		$('#change_storage_number').val(row.materialnumber);
	}

	// 导出库存信息
	function exportStorageAction() {
		var data;
		$('#export_storage').click(function() {
			$('#export_modal').modal("show");
		})

		$('#export_storage_download').click(function(){
			
			$.ajax({
				url:"Material/getmodifyhistory",
				type:"POST",
				dataType:"JSON",
				success:function(response){
					data = eval(response);
					console.log({data}+"success");
					JSONToExcelConvertor(data);
				}
			});
			$('#export_modal').modal("hide");
		})
	}
	
	//导入库存
	function importStroageAction() {
		$('#import_storage').click(function (){
			$('#import_modal').modal("show");
		})
	}

	// 操作结果提示模态框
	function infoModal(type, msg) {
		$('#info_success').removeClass("hide");
		$('#info_error').removeClass("hide");
		if (type == "success") {
			$('#info_error').addClass("hide");
		} else if (type == "error") {
			$('#info_success').addClass("hide");
		}
		$('#info_content').text(msg);
		$('#info_modal').modal("show");
	}
</script>
<div class="panel panel-default">
	<ol class="breadcrumb">
		<li>库存信息管理</li>
	</ol>
	<div class="panel-body">
		<div class="row">
			<div class="col-md-1 col-sm-2">
				<div class="btn-group">
					<button class="btn btn-default dropdown-toggle"
						data-toggle="dropdown">
						<span id="search_type">查询方式</span> <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="javascript:void(0)" class="dropOption">货物ID</a></li>
						<li><a href="javascript:void(0)" class="dropOption">货物名称</a></li>
						<li><a href="javascript:void(0)" class="dropOption">货物类型</a></li>
						<li><a href="javascript:void(0)" class="dropOption">所有</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-9 col-sm-9">
				<div>
					<div class="col-md-3 col-sm-3">
						<input id="search_input_type" type="text" class="form-control"
							placeholder="货物ID">
					</div>
					<div class="col-md-2 col-sm-2">
						<button id="search_button" class="btn btn-success">
							<span class="glyphicon glyphicon-search"></span> <span>查询</span>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row" style="margin-top: 25px">
			<div class="col-md-5 col-sm-5">
				<button class="btn btn-sm btn-default" id="export_storage">
					<span class="glyphicon glyphicon-export"></span> <span>导出</span>
				</button>
				<button class="btn btn-sm btn-default" id="import_storage">
					<span class="glyphicon glyphicon-import"></span> <span>导入</span>
				</button>
			</div>
			<div class="col-md-5 col-sm-5"></div>
		</div>

		<div class="row" style="margin-top: 15px">
			<div class="col-md-12">
				<table id="storageList" class="table table-striped"></table>
			</div>
		</div>
	</div>
</div>

<!-- 导出库存信息模态框 -->
<div class="modal fade" id="export_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">导出库存信息</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-3 col-sm-3" style="text-align: center;">
						<img src="media/icons/warning-icon.png" alt=""
							style="width: 70px; height: 70px; margin-top: 20px;">
					</div>
					<div class="col-md-8 col-sm-8">
						<h3>是否确认导出库存信息</h3>
						<p>(注意：请确定要导出的库存信息，导出的内容为当前列表的搜索结果)</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-success" type="button" id="export_storage_download">
					<span>确认下载</span>
				</button>
			</div>
		</div>
	</div>
</div>
<!-- 导入库存模拟框 -->
<div class="modal fade" id="import_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">导出库存信息</h4>
			</div>
			<div class="modal-body">
				
                   
                    <div class="form-group">
                    	<label for="materialId" class="control-label">机料物代码</label>
                    	<input type="text" class="form-control" id="materialId" >
                    </div>
                    <div class="form-group">
                    	<label for="viceId" class="control-label">亚纶代码</label>
                    	<input type="text" class="form-control" id="viceId" >
                    </div>
                    
                    <div class="form-group">
                    	<label for="materialName" class="control-label">名称</label>
                    	<input type="text" class="form-control" id="materialName" >
                    </div>
                    
                    <div class="form-group">
                    	<label for="materialSpec" class="control-label">规格</label>
                    	<input type="text" class="form-control" id="materialSpec" >
                    </div>
                    
                    <div class="form-group">
                    	<label for="warehousePosition" class="control-label">仓位</label>
                    	<input type="text" class="form-control" id="warehousePosition" >
                    </div>
                    
                   
                    <div class="form-group">
                        <label for="plus" class="control-label">备注:</label>
                        <textarea class="form-control" id="plus" ></textarea>
                    </div>
                    
                    <div class="form-group">
                    	<label for="materialType" class="control-label">物料种类</label>
                    	<input type="text" class="form-control" id="materialType" >
                    </div>
                    
                    <div class="form-group">
                    	<label for="materialUnit" class="control-label">单位</label>
                    	<input type="text" class="form-control" id="materialUnit" >
                    	<label for="materialPrice" class="control-label">单价</label>
                    	<input type="text" class="form-control" id="materialPrice" >
                    </div>
                    
                   
                    
                    <div class="form-group">
                    	<label for="stockNumber" class="control-label">剩余数量</label>
                    	<input type="text" class="form-control" id="stockNumber" >
                    </div>
                    
                    <div class="form-group">
                    	<label for="stockSafe" class="control-label">安全库存</label>
                    	<input type="text" class="form-control" id="stockSafe" >
                    </div>
                    
                    <div class="form-group">
                    	<label for="batchManage" class="control-label">批次管理</label>
                    	<input type="text" class="form-control" id="batchManage" >
                    </div>
                    
                    
                    
                  
                    <div class="text-right">
                        <span id="returnMessage" class="glyphicon"> </span>
                        <button type="button" class="btn btn-default right" data-dismiss="modal" id="closeModal">关闭</button>
                        <button id="submitBtn" type="button" class="btn btn-primary" onclick="addStorage()" >添加</button>
                    </div>
                

				
				
			</div>
			
		</div>
	</div>
</div>

<!-- 详细的库存模态框 -->
<div id="detail_modal" class="modal fade" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">编辑货物信息</h4>
			</div>
			<div class="modal-body">
				<!-- 模态框的内容 -->
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="storage_detail"
							style="margin-top: 25px">
							<div class="row">
								<div class="col-md-6 col-sm-6">
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物Id：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsID" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>操作名称：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsName" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>时间：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsType" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>操作人：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsSize" class="form-control-static"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6 col-sm-6">
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsValue" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物存储仓库ID：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_repositoryBelong" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>数量：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_number" class="form-control-static"></p>
										</div>
									</div>
									<!-- <div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>修改数量：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<input id="change_storage_number" class="form-control"></input>
										</div>
									</div> -->
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<!-- <button class="btn btn-default" type="button" onclick="changeNumber()">
					<span>修改</span>
				</button> -->
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
</div>