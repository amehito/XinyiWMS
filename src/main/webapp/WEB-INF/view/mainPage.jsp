<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
 <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>仓库管理系统</title>
    <script src="./js/toExcel.js"></script>
    <link rel="stylesheet" type="text/css"
          href="./css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="./css/bootstrap-table.css">
    <link rel="stylesheet" type="text/css"
          href="./css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css"
          href="./css/jquery-ui.css">
    <link rel="stylesheet" type="text/css"
          href="./css/mainPage.css">
    <link rel="stylesheet" type="text/css"
          href="./css/mainPage.css">
</head>
<body>
<!-- 导航栏 -->
<div id="navBar">
    <!-- 此处加载顶部导航栏 -->
    <!-- 导航栏 -->
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <!-- 导航栏标题 -->
            <div class="navbar-header">
                <a href="javascript:void(0)" class="navbar-brand home">仓库管理系统</a>
            </div>

            <!-- 导航栏下拉菜单；用户信息与注销登陆 -->
            <div>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle"
                           data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
                            <span>欢迎&nbsp;</span> <span id="nav_userName">用户名:<%=session.getAttribute("UserName") %></span>
                            <!--小三角-->
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-通过session获取的name的值-->
                            <shiro:hasRole name="commonsAdmin">
                                <li>
                                    <a href="#" id="editInfo"> <span
                                            class="glyphicon glyphicon-pencil"></span> &nbsp;&nbsp;修改个人信息</a></li>
                            </shiro:hasRole>
                            <li>
                                <a href="javascript:void(0)" id="signOut"> <span
                                        class="glyphicon glyphicon-off"></span> &nbsp;&nbsp;注销登录
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>

<div class="container-fluid" style="padding-left: 0px;">
    <div class="row">
        <!-- 左侧导航栏 -->
        <div id="sideBar" class="col-md-2 col-sm-3">
            <!--  此处加载左侧导航栏 -->
            <!-- 左侧导航栏  -->
            <!--依照accordion为parent-->
            <div class="panel-group" id="accordion">
               <!--  <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a>链接至class为collapse1的div，即库存管理与出入库管理
                            <a href="#collapse1" data-toggle="collapse" data-parent="#accordion"
                               class="parentMenuTitle collapseHead">库存管理</a>
                            <div class="pull-right">
                                <span class="caret"></span>
                            </div>
                        </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse collapseBody">
                        <div class="panel-body">
                            <ul class="list-group">
                                若为普通管理员
                                <shiro:hasRole name="commonsAdmin">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/storageManagementCommon.jsp">库存查询</a>
                                    </li>
                                </shiro:hasRole>
                                若为超级管理员
                                <shiro:hasRole name="systemAdmin">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/storageManagement.jsp">库存查询</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id=""
                                           class="menu_item"
                                           name="pagecomponent/stockRecordManagement.html">出入库记录</a>
                                    </li>
                                </shiro:hasRole>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="#collapse2" data-toggle="collapse" data-parent="#accordion"
                               class="parentMenuTitle collapseHead">出入库管理</a>
                            <div class="pull-right">
                                <span class="caret"></span>
                            </div>
                        </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse collapseBody in">
                        <div class="panel-body">
                            <shiro:hasRole name="systemAdmin">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/stock-inManagement.jsp">货物入库</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/stock-outManagement.jsp">货物出库</a>
                                    </li>
                                </ul>
                            </shiro:hasRole>
                            <shiro:hasRole name="commonsAdmin">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="#">货物入库</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="#">货物出库</a>
                                    </li>
                                </ul>
                            </shiro:hasRole>
                        </div>
                    </div>
                </div>
                <shiro:hasRole name="systemAdmin">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a href="#collapse3" data-toggle="collapse" data-parent="#accordion"
                                   class="parentMenuTitle collapseHead">人员管理</a>
                                <div class="pull-right	">
                                    <span class="caret"></span>
                                </div>
                            </h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse collapseBody">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/repositoryAdminManagement.jsp">仓库管理员管理</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole>
                <shiro:hasRole name="systemAdmin">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a href="#collapse4" data-toggle="collapse" data-parent="#accordion"
                                   class="parentMenuTitle collapseHead">基础数据</a>
                                <div class="pull-right	">
                                    <span class="caret"></span>
                                </div>
                            </h4>
                        </div>
                        <div id="collapse4" class="panel-collapse collapse collapseBody">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/supplierManagement.jsp">供应商信息管理</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/customerManagement.jsp">客户信息管理</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/goodsManagement.jsp">货物信息管理</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/repositoryManagement.jsp">仓库信息管理</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole> -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="#collapse5" data-toggle="collapse" data-parent="#accordion"
                               class="parentMenuTitle collapseHead">系统维护</a>
                            <div class="pull-right	"><span class="caret"></span></div>
                        </h4>
                    </div>
                    <div id="collapse5" class="panel-collapse collapse collapseBody">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <a href="javascript:void(0)" id="" class="menu_item"
                                       name="pagecomponent/passwordModification.jsp">更改密码</a>
                                </li>
                                <shiro:hasRole name="systemAdmin">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id=""
                                           class="menu_item" name="pagecomponent/userOperationRecorderManagement.html">出库日志</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id=""
                                           class="menu_item" name="pagecomponent/accessRecordManagement.html">扫描二维码</a>
                                    </li>
                                </shiro:hasRole>
                            </ul>
                        </div>
                    </div>
                         
                </div>
                
                <!-- 新益 -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="#collapse6" data-toggle="collapse" data-parent="#accordion" class="parentMenuTitle collapseHead collapsed" aria-expanded="false">零配件支取查询</a>
                            <div class="pull-right	"><span class="caret"></span></div>
                        </h4>
                    </div>
                    <div id="collapse6" class="panel-collapse collapse collapseBody" aria-expanded="false">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/goodsSearch.jsp">查询</a>
                                </li>
                                
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/userOperationRecorderManagement.html">出库日志</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/stock-inManagementRecords.html">入库日志</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/stock-inManagement.html">入库</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/stock-outManagement.jsp">出库</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/allRecord.html">记录汇总</a>
                                    </li>
                                	<li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/board.html">领料面板</a>
                                    </li>
                            </ul>
                        </div>
                    </div>
                    
                    
                    
                </div>
                <!-- 新益 -->
                <!-- collapse7 -->
               	
               		
             	  <shiro:hasRole name="admin">  	
             	   	<div class="panel panel-default">
                	    <div class="panel-heading">
                      	  <h4 class="panel-title">
                       	     <a href="#collapse7" data-toggle="collapse" data-parent="#accordion" class="parentMenuTitle collapseHead collapsed" aria-expanded="false">管理员操作</a>
                       	     <div class="pull-right	"><span class="caret"></span></div>
                      	  </h4>
                  		</div>
                    <div id="collapse7" class="panel-collapse collapse collapseBody" aria-expanded="false">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/addUser.jsp">添加用户</a>
                                </li>
                                <li class="list-group-item">
                                    <a href="javascript:void(0)" id="" class="menu_item" name="pagecomponent/manageKits.jsp">修改配件</a>
                                </li>
                                    
                                
                            </ul>
                        </div>
                    </div>                 
             	   </div>
             	  
             	</shiro:hasRole>
             	   
               
               	
                <!--  -->
            </div>
        </div>

        <!-- 面板区域 -->
        <div id="panel" class="col-md-10 col-sm-9">
       
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
        <table  border=1; cellpadding=6; cellspacing=0; align=center id="modalTable">
				<tr align=center>
					<td colspan="1">领料单</td>
					<td>姓名:</td>
					<td colspan="3" id="userName"></td>
				</tr>
		
				<tr align=center>
					<td>领料日期:</td>
					<td colspan="4" id="time"></td>
				</tr>
				
				<tr align=center>
					<td colspan="5">领料信息</td> 
				</tr>
				<tr align=center>
					<td>物品名称</td>
					<td>物品编号</td>
					<td>数量</td>
					<td>单位</td>
					<td>仓位</td>
				</tr>
				<tbody id="information" >
				<tr align=center>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				</tbody>
				
			</table>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-danger" id="declineExport" data-dismiss="modal">拒绝</button>
        <button type="button" class="btn btn-primary" id="identifyExport" data-dismiss="modal">确认出库</button>
      </div>
    </div>
  </div>
</div>

<div id="notifyPanel"></div>
<span id="requestPrefix" class="hide">${sessionScope.requestPrefix}</span>

<script type="text/javascript"
        src="./js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
        src="./js/bootstrap.min.js"></script>
<script type="text/javascript"
        src="./js/jquery-ui.min.js"></script>
<script type="text/javascript"
        src="./js/bootstrapValidator.min.js"></script>
<script type="text/javascript"
        src="./js/bootstrap-table.min.js"></script>
<script type="text/javascript"
        src="./js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript"
        src="./js/jquery.md5.js"></script>
<script type="text/javascript"
        src="./js/mainPage.js"></script>
<script type="text/javascript"
        src="./js/ajaxfileupload.js"></script>
<script type="text/javascript"
        src="./js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
        src="./js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="./js/notice.js"></script>
        
</body>
</html>