const ms = 1000;
let info  ;
let globalId;
let materialInfo = [];
let a={"admin":"admin","time":"2019-07-19","materials":[{"material":"DN20铝塑对节","materialId":"21270302001","number":1,"unit":"只"},{"material":"2-4kw立式电动机","materialId":"16060115002","number":1,"unit":"台"},{"material":"2路继电器座","materialId":"11020102001","number":1,"unit":"只"},{"material":"剪刀","materialId":"10000000001","number":1,"unit":"个"},{"material":"4-7.5kw立式电机","materialId":"16060117001","number":12,"unit":"台"}]};
function seeInformation(id){
	globalId = id;
	a = info.filter((item) =>  (item.id === id));
	console.log({a,info});
	
	let userName=document.getElementById('userName').innerText=a[0].name;
	let time=document.getElementById('time').innerText=a[0].time;
	let information=document.getElementById('information');
	information.innerHTML="";
	let json = eval(a[0].materials);
	console.log({json});
	materialInfo = [];
	for(let i=0;i<json.length;i++){
		
		information.innerHTML+="<tr align=center>"+
				"<td>"+json[i].material+"</td>"+
				"<td>"+json[i].materialId+"</td>"+
				"<td><input style='width:100px' value='"+json[i].number+"'></td>"+
				"<td>"+json[i].unit+"</td>"+
				"<td>"+json[i].warehousePosition+"</td>"+
			"</tr>";
		
	}
	
	marked(id);
}
function marked(id){
	//把样式改成别的颜色，下次再写
}
window.onload = function(){
	//setInterval(receive,ms);
	notifiInit();
	setInterval(receive,ms);
}

function notifiInit(){
	$.ajax({
		url:'Material/notificationInit',
		type:'GET',
		success:function(text){
		
		showNotification(text);
		}
	})
}

function showNotification(text){
	document.getElementById('notifyPanel').innerHTML = '';
	info = text;
	console.log({info});
	function show(item){
		console.log({item});
		let html = `
		<div class="panel panel-default noticePanel" data-toggle="modal" data-target="#myModal" onclick="seeInformation(${item.id})" style="background:#50b7c1" >
			<div class="panel-body">
				有一条来自<strong>${item.name}</strong>的取料请求，编号为<strong>${item.id}</strong>  点击我查看 :)
  			</div>
		</div>
			`;
		document.getElementById('notifyPanel').innerHTML += html;
		
		
	}
	text.forEach( item => (show(item)));
	
	
	console.log(text);
//	document.body.appendChild(notifyDiv);

}
let receive = function(){
	$.ajax({
		url:'Material/showNotification',
		data:'notice',
		type:'POST',
		success:function(data){
			let text = data;
			
			showNotification(text);
		},
		error:function(data){
		}
	});
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

document.querySelector('#identifyExport').addEventListener('click',function(){
	materialInfo = [];
	document.querySelectorAll('#information tr').forEach(item=>{materialInfo.push({
		materialId:item.children[1].innerText,
		number:item.children[2].children[0].value
	})});
	
		$.ajax({
			type: "POST",
			url: "Material/passRequest",//提交的接口
			data: {
				/*传值*/
				globalId:globalId,
				materialInfo:eval(materialInfo)
			},
			success:function(response){
				alert("提交成功");
				location.reload()
			},
			error:function(response){
				alert("提交成功");
				location.reload()
			}
		});
});