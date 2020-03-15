<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
 <style>
 	.modalImg{
 		width: 100%;
 		height:100%;
 		position: relative;
 	}
 	.modalLeft{
 		left:0px;
 		position: absolute;
 	}
 </style>
 <script>
 	let allImageInfo = [];
 	let globalRow ;
 	let arrTemp =[];
 	
 	function getAllInfo(){
 		fetch('Material/ImageInfo')
 	 	  .then(function(response) {
 	 	    return response.json();
 	 	  })
 	 	  .then(function(myJson) {
 	 	    allImageInfo = myJson.reverse();
 	 	  });
 	}
 	getAllInfo();
    // 鏌ヨ鍙傛暟
	//document.querySelector('#search_button').addEventListener('keydown',function(key){console.log(key)});
	document.querySelector('#user_id').addEventListener('keydown',function(key){
		if(key.keyCode ===13){
			document.querySelector('#search_button').click();
		}
	});

    search_user_id = null
    search_start_date = null
    search_end_date = null

    $(function(){
        datePickerInit();
        userOperationRecordTableInit();
        searchActionInit();
        exportStorageAction();
    })
	
    function showPicture(row){
    	let temp = allImageInfo.filter(item => item.materialId ==row.materialId);
    	let url = temp.length>0?temp[0].imageName:'';
    	console.log({url});
    	document.querySelector('#imgUrl').src = '/images/'+url;
    	$('#imageModal').modal("show");
    }
    function editPicture(row){
    	$('#editModal').modal("show");
    	globalRow = row;
    }
    function editState(){
    	document.querySelectorAll('#detailModal input').forEach(item=>{item.disabled=''});
    	document.querySelectorAll('#detailModal input').forEach(item => item.value = item.value.trim());
    	document.querySelector('#detailModal .modal-footer').innerHTML = `<button type="button" class="btn btn-primary" id=""
			onclick = 'modifyEditDetail()'>确定</button><button type="button" class="btn btn-info" id=""
			onclick = 'cancelEditDetail()'>取消</button><button type="button" class="btn btn-danger modalLeft" id=""
			onclick = 'deleteThisMaterial()'>删除</button>`;
    }
    function confirmDelMaterial() {
		let row = globalRow;
		console.log({row});
		$.ajax({
			type: "POST",
			url: "Material/DeleteMaterialById",//提交的接口
			ContentType:'application/json',
			data: {
				/*传值*/
				materialId:row.materialId
			},
			success:function(response){
				if(response =='DeleteMaterialSucceed'){
					alert('删除成功');
					$('#deleteModal').modal('hide');
					$('#detailModal').modal('hide');
					reloadTable();
				}
				else{
					alert('删除失败，请稍后再试');
				}
			},
			error:function(response){
			}
		});
	}
    function reloadTable() {
    	$.ajax({
			url:'Material/getMaterialInfo',
			method:'post',
			success:function(response){						
			$('#userOperationRecordTable').bootstrapTable('load', response);
				arrTemp = response;
			}
		});
	}
    function detailModalInit() {
    	document.querySelector('#detailModal .modal-footer').innerHTML =`
    		<button type="button" class="btn btn-info" id=""
			onclick = 'editState()'>编辑</button>
    	`;
    	document.querySelectorAll('#detailModal input').forEach(item=>{item.disabled='desabled'});

	}
    function modifyEditDetail() {
    	const row = globalRow;
    	console.log({row})
    	let nowDate = new Date();
    	let time = `${nowDate.getFullYear()}-${nowDate.getMonth}-${nowDate.getDate}`;
    	let material = {
    		'batchManage':row.batchManage,
			'changeManager':row.changeManager,
			'changeTime':row.changeTime,
			'createManager':row.createManager,
			'finance':row.finance,
			'finishTime':row.finishTime,
			'materialId':document.querySelector('#materialIdInput').value.trim(),
			'materialName':document.querySelector('#materialNameInput').value.trim(),
			'materialPrice':document.querySelector('#priceInput').value.trim(),
			'materialSpec':document.querySelector('#materialSpecInput').value.trim(),
			'materialType':document.querySelector('#materialTypeInput').value.trim(),
			'materialUnit':document.querySelector('#unitInput').value.trim(),
			'plus':document.querySelector('#plusInput').value.trim(),
			'startTime':row.startTime,
			'status':row.status,
			'stockNumber':document.querySelector('#stockNumberInput').value.trim(),
			'stockSafe':document.querySelector('#stockSafeInput').value.trim(),
			'viceId':document.querySelector('#viceIdInput').value.trim(),
			'warehousePosition':document.querySelector('#warehousePositionInput').value.trim(),
    	}
    	$.ajax({
			type: "POST",
			url: "Material/modifyMaterialProperty",//提交的接口
			contentType:'application/json;charset=utf-8',
			data:JSON.stringify(material),
			success:function(response){
				if(response=="modifyMaterialPropertySucceed"){
					reloadTable();
					alert('修改成功');
				}
				else{
					alert('网络繁忙，请稍后再试');
				}
			},
			error:function(response){
			}
		});
    	detailModalInit();
	}
    function cancelEditDetail() {
    	let row = globalRow;
    	document.querySelector('#detailDiv').innerHTML = `
			<div>
				<p><Strong>机物料ID: </Strong> </p>
				<input id='materialIdInput' value='${"${row.materialId}"} 'disabled='disabled' />
			</div>
			<div>
			<p><Strong>亚纶Id:  </Strong> </p>
			<input id='viceIdInput' value='${"${row.viceId}"} 'disabled='disabled' />
		</div>
			<div>
			<p><Strong>机物料名字:</Strong> </p>
			<input id='materialNameInput' value='${"${row.materialName}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>型号:</Strong> </p>    
			<input id='materialSpecInput' value='${"${row.materialSpec}"} 'disabled='disabled' />
		</div>
		<div>
		<p><Strong>仓库位置: </Strong> </p>
		<input id='warehousePositionInput' value='${"${row.warehousePosition}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>修改时间: </Strong> </p>
			<input id='changeTimeInput' value='${"${row.finishTime}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>参考价:    </Strong> </p>
			<input id='priceInput' value='${"${row.materialPrice}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>单位:    </Strong> </p>
			<input id='unitInput' value='${"${row.materialUnit}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>类型:</Strong> </p>
			<input id='materialTypeInput' value='${"${row.materialType}"} 'disabled='disabled' />
		</div>
		
		<div>
			<p><Strong>库存:    </Strong> </p>
			<input id='stockNumberInput' value='${"${row.stockNumber}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>安全库存:  </Strong> </p>
			<input id='stockSafeInput' value='${"${row.stockSafe}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>备注:  </Strong> </p>
			<input id='plusInput' value='${"${row.plus}"} 'disabled='disabled' />
		</div>
		 
		`;
    	detailModalInit();
	}
    function deleteThisMaterial() {
		$('#deleteModal').modal("show");
	}
    function showDetail(row) {
    	detailModalInit();
    	globalRow = row;
    	document.querySelector('#detailDiv').innerHTML =  `
			<div>
			<p><Strong>机物料ID: </Strong> </p>
			<input id='materialIdInput' value='${"${row.materialId}"} 'disabled='disabled' />
			</div>
			<div>
			<p><Strong>亚纶Id:  </Strong> </p>
			<input id='viceIdInput' value='${"${row.viceId}"} 'disabled='disabled' />
		</div>
			<div>
			<p><Strong>机物料名字:</Strong> </p>
			<input id='materialNameInput' value='${"${row.materialName}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>型号:</Strong> </p>    
			<input id='materialSpecInput' value='${"${row.materialSpec}"} 'disabled='disabled' />
		</div>
		<div>
		<p><Strong>仓库位置: </Strong> </p>
		<input id='warehousePositionInput' value='${"${row.warehousePosition}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>修改时间: </Strong> </p>
			<input id='changeTimeInput' value='${"${row.finishTime}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>参考价:    </Strong> </p>
			<input id='priceInput' value='${"${row.materialPrice}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>单位:    </Strong> </p>
			<input id='unitInput' value='${"${row.materialUnit}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>类型:</Strong> </p>
			<input id='materialTypeInput' value='${"${row.materialType}"} 'disabled='disabled' />
		</div>
		
		<div>
			<p><Strong>库存:    </Strong> </p>
			<input id='stockNumberInput' value='${"${row.stockNumber}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>安全库存:  </Strong> </p>
			<input id='stockSafeInput' value='${"${row.stockSafe}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>备注:  </Strong> </p>
			<input id='plusInput' value='${"${row.plus}"} 'disabled='disabled' />
		</div>
		 
		`;
    	$('#detailModal').modal("show");

	}
    
    function uploadImage(){
    	let row = globalRow;
    	let formData = new FormData();
        formData.append("file", document.getElementById("file1").files[0]); 
        formData.append("id",row.materialId);
        formData.append("name",row.materialName);
        $.ajax({
            url: "./upload",
            type: "POST",
            data: formData,
            /**
            *必须false才会自动加上正确的Content-Type
            */
            contentType: false,
            /**
            * 必须false才会避开jQuery对 formdata 的默认处理
            * XMLHttpRequest会对 formdata 进行正确的处理
            */
            processData: false,
            success: function (data) {
            	alert("上传成功！");
            	$('#editModal').modal("hide");
            	getAllInfo();
            },
            error: function () {
                alert("上传失败！");
            }
        });
   
    }
	function datePickerInit(){
		$('.form_date').datetimepicker({
			format:'yyyy-mm-dd',
			language : 'zh-CN',
			endDate : new Date(),
			weekStart : 1,
			todayBtn : 1,
			autoClose : 1,
			todayHighlight : 1,
			startView : 2,
			forceParse : 0,
			minView:2
		});
	}

	// 琛ㄦ牸鍒濆鍖�
	function userOperationRecordTableInit(){
		$.ajax({
			url:'Material/getMaterialInfo',
			method:'post',
			success:function(response){
			arrTemp = response;
			}
		});
		$('#userOperationRecordTable').bootstrapTable({
			columns:[{
	            field : 'materialId',
	            title : '零件编号'
	        },{
	            field : 'createManager',
	            title : '管理员姓名',
	            visible:false	            
	        },{
	            field : 'userName',
	            title : '用户姓名',
	            visible:false,
	            
	        },{
	            field : 'materialName',
	            title : '零件名'
	        },{
	            field : 'startTime',
	            title : '时间',
	            visible:false 
	        },{
	            field : 'stockNumber',
	            title : '剩余数量',
	           	formatter:function(value,row,index){
	           	
	           		var s = ` <button class="btn btn-success" type="button">
	           				${"${value}"}
	          			 	 </button>`;
	          		
	           		if(value>=row.stockSafe)
		           		return s;
	           		var t =  `<button class="btn btn-danger" type="button">
           					${"${value}"}
         			 	 </button>`;
	           		return t;
	           			
	           		
	           	},events:{
	           		
	           	}
	        },{
	        	field : '0',
	            title : '详细信息',
	           	formatter:function(value,row,index){
	           	
	           		var s = `<button class="btn btn-info showDetail" type="button" >
	           				详细信息
	          			 	 </button>`;	           		
	           		return s;
	           			
	           		
	           	},
	           	events:{
	           		'click .showDetail' : function(e, value,
							row, index) {
						showDetail(row);
						console.log(1234);
						
	           		}
	           	}
	        },
	        {
	            field : '1',
	            title : '图片',
	           	formatter:function(value,row,index){
	           	
	           		var s = `<button class="btn btn-primary showPic" type="button" >
	           				显示图片
	          			 	 </button><button class="btn btn-danger editPic" type="button" >
	           				修改图片
	          			 	 </button>`;	           		
	           		return s;
	           			
	           		
	           	},events:{
	           		
	           		'click .showPic' : function(e, value,
							row, index) {
						showPicture(row);
						
	           		},
	           		
	           		
	           		
	           		'click .editPic' : function(e ,value,
	           				row, index){
	           			editPicture(row);
	           		}
	           	}
	        }
			],
	        
	        url:'Material/getMaterialInfo',
	        sortable: true,   
	        pageList : [ 10,20,50],
	        pageSize : 10,
	        sidePagination: "client",  
	        clickToSelect : true,
	        pagination : true,
	        queryParams : queryParams,
		});
	   /*  $('#userOperationRecordTable').bootstrapTable({
	        columns:[{
	            field : 'id',
	            title : '零件编号'
	        },{
	            field : 'userID',
	            title : '管理员编号'
	        },{
	            field : 'userName',
	            title : '管理员姓名'
	        },{
	            field : 'operationName',
	            title : '支取情况'
	        },{
	            field : 'operationTime',
	            title : '时间'
	        },{
	            field : 'resultNumber',
	            title : '剩余数量'
	        }],
	        url : 'data/kits.json',
	        method : 'GET',
	        queryParams : queryParams,
            sidePagination : "server",
            dataType : 'json',
            pagination : true,
            pageNumber : 1,
            pageSize : 5,
            pageList : [ 5, 10, 25, 50, 100 ],
            clickToSelect : true,
            
	    }); */
	}

	// 琛ㄦ牸鍒锋柊
	
	
	function exportStorageAction() {	
		$('#export_storage').click(function() {
			$('#export_modal').modal("show");
			console.log('导出开始')
		})
		$('#export_storage_download').click(function(){
			JSONToExcelConvertor(arrTemp);			
			$('#export_modal').modal("hide");
		})

	}
	function tableRefresh(search_user_id) {
		search_user_id = search_user_id.trim()
		search_user_id = search_user_id.replace(/\*/g,'\\*')
		console.log({search_user_id})
		let filterdata = []
		$.ajax({
			url:'Material/getMaterialInfo',
			method:'post',
			success:function(response){
			
	//		console.log({search_user_id});
			response = response.filter(function(key){
	//			console.log(key)
				return key.materialId === search_user_id ||(key.materialName.search(search_user_id)!=-1);
			});		
			console.log({response});
			
			filterdata = JSON.stringify(response);
			filterdata=eval(filterdata);
			console.log({filterdata});
			
			$('#userOperationRecordTable').bootstrapTable('load', filterdata);
			arrTemp = filterdata;
			}
		});
		
	}
	

	// 鍒嗛〉鏌ヨ鍙傛暟
	function queryParams(params) {
		var temp = {/* 
			limit : params.limit,
			offset : params.offset, */
			materialId : search_user_id,
	/* 		startDate : search_start_date,
			endDate : search_end_date */
		}
		
		return temp;
	}

    // 鏌ヨ鎿嶄綔
    function searchActionInit(){
        $('#search_button').click(function(){
            search_user_id = $('#user_id').val();
            search_start_date = $('#start_date').val();
            search_end_date = $('#end_date').val();
            tableRefresh(search_user_id);
        })
    }
</script>
<div class="panel panel-default">
    <ol class="breadcrumb">
        <li>查询</li>
    </ol>
    <div class="panel-body">
        <div class="row">
           <div class="col-md-3" >
                
                    <div class="form-group">
                        <input type="text" id="user_id" class="form-control" placeholder="零配件Id或名称" style="width:50%">
                    </div>
                	<button class="btn btn-sm btn-default" id="export_storage" >
						<span class="glyphicon glyphicon-export"></span> <span>导出</span>
					</button>
            </div>
            
       <!--      <div class="col-md-7">
                <form action="" class="form-inline">
                    <label class="form-label">日期范围</label>
                    <input class="form_date form-control" id="start_date" placeholder="开始日期">
                    <label class="form-label">&nbsp;&nbsp;-&nbsp;&nbsp;</label>
                    <input class="form_date form-control" id="end_date" placeholder="结束日期">
                </form>
            </div> -->
            <div class="col-md-2">
                <button class="btn btn-success" id="search_button">
                    <span class="glyphicon glyphicon-search"></span> <span>查询</span>
                </button>
            </div>
        </div>
        <div class="row" style="margin-top:25px">
            <div class="col-md-12">
                <table class="table table-striped" id="userOperationRecordTable"></table>
            </div>
        </div>
    </div>
</div>

<!-- modal -->
<div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="imageModalLabel"></h4>
      </div>
      <div class="modal-body">
		<img class='modalImg' id="imgUrl" src="./media/images/backgroundPic.png" alt="没有图片，请上传" class="img-rounded">
		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="identifyExport" data-dismiss="modal">确认</button>
      </div>
    </div>
  </div>
</div>

<!-- EditModal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="imageModalLabel"></h4>
      </div>
      <div class="modal-body">
	    <input type="file" name="file" id="file1"><br>
	    <input type="submit" value="提交" id="uploadBtn" onclick="uploadImage()">
	    
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="identifyExport" data-dismiss="modal">确认</button>
      </div>
    </div>
  </div>
</div>

<!-- DetailModal -->
<div class="modal fade" id="detailModal" tabindex="-1" role="dialog"
	aria-labelledby="imageModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="imageModalLabel"></h4>
			</div>
			<div class="modal-body">
				<div id="detailDiv"></div>
			</div>
			<div class="modal-footer">
				

			</div>
		</div>
	</div>
</div>
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
<!-- 确认是否删除Modal -->
<div class="modal fade" id="deleteModal" table-index="-1" role="dialog"
	aria-labelledby="imageModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="imageModalLabel">确认删除</h4>
			</div>
			<div class="modal-body">
				<div class="col-md-8 col-sm-8">
					<h3>是否删除</h3>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-danger" type="button" id="confirmDeleteMaterial" onclick="confirmDelMaterial()">
					<span>确定删除</span>
				</button>
			</div>
		</div>
	</div>
</div>