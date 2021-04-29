//
$("#loginBtn").click(function(){
	var user = new Object();

	var loginCode = $.trim($("#logincode").val());
	var password = $.trim($("#password").val());

	user.loginCode = loginCode;
	user.password = password;

	if(user.loginCode == '' || user.loginCode == null){
		$("#logincode").focus(); //获得光标
		$("#formtip").css("color","red");
		$("#formtip").html("对不起,登录账号不能为空。");
	}else if (user.password == '' || user.password == null){
		$("#password").focus();
		$("#formtip").css("color","red");
		$("#formtip").html("对不起,登录密码不能为空。");
	}else{
		$("#formtip").html('');
		$.ajax({
			url:'/login.html',
			type:'post',
			data:{user:JSON.stringify(user)}, //JSON.stringify()方法将JavaScript的值转换成JSON格式的字符串
			dataType:'html',
			error:function(){
				$("#formtip").css("color","red");
				$("#formtip").html("登录失败，请重试。");
			},
			success:function(data){  //返回的json格式的数据流
				if(data == "success"){
					window.location.href="/main.html";
				}else if (data == "nodata"){

					$("#formtip").css("color","red");
					$("#formtip").html("没有任何数据需要处理！请重试。");

				}else if(data == "nologincode"){

					$("#formtip").css("color","red");
					$("#formtip").html("登录账号不存在，请重新输入。");
					$("#logincode").val('');
					$("#password").val('');

				}else if (data == "pwderror"){

					$("#formtip").css("color","red");
					$("#formtip").html("密码错误,请重新输入。");
					$("#logincode").val('');
					$("#password").val('');

				}else if(data=="failed"){

					$("#formtip").css("color","red");
					$("#formtip").html("登录失败,请重试。");
					$("#logincode").val('');
					$("#password").val('');
				}
			}
		});
	}
});
