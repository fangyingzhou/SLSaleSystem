//rich text editor
$('#a_goodsFormat').cleditor();
$('#a_note').cleditor();

$(".modifygoodsinfo").click(function(e){
	var id = $(this).attr("id");
	$.ajax({
		url:'/backend/getgoodsinfo.html',
		type:'post',
		data:{"m_id":id},
		dataType:'html',
		timeout:1000,
		error:function (){
			alert("error");
		},
		success:function(data){
			if(data != ""){
				var m = eval("("+data+")");
				$("#m_id").val(m.id)
				$("#m_goodsName").val(m.goodsName);
				$("#m_goodsSN").val(m.goodsSN);
				$("#m_marketPrice").val(m.marketPrice);
				$("#m_realPrice").val(m.realPrice);
				$("#m_num").val(m.num);
				$("#m_unit").val(m.unit);

				$("#m_state").html('');
				var state = m.state;

				if(state == 1){
					$("#m_state").append("<span>状态：</span><input type=\"radio\" checked=\"checked\" value='1'/>上架<input type='radio' value='2'/>下架");
				}else if(state == 2){
					$("#m_state").append("<span>状态：</span><input type='radio' value='1'/>上架<input type='radio' value='2' checked='checked'/>下架")
				}
				$("#m_goodsFormatli").html('');
				$("#m_goodsFormatli").append("<span>商品规格：</span><textarea id=\"m_goodsFormat\" name=\"goodsFormat\" rows='3'>"+m.goodsFormat+"</textarea>");
				$("#m_goodsFormatli").cleditor();

				$("#m_noteli").html('');
				$("#m_noteli").append("<span>商品说明：</span><texarea id=\"m_noe\" name=\"note\" rows=\"3\">"+m.note+"</texarea>");
				$("#m_noteli").cleditor();

				e.preventDefault();
				$("#modifyGoodsInfoDiv").modal("show");
			}

		}
	});
});

//判断商品编号SN是否已经存在
$("#m_goodsSN").blur(function(){

	var goodsSN = $("#m_goodsSN").val();
	var id = $("#m_id").val();

	if(goodsSN != ""){
		$.ajax({
			url:'/backend/goodssnisexist.html',
			type:'post',
			data:{"goodsSN":goodsSN,"id":id},
			dataType:'html',
			timeout:1000,
			error:function(){
				alert("error");
			},
			success:function(data){
				if(data == "failed"){
					alert("服务器端错误!");
				}else if(data == "rename"){
					$("#m_goodsSN").focus();
					$("#modify_formtip").css("color","red");
					$("#modify_formtip").html("该商品编号已经存在，请重试。")
				}
			}
		});
	}

});
function modifyGoodsInfoFunction(){
	var result = true;
	$("#modify_formtip").html("");

	if($.trim($("#m_goodsName").val()) == "" || $("#m_goodsName").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，商品名称不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_goodsSN").val()) == "" || $("#m_goodsSN").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，商品编号不能为空。</li>");
		result = false;
	}

	if($.trim($("#m_marketPrice").val()) == "" || $("#m_marketPrice").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，市场价不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_realPrice").val()) == "" || $("#m_realPrice").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，优惠价不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_num").val()) == "" || $("#m_num").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，库存量不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_unit").val()) == "" || $("#m_unit").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，单位不能为空。</li>");
		result = false;
	}

	return result;
}

//第一次请求的时候 state的值为数据库中的值
$(".modifystate").click(function(){
	var modify = $(this);
	var goodsInfo = new Object();

	state = $(this).attr("state");
	goodsInfo.id = $(this).attr("goodsinfoid");

	if(state == 1){  //复选款为选中状态
		goodsInfo.state = 2;

	}else{
		goodsInfo.state = 1;  //传递给数据库 更新state字段值 把数据库的值更新为 1
		//modify.attr("state","1"); //没有把改变后的属性值赋值给 state 导致state的属性值还是原始值
	}

	$.ajax({
		url:'/backend/modifystate.html',
		type:'post',
		data:{"goodsInfo":JSON.stringify(goodsInfo)},
		dataType:'html',
		timeout:1000,
		error:function(){
			alert("");
		},
		success:function (data){
			if(data != "" && data =="success"){
				if(state == 1){
					modify.attr("state","2"); //更新state的属性的值
				}else{
					modify.attr("state","1"); //更新state的属性值 否则state的值仍为原始值
				}
			}else if("failed" == result){
				alert("上架或下架商品操作时失败！请重试。");
			}else if("nodata" == result){
				alert("对不起，没有任何数据需要处理！请重试。");
			}
		}

	});
});
//提示函数
function showmsg(msg){
	var divBox = document.getElementById("showmsgBox") || "";
	if(divBox){
		divBox.style.display = "block";
	}else{
		divBox = document.createElement("span");
		divBox.className = "showmsgBox"; 
		divBox.setAttribute("id","showmsgBox");  
		document.body.appendChild(divBox);
	}
	divBox.innerHTML = msg; 
	divBox.style.left = document.documentElement.clientWidth/2 + "px"
	setTimeout(function(){ 
		 divBox.style.display = "none";
	},1000);
}

