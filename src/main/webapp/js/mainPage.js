$(function() {
	menuClickAction();
	welcomePageInit();
	signOut();
	getRequestPrefix();
	homePage();
});

// 获取请求前缀
function getRequestPrefix(){
	requestPrefix = $('#requestPrefix').text();
}

// 加载欢迎界面
function welcomePageInit(){
	$('#panel').load('pagecomponent/welcomePage.jsp');
}

// 跳回首页
function homePage(){
	$('.home').click(function(){
		$('#panel').load('pagecomponent/welcomePage.jsp');
	})
}

// 侧边栏连接点击动作
function menuClickAction() {
	$(".menu_item").click(function() {
		var url = $(this).attr("name");
		$('#panel').load(url);
	})
}

// 注销登陆
function signOut() {
	$("#signOut").click(function() {
		$.ajax({
			type : "GET",
			url : "account/logout",
			dataType : "json",
			contentType : "application/json",
			success:function(response){
				//刷新
				console.log('退出');
				window.location.href="login.html";
			},error:function(response){
				
			}
		})
	})
}
document.querySelector('#declineExport').addEventListener('click',function(){
	fetch('Material/declineRequest',{
		headers:new Headers({
           "Content-Type":"application/json",
		}),
		method:'POST',
		body:globalId
	})
	.then(response => response.text())
	.then(data=>{alert(data)});
});
let passRequest = document.querySelector('#identifyExport');
	passRequest.addEventListener('click',function(){
		fetch('Material/passRequest',{
			headers:new Headers({
	           "Content-Type":"application/json",
			}),
			method:'POST',
			body:globalId
		})
		.then(response => response.text())
		.then(data=>{alert(data)});
		
});