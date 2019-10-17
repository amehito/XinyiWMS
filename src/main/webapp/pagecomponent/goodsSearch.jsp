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
 	let globeRow ;
 	
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
    	detailModalInit();
    })
	
    function showPicture(row){
    	let temp = allImageInfo.filter(item => item.materialId ==row.materialId);
    	let url = temp.length>0?temp[0].imageName:'';
    	console.log({url});
    	document.querySelector('#imgUrl').src = '/images/'+url;
    	$('#myModal').modal("show");
    }
    function editPicture(row){
    	$('#editModal').modal("show");
    	globeRow = row;
    	console.log(12);
    }
    function editState(){
    	document.querySelectorAll('#detailModal input').forEach(item=>{item.disabled=''});
    	document.querySelector('#detailModal .modal-footer').innerHTML = `<button type="button" class="btn btn-primary" id=""
			onclick = 'modifyEditDetail()'>确定</button><button type="button" class="btn btn-info" id=""
			onclick = 'cancelEditDetail()'>取消</button><button type="button" class="btn btn-danger modalLeft" id=""
			onclick = 'deleteThisMaterial()'>删除</button>`;
    }
    
    function detailModalInit() {
    	document.querySelector('#detailModal .modal-footer').innerHTML =`
    		<button type="button" class="btn btn-info" id=""
			onclick = 'editState()'>编辑</button>
    	`;
	}
    function modifyEditDetail() {
    	detailModalInit();
	}
    function cancelEditDetail() {
    	detailModalInit();
	}
    function deleteThisMaterial() {
		$('#myModalLabel').modal("show");
	}
    function showDetail(row) {
    	console.log({row});
    	console.log(row.materialId);
		let detailHtml = ``;
    	document.querySelector('#detailDiv').innerHTML = `
			<div>
				<p><Strong>机物料ID: </Strong> </p>
				<input value='${"${row.materialId}"} 'disabled='disabled' />
			</div>
			<div>
			<p><Strong>亚纶Id:  </Strong> </p>
			<input value='${"${row.viceId}"} 'disabled='disabled' />
		</div>
			<div>
			<p><Strong>机物料名字:</Strong> </p>
			<input value='${"${row.materialName}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>修改时间: </Strong> </p>
			<input value='${"${row.finishTime}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>价格:    </Strong> </p>
			<input value='${"${row.materialPrice}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>单位:    </Strong> </p>
			<input value='${"${row.materialUnit}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>类型:</Strong> </p>
			<input value='${"${row.materialType}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>型号:</Strong> </p>    
			<input value='${"${row.materialSpec}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>库存:    </Strong> </p>
			<input value='${"${row.stockNumber}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>安全库存:  </Strong> </p>
			<input value='${"${row.stockSafe}"} 'disabled='disabled' />
		</div>
		<div>
			<p><Strong>备注:  </Strong> </p>
			<input value='${"${row.plus}"} 'disabled='disabled' />
		</div>
		 
		`;
    	$('#detailModal').modal("show");

	}
    
    function uploadImage(){
    	let row = globeRow;
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
	

	function tableRefresh(search_user_id) {
		
		let filterdata = [];
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
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
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
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
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body">
				<div id="detailDiv"></div>
			</div>
			<div class="modal-footer">
				

			</div>
		</div>
	</div>
</div>

<!-- 确认是否删除Modal -->
<div class="modal fade" id="export_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">确认删除</h4>
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
				<button class="btn btn-danger" type="button" id="confirmDeleteMaterial">
					<span>确定删除</span>
				</button>
			</div>
		</div>
	</div>
</div>