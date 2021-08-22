//根据角色名称获取该用户的权限
$(".roleNameAuthority").click(function(){
	var authority = $(this);
	var roleId = authority.attr("roleid");
	var roleName = authority.attr("rolename");
	$("#roleidhide").val(roleId);
	$("#selectrole").html("当前配置角色为："+roleName);
	$.ajax({
		url:'/backend/functions.html',
		type:'post',
		data:{"roleId":roleId},
		dataType:'json',
		error:function(){
			alert("服务器端错误。");
		},
		success:function(data){
			if(data == "nodata"){
				alert("对不起,功能列表获取失败,请重试。");
			}else{
				//将来自服务器端的JSON格式字符串转换成JSON对象
				//var json = eval("("+data+")");
				var listr = '';
				for(var i=0;i<data.length;i++){  //一级主菜单
					listr += "<li>"; //列表项  <ul><li></li></ul>无序列表
					listr += "<ul id=\"subfuncul"+data[i].mainFunction.id+"\" class=\"subfuncul\">"; //为一级菜单拼接了一个<ul><li>
					listr += "<li class=\"functiontitle\"><input id=\"functiontitle"+data[i].mainFunction.id+"\" funcid=\""+data[i].mainFunction.id+"\" type=\"checkbox\" onchange='mainFunctionSelectChange(this,"+data[i].mainFunction.id+");'/>"+data[i].mainFunction.functionName+"</li>";
					for (var j=0;j<data[i].subFunctions.length;j++){
						listr += "<li><input onchange=\"subFunctionSelectChange(this,\""+data[i].mainFunction.id+"\");\" funcid=\""+data[i].subFunctions[j].id+"\" type='checkbox'>"+data[i].subFunctions[j].functionName+"</li>";
					}
					listr += "</ul></li>";
				}
				$("#functionList").html(listr);

				//回显加载 循环遍历所有的复选框 一个复选框对应一个功能菜单 根据角色ID和菜单ID确定权限对象
				$("#functionList :checkbox").each(function(){
					var checkbox = $(this);
					var functionId = $(this).attr("funcid");
					//alert(functionId);
					//获取当前选中的角色ID
					var roleId = $("#roleidhide").val();
					$.ajax({
						url:'/backend/getDefaultAuthority.html',
						type:'post',
						data:{'roleId':roleId,'functionId':functionId}, //根据角色ID以及功能菜单ID 确定一个权限对象
						dataType:'html',
						timeout:1000,
						error:function(){
							//alert("error");
						},
						success:function(data){

							if(data !="" && data=="success"){
								checkbox.attr("checked",true);
							}else{
								checkbox.attr("checked",false);
							}
						}
					});
				});
			}
		}
	});
});
function mainFunctionSelectChange(obj,id){
	if (obj.checked){
		$("#subfuncul"+id+" :checkbox").attr("checked",true);
	}else{
		$("#subfuncul"+id+" :checkbox").attr("checked",false);
	}
}
function subFunctionSelectChange(obj,id){
	if(obj.checked){
		$("#functiontitle"+id).attr("checked",true);
	}
}

$("#confirmsave").click(function(){
	if (confirm("您确定要修改当前角色的权限吗？")){
		//获取当前角色ID
		var ids = $("#roleidhide").val()+'-';
		$("#functionList :checkbox").each(function(){
			//$('#checkbox').attr('checked'); 返回的是checked或者是undefined
			if($(this).attr("checked") == 'checked'){
				ids += $(this).attr("funcid")+"-";
			}
		});
		alert(ids);
		$.ajax({
			url:'/backend/modifyAuthority.html',
			type:'post',
			data:{"ids":ids},
			dataType:'html',
			timeout:1000,
			error:function(){
				//alert("error");
			},
			success:function(data){

			}
		});
	}
});