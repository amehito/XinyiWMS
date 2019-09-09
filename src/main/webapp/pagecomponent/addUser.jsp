<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
<script type="text/javascript">
	function submit() {
		var data = {
				"newUsername":$('#newUsername').val(),
				"newPassword":$('#newPassword').val()
		}
		
		$.ajax({
			type: "POST",
			url:"./account/register",
			data:JSON.stringify(data),
			dataType:"text",
			contentType : "application/json;charset=utf8",
			success:function(response){
				// 接收并处理后端返回的响应e'd'
				if(response.result == "error"){
					var errorMessage;
					console.log(response.errorMsg);
					if(response.msg == "passwordError"){
						errorMessage = "密码错误";
						field = "oldPassword";
					}else if(response.msg == "passwordUnmatched"){
						errorMessage = "密码不一致";
						field = "newPassword";
					}
					
					$("#oldPassword").val("");
					$("#newPassword").val("");
					$("#newPassword_re").val("");
					bv.updateMessage(field,'callback',errorMessage);
					bv.updateStatus(field,'INVALID','callback');
				}else{
					// 否则更新成功，弹出模态框并清空表单
					$('#passwordEditSuccess').modal('show');
					$('#reset').trigger("click");
					$('#form').bootstrapValidator("resetForm",true); 
				}
				
			},
			error:function(response){
				//window.location.href = "./";
		//		location.reload();
				alert("添加成功");
			}
		});
	}
</script>
<div class="panel panel-default">
   <!-- 修改密码面板 -->
<div class="panel panel-default">
	<!-- 面包屑 -->
	<ol class="breadcrumb">
		<li>添加用户</li>
	</ol>

	<div class="panel-body">
		<!--  修改密码主体部分 -->
		<div class="row">
			<div class="col-md-4 col-sm-2"></div>
			<div class="col-md-4 col-sm-8">

			
					

					<div class="form-group">
						<label for="" class="control-label col-md-4 col-sm-4" > 输入名字: </label>
						<div class="col-md-8 col-sm-8">
							<input type="text" class="form-control" id="newUsername"
								name="newUsername">
						</div>
					</div>

					<div class="form-group">
						<label for="" class="control-label col-md-4 col-sm-4"> 输入新密码: </label>
						<div class="col-md-8 col-sm-8">
							<input type="password" class="form-control" id="newPassword"
								name="newPassword">
						</div>
					</div>

					<div class="form-group">
						<label for="" class="control-label col-md-4 col-sm-4"> 确认新密码: </label>
						<div class="col-md-8 col-sm-8 has-feedback">
							<input type="password" class="form-control" id="newPassword_re"
								name="newPassword_re">
						</div>
					</div>

					<div>
						<div class="col-md-4 col-sm-4">
						</div>
						<div class="col-md-4 col-sm-4">
							<button  class="btn btn-success" onclick="submit()">
								&nbsp;&nbsp;&nbsp;&nbsp;确认添加&nbsp;&nbsp;&nbsp;&nbsp;</button>
						</div>
						<div class="col-md-4 col-sm-4"></div>
					</div>
					<div class="col-md-4 col-sm-4">
						<input id="reset" type="reset" class="btn btn-primary " >
					</div>
								
			</div>
			<div class="col-md-4 col-sm-2"></div>
		</div>

		<div class="row">
			<div class="col-md-3 col-sm-1"></div>
			<div class="col-md-6 col-sm-10">
				<div class="alert alert-info" style="margin-top: 50px">
					<p>登录密码修改规则说明：</p>
					<p>1.密码长度为6~16位，至少包含数字、字母、特殊符号中的两类，字母区分大小写</p>
					<p>2.密码不可与账号相同</p>
				</div>
			</div>
			<div class="col-md-3 col-sm-1"></div>
		</div>
	</div>
</div>
</div>

<div class="modal fade" id="passwordEditSuccess"
	tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close"
					data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					提示
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-4"></div>
					<div class="col-md-4">
						<div style="text-align: center;">
							<img src="media/icons/success-icon.png" alt=""
								style="width: 100px; height: 100px;">
						</div>
					</div>
					<div class="col-md-4"></div>
				</div>
				<div class="row" style="margin-top: 10px">
					<div class="col-md-4"></div>
					<div class="col-md-4" style="text-align:center;"><h4>用户添加成功</h4></div>
					<div class="col-md-4"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
</div>
</body>
</html>