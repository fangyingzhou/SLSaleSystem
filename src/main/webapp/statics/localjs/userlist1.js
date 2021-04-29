//用户修改 e.preventDefault() 阻止submit的提交事件 触发修改按钮的click()事件 发送一个ajax请求
$(".modifyuser").click(function (e){
	var m_id = $(this).attr("id");
	$.ajax({
		url:'/backend/getuser.html',
		type:'post',
		data:{id:m_id}, //发送到服务器的数据。将自动转换为请求字符串格式
		dataType:'html', //返回纯文本html信息
		timeout:1000,
		error:function(){
			alert("error");
		},
		success:function(data){
			var m = eval("("+data+")"); //将控制器端返回的字符串转换为json对象
			var roleId = m.roleId;
			var roleName = m.roleName;
			$("#m_roleId").html('');
			if (roleId == null || roleId == ''){
				$("#m_roleId").append("<option value='' selected = 'selected'>==请选择==</option>");
			}
			//循环时的roleId 等于当前用户的roleId 即被选中
			for(var i=0;i<roleListJson.length-1;i++){
				if (roleListJson[i].id == roleId){
					$("#m_roleId").append("<option value=\""+roleId+"\" selected=\"selected\">"+roleName+"</option>");
				}else{
					$("#m_roleId").append("<option value=\""+roleListJson[i].id+"\">"+roleListJson[i].roleName+"</option>");
				}
			}

			//会员类型 只有当roleId = 2即为会员的时候 才会存在会员类型
			$("#m_selectusertypename").val(m.userTypeName);

			//角色为会员
			if(roleId == 2){
				var userType = m.userType;  //对应数据字段表中的valueId
				var userTypeName = m.userTypeName;

				$("#m_selectusertype").html('');
				if (userType == null || userType == ''){
					$("#m_selectusertype").append("<option value=\"\" selected=\"selected\">--请选择--</option>");
				}else{
					$.post('/backend/loadUserTypeList.html',{'s_role':roleId},function(result){
						if(result != ""){
							for(var i=0;i<result.length;i++){
								if(result[i].valueId == userType){
									$("#m_selectusertype").append("<option value=\""+userType+"\" selected=\"selected\">"+userTypeName+"</option>");
								}else{
									$("#m_selectusertype").append("<option value=\""+result[i].valueId+"\">"+result[i].valueName+"</option>");
								}
							}
						}else{
							alert("会员类型加载失败!");
						}
					},'json');
				}
				//角色为非会员
			}else if(roleId==1){
				$("#m_selectusertype").append("<option value=\"\" selected=\"selected\">--请选择--</option>")
			}
			$("#m_id").val(m.id);
			$("#m_logincode").val(m.loginCode);
			$("#m_username").val(m.userName);

			$("#m_sex").html('');
			var sex = m.sex;
			if(sex==''){
				$("#m_sex").append("<option value=\"\" selected=\"selected\">--请选择--</option><option value=\"男\">男</option><option value=\"女\">女</option>")
			}else if (sex=='男'){
				$("#m_sex").append("<option value=\"男\" selected=\"selected\">男</option><option value=\"女\">女</option>");
			}else if(sex=='女'){
				$("#m_sex").append("<option value=\"男\">男</option><option value=\"女\" selected=\"selected\">女</option>");
			}

			//证件类型
			var cardType = m.cardType;
			var cardTypeName = m.cardTypeName;
			$("#m_cardtype").html('');
			if(cardType == null || cardType == ''){
				$("#m_cardtype").append("<option value=\"\" selected=\"selected\">--请选择--</option>")
			}
			for(var i=0;i<cardTypeListJson.length-1;i++){
				if(cardTypeListJson[i].valueId == cardType){
					$("#m_cardtype").append("<option value=\""+cardType+"\" selected=\"selected\">"+cardTypeName+"</option>");
				}else{
					$("#m_cardtype").append("<option value=\""+cardTypeListJson[i].valueId+"\">"+cardTypeListJson[i].valueName+"</option>");
				}
			}

			$("#m_idcard").val(m.idCard);
			$("#m_birthday").val(m.birthday);
			$("#m_country").val(m.mobile);
			$("#m_mobile").val(m.country);
			$("#m_email").val(m.email);
			$("#m_postcode").val(m.postCode);
			$("#m_bankname").val(m.bankName);
			$("#m_bankaccount").val(m.bankAccount);
			$("#m_accountholder").val(m.accountHolder);
			$("#m_refercode").val(m.referCode);
			$("#m_createtime").val(m.createTime);
			$("#m_useraddress").val(m.userAddress);
			$("#m_refercode").val(m.referCode);

			var m_idCardPicPath = m.idCardPicPath;

			if(m_idCardPicPath == null || m_idCardPicPath == ""){
				$("#m_uploadbtnID").show();
			}else{
				$("#m_idPic").append("<img src=\""+m_idCardPicPath+"?m="+Math.random()+"\"/>");
			}
			$("#m_isstart").html('');
			var isStart = m.isStart;
			if(isStart == null || isStart ==''){
				$("#m_isstart").append("<option value=\"\" selected=\"selected\">--请选择--</option><option value=\"1\">启用</option><option value=\"2\">未启用</option>");
			}else if(isStart == 1){
				$("#m_isstart").append("<option value=\"1\" selected=\"selected\">启用</option><option value=\"2\">未启用</option>");
			}else if(isStart == 2){
				$("#m_isstart").append("<option value=\"1\">启用</option><option value=\"2\" selected=\"selected\">未启用</option>");
			}
			e.preventDefault(); //阻止提交按钮的submit事件
			$("#modifyUserDiv").modal("show"); //modal为bootstrap的模块狂
		}
	})
});

//当角色下拉框的值发生改变的时候触发change()事件 级联操作
$("#m_roleId").change(function(){
	//find()搜索所有与指定表达式匹配的元素
	var roleName = $("#m_roleId").find("option:selected").text(); //text()取得所有匹配元素的内容 获取当前选中项的text
	$("#m_rolename").val(roleName);
	$("#m_selectusertype").empty();
	var roleId = $("#m_roleId").val();
	if (roleId == 2){
		$.post('/backend/loadUserTypeList.html',{"s_role":roleId},function(data){
			if(data != ""){
				for(var i=0;i<data.length;i++){
					$("#m_selectusertype").append("<option value=\""+data[i].valueId+"\">"+data[i].valueName+"</option>");
				}
			}else{
				alert("加载会员类型失败!");
			}
		},'json');
	}else{
		$("#m_selectusertype").append("<option value=\"\" selected=\"selected\">--请选择--</option>");
	}
	//修改操作 会员类型下拉框待用户选取 会员类型的名称需要置为空
	$("#m_selectusertypename").val('');
});

//当会员类型下拉框的值发生改变时触发change()事件
$("#m_selectusertype").change(function(){
	var userTypeName = $("#m_selectusertype").find("option:selected").text();
	$("#m_selectusertypename").val(userTypeName);
});

//证件类型下拉框的值发生改变的时候触发的事件
$("#m_cardtype").change(function(){
	var cardTypeName = $("#m_cardtype").find("option:selected").text();
	$("#m_cardtypename").val(cardTypeName);
});

//图片上传
$("#m_uploadbtnID").click(function(){
	TajaxFileUpload($("#m_id").val(),'m_fileInputID','m_uploadbtnID','m_idPic','m_fileInputIDPath');
});

$("#m_uploadbtnBank").click(function(){
	TajaxFileUpload($("#m_id").val(),'m_fileInputBank','m_uploadbtnBank','m_bankPic','m_fileInputBankPath');
});
function TajaxFileUpload(flag,t1,t2,t3,t4) {
	//判断文件选择框的值是否位空 注意file文件选择框的为只读属性 只能读取
	if ($("#" + t1 + "").val() == '' || $("#" + t1 + "").val() == null) {
		alert("请选择上传文件！");
	} else {
		$.ajaxFileUpload
		({
			url: '/backend/upload.html', //处理上传文件的服务端
			secureuri: false,
			fileElementId: t1,
			dataType: 'json',
			success: function (data) {
				data = data.replace(/(^\s*)|(\s*$)/g, "");
				if (data == "1") {
					alert("上传图片大小不得超过50K！");
					$("#uniform-" + t1 + " span:first").html('无文件');
				} else if (data == "2") {
					alert("上传图片格式不正确！");
					$("#uniform-" + t1 + " span:first").html('无文件');
				} else {
					$("#" + t3 + "").append("<p><span onclick=\"delpic('" + flag + "','" + t3 + "','" + t2 + "',this,'" + data + "','" + t4 + "','" + t1 + "');\">x</span>" +
						"<img src=\"" + data + "?m=" + Math.random() + "\" /></p>");
					$("#" + t2 + "").hide(); //上传按钮
					$("#" + t4 + "").val(data);
				}
			},
			error: function () {
				alert("上传失败！");
			}
		});
	}
}

//对用户名作重复值判断
$("#m_logincode").blur(function(){
	var user = new Object();
	var id = $("#m_id").val();
	var loginCode = $("#m_logincode").val();
	user.id = id;
	user.loginCode = loginCode;

	if(loginCode != ''){
		$.post('/backend/logincodeisexist.html',{user:JSON.stringify(user)},function(data){

			if(data == 'repeat'){
				$("#modify_formtip").css('color','red');
				$("#modify_formtip").html('该用户名已存在,请重试。');

				$("#m_logincode").val('');
				$("#m_logincode").focus();
			}else if(data == 'only'){
				$("#modify_formtip").css('color','green');
				$("#modify_formtip").html('该用户名可以正常使用。');
			}else if(result=="failed"){
				alert("操作超时!");
			}
		},'html');
	}
});

//对邮箱格式作判断
function checkEmail(str){
	//fyz@yahoo.com.cn
	var reg = /^\w+@\w+(\.[A-Za-z]{2,3}){1,2}$/;
	if(str == null || str=='' || reg.test(str)){ //test()方法用于检测一个字符串是否匹配某个正则表达式
		return true;
	}else{
		return false;
	}
}
$("#m_email").blur(function(){
	var email = $("#m_email").val();
	var flag = checkEmail(email);
	if (flag == false){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html("邮箱格式不正确,请重试。")
	}else{
		$("#modify_formtip").html('');
	}
});

function modifyUserFunction(){
	$("#modify_formtip").html('');
	var result = true;
	if($("#m_roleId").val() == ''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('角色不能为空，请重试。');
		result = false;
	}

	if($("#m_logincode").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('用户名不能为空,请重试。');
		result = false;
	}
	if ($("#m_username").val() ==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('真实姓名不能为空，请重试。');
		result = false;
	}
	if($("#m_cardtype").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('证件类型不能为空，请重试。');
		result = false;
	}
	if($("#m_idcard").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('证件号码不能为空，请重试。');
		result = false;
	}else if ($("#m_idcard").val().length < 6){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('证件号码长度不能小于6位，请重试。');
		result = false;
	}
	if($("#m_mobile").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('手机号码不能为空，请重试。');
		result = false;
	}
	if($("#m_bankname").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('开户行不能为空，请重试。');
		result = false;
	}
	if($("#m_bankaccount").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('开户卡号不能为空，请重试。');
		result = false;
	}
	if($("#m_accountholder").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('开户人不能为空，请重试。');
		result = false;
	}
	if($("#m_isstart").val()==''){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").html('证件类型不能为空，请重试。');
		result = false;
	}

	if(result == true){
		alert("修改成功^_^");
	}
	return result;
}