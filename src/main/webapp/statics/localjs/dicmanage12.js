
var maxValue = 0;
$(".typecodelist").click(function(){
	var tag = $(this);
	var typeName = tag.attr("typename");
	var typeCode = tag.attr("typecode");
	$("#typeDicSubCode").val(typeCode);
	$("#typeDicSubName").val(typeName);
	$("#valueDicSubName").val('');
	$("#addDicSubtip").html('');
	$("#optitle").html("当前操作："+typeCode+"-"+typeName);

	$.ajax({
		url:'/backend/getJsonDic.html',
		type:'post',
		data:{'typeCode':typeCode},
		dataType:'html',
		error:function(){

		},
		success:function (result){

			if(result == "failed"){
				alert("查询"+typeName+"失败!");
			}else if(result == "nodata"){
				alert("查询"+typeName+"失败!")
			}else{
				var data = eval("("+result+")"); //将字符串转换成json对象
				str = "";
				dicListUL = $("#dicListUL");
				dicListUL.html("");
				for(var i=0;i<data.length;i++){
					str += "<li id=\"li"+data[i].id+"\">";
					str += "<div>类型代码:"+data[i].typeCode+"</div>";
					str += "<div>类型名称:"+data[i].typeName+"</div>";

					str += "<div>数据数值:<input type=\"text\" disabled=\"disabled\" onkeyup=\"this.value=this.value.replace(\/\\D\/g,'')\" onafterpaste=\"this.value=this.value.replace(\/\\D\/g,'')\" id=\"valueIdText"+data[i].id+"\" value=\""+data[i].valueId+"\"/></div>";
					str += "<div>数值名称:<input type=\"text\" id=\"valueNameText"+data[i].id+"\" value=\""+data[i].valueName+"\"/></div>";
					str += "<div class=\"editdiv\">";
					str += "<img class=\"modifyDicValue\" id=\""+data[i].id+"\" modifytypecode="+data[i].typeCode+" modifytypename="+data[i].typeName+" valueid=\""+data[i].valueId+"\" valuename=\""+data[i].valueName+"\" src=\"/statics/img/ico10.png\"> <img class=\"delDicValue\" id=\""+data[i].id+"\" deltypename="+data[i].typeName+" valueid=\""+data[i].valueId+"\" valuename=\""+data[i].valueName+"\"  src=\"/statics/img/linkdel.png\">";
					str += "</div>";
					str += "<div id=\"dicTip"+data[i].id+"\"></div>";
					str += "</li>";

					//该类型下最后一个证件类型
					maxValue = data[i].valueId;
				}

				dicListUL.append(str);
				$("#addsubdicul").show();

				$(".modifyDicValue").click(function(){
					var modify = $(this);
					var dic = new Object();
					dic.id = modify.attr("id");
					dic.typeCode = modify.attr("modifytypecode");
					dic.typeName = modify.attr("modifytypename");
					dic.valueId = modify.attr("valueid");
					dic.valueName = modify.attr("valuename");

					if(confirm("您确定要修改【"+dic.typeName +"】 - 【"+dic.typeCode+"】- 【"+dic.valueName+"】的数据配置吗?")){
						dic.valueName= $.trim($("#valueNameText"+dic.id).val()); // 获取当前对象的valueName
						 if(dic.valueName == ""){
							$("#valueNameText"+dic.id).focus();
							$("#dicTip"+dic.id).css("color","red");
							$("#dicTip"+dic.id).html("数据名称不能为空!");
						}else{
							$.ajax({
								url:'/backend/modifydic.html',
								type:'post',
								data:{dicJson:JSON.stringify(dic)},
								dataType:'html',
								timemout:1000,
								error:function(){
									$("#dicTip"+dic.id).css("color","red");
									$("#dicTip"+dic.id).html('修改数据字典失败!请重试。');
								},
								success:function(data){
									if(data == "success"){
										$("#dicTip"+dic.id).css("color","green");
										$("#dicTip"+dic.id).html("恭喜您，修改成功。^_^");
									}else if(data =="nodata"){
										$("#dicTip"+dic.id).css("color","red");
										$("#dicTip"+dic.id).html('对不起,没有任何数据需要处理，请重试。');
									}else if(data=="rename"){
										$("#dicTip"+dic.id).css("color","red");
										$("#dicTip"+dic.id).html('修改失败!该类型代码下的数据名称不能重复,请重试。');
									}else if(data == "failed"){
										$("#dicTip"+dic.id).css("color","red");
										$("#dicTip"+dic.id).html('数据字典修改失败，请重试');
									}
								}
							});
						}
					}
				});

				$(".delDicValue").click(function(){
					var del = $(this);
					var dic = new Object();
					dic.id = del.attr("id");
					dic.typeName = del.attr("deltypename");
					dic.valueId = del.attr("valueid");
					dic.valueName = del.attr("valuename");
					if(confirm("你确定要删除【"+dic.typeName+"】- 【"+dic.valueName+"】的数据吗？")){
						$.ajax({
							url:'/backend/deldic.html',
							type:'post',
							data:{id:dic.id},
							dataType:'html',
							timeout:1000,
							error:function (){
								alert("删除失败,请重试!");
							},
							success:function (data){

							}
						});
					}
				});
			}
		}
	});
});

$("#addDicLiBtn").click(function(e){
	e.preventDefault();
	$("#addDicSubModel").modal("show");
});

$("#addDicsubExeBtn").click(function(){ //ddDicsubExeBtn
	var dic = new Object();
	dic.typeCode = $.trim($("#typeDicSubCode").val()); //typeDicSubCode
	dic.typeName = $.trim($("#typeDicSubName").val()); //typeDicSubName
	dic.valueName = $.trim($("#valueDicSubName").val()); //valueDicSubName


	if(dic.typeCode == null){
		$("#addDicSubtip").css("color","red");
		$("#addDicSubtip").html("类型代码不能为空。请重试。");
	}else if (dic.typeName == null){
		$("#addDicSubtip").css("color","red");
		$("#addDicSubtip").html("类型名称不能为空。请重试。");
	}else if (dic.valueName == null) {
		$("#valueDicSubName").focus();
		$("#addDicSubtip").css("color", "red");
		$("#addDicSubtip").html("类型名称不能为空。请重试。");
	}else{
		$.ajax({
			url:'/backend/addSubDic.html',
			type:'post',
			data:{"jsonDic":JSON.stringify(dic)},
			dataType:'html',
			timeout:1000,
			error:function(){
				alert("添加数据字典失败，请重试。")
			},
			success:function(data){
				if(data != "" && data =="success"){
					var str = "";
					str += "<li id=\"li999\">";
					str += "<div>类型代码："+dic.typeCode+"</div>";
					str += "<div>类型名称："+dic.typeName+"</div>";
					str += "<div>数据数值：<input type=\"text\" disabled=\"disabled\" value=\""+(maxValue+1)+"\"/></div>";
					str += "<div>数据名称：<input type=\"text\" id=\"valueNameText"+dic.id+"\" value=\""+dic.valueName+"\"/></div>";
					str += "<div class=\"editdiv\">";
					str += "</div>";
					str += "<div id=\"dicTip"+dic.id+"\"></div>";
					str += "</li>";

					$("#dicListUL").append(str);
					$("#addDicSubtip").css("color","green");
					$("#addDicSubtip").html("数据字典添加成功。^_^");
				}else if(data =="rename"){
					$("#addDicSubtip").css("color","red");
					$("#addDicSubtip").html("数据字典添加失败,该类型代码下的数据名称不能重复，请重试。");
				}else if(data =="nodata"){
					$("#addDicSubtip").css("color","red");
					$("#addDicSubtip").html("对不起，没有任何数据需要处理，请重试。");
				}else if(data =="failed"){
					$("#addDicSubtip").css("color","red");
					$("#addDicSubtip").html("数据字典添加失败");
				}
			}
		});
	}
});


$(".maintitle").mouseenter(function(){
	$(this).children(".mainset").show();
});

$(".maintitle").mouseleave(function(){
	$(this).children(".mainset").hide();
});

//修改主数据字典列表
$(".modifyMainDic").click(function(){

	var typeCode = $(this).attr("dictypecode");
	var typeName = $(this).attr("dictypename");
	var dicId = $(this).attr("dicid");

	$("#modifytypeCode").val(typeCode); //修改后的类型代码
	$("#modifytypeName").val(typeName); //修改后的类型名称

	$("#modifydicid").val(dicId);
	$("#modifydictypecode").val(typeCode); //修改前的 类型代码
	$("#modifydictypename").val(typeName); //修改前的 类型名称

	$("#modifyDicModel").modal("show");
});

//
$("#modifytypeCode").blur(function(){
	 var dic = new Object();
	 dic.typeCode = $(this).val();
	 dic.id = $("#modifydicid").val();

	 $.post('/backend/typecodeisexist.html',{"jsonDic":JSON.stringify(dic)},function(data){
	 	if(data !="" && data== "only"){
			$("#modifyDictip").css("color","green");
			$("#modifyDictip").html("该类型代码可以正常使用。");
		}else if(data == "rename"){
	 		$("#modifytypeCode").focus();
	 		$("#modifyDictip").css("color","red");
	 		$("#modifyDictip").html("该类型代码已经存在，请重试。");
		}else if(data == "nodata"){
			$("#modifyDictip").css("color","red");
			$("#modifyDictip").html("没有任何数据需要操作，请重试。");
		}else if(data =="failed"){
			$("#modifyDictip").css("color","red");
			$("#modifyDictip").html("修改失败，请重试。");
		}
	 },'html');
});

//根据
$(".modifyDicExeBtn").click(function(){
	var newDic = new Object();
	newDic.typeCode = $.trim($("#modifytypeCode").val());
	newDic.typeName = $.trim($("#modifytypeName").val());
	newDic.id = $.trim($("#modifydicid").val());

	var oldDic = new Object();
	oldDic.typeCode = $.trim($("#modifydictypecode").val());
	oldDic.typeName = $.trim($("#modifydictypename").val());
	oldDic.id = $.trim($("#modifydicid").val());

	if(newDic.typeCode == null){
		$("#modifytypeCode").focus();
		$("#modifyDictip").css("color","red");
		$("#modifyDictip").html("类型代码不能为空。请重试");
	}else if(newDic.typeName == null){
		$("#modifytypeCode").focus();
		$("#modifyDictip").css("color","red");
		$("#modifyDictip").html("类型名称不能为空。请重试");
	}else{
		$.ajax({
			url:'/backend/modifymaindic.html',
			type:'post',
			data:{"oldDic":JSON.stringify(oldDic),"newDic":JSON.stringify(newDic)},
			dataType:'html',
			timeout:1000,
			error:function(){

			},
			success:function (data){
				if(data != null && data=="success"){
					window.location.href="/backend/dicmanage.html";
				}else if(data == "failed"){
					$("#modifyDictip").css("color","red");
					$("#modifyDictip").html("数据字典修改失败,请重试。");
				}else if(data == "nodata"){
					$("#modifyDictip").css("color","red");
					$("#modifyDictip").html("对不起，没有任何数据需要处理,请重试。");
				}else if(data == "rename"){
					$("#modifyDictip").css("color","red");
					$("#modifyDictip").html("数据类型已存在,请重试。");
				}

			}
		});
	}

});