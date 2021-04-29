//根据角色名称获取该用户的权限
$(".roleNameAuthority").click(function(){
	var authority = $(this);
	var roleId = authority.attr("roleid");
	var roleName = authority.attr("rolename");
	$("#roleidhide").val(roleId);

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
					listr += "<li class=\"functiontitle\"><input id=\"functiontitle"+data[i].mainFunction.id+"\" funcid=\""+data[i].mainFunction.id+"\" type=\"checkbox\" onchange='mainFunctionSelectChange(this,"+data[i].mainFunction.id+");'/>"+data[i].mainFunction.functionNmae+"</li>";
					for (var j=0;j<data[i].subFunctions.length;j++){
						listr += "<li><input funcid=\""+data[i].subFunctions[j].id+"\" type='checkbox'>"+data[i].subFunctions[j].functionName+"</li>";
					}
					listr += "</ul></li>";
				}
				$("#functionList").html(listr);
			}
		}
	});
});
function mainFunctionSelectChange(obj,id){
	if (obj.checked){
		$("#subfuncul"+id+" :checkbox").attr("checked",true);
	}else{
		$("#subfuncul"+id+" :checkbox").attr("checked",true);
	}
}