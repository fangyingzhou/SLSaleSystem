//修改当前套餐信息
$(".modifygoodspack").click(function(){
	var id = $(this).attr("id");
	window.location.href="/backend/modifygoodspack.html?id="+id;
});

//修改套餐的状态
$(".modifystate").click(function(){
	var modify = $(this);
	var state = modify.attr("state");
	var id = modify.attr("goodspackid");
	var goodsPack = new Object();
	goodsPack.id = id;

	if(state == 1){
		goodsPack.state = 2;
	}else{
		goodsPack.state = 1;
	}

	$.ajax({
		url:'/backend/modifystate.html',
		type:'post',
		data:{"jsonState":JSON.stringify(goodsPack)},
		dataType:'html',
		timeout:1000,
		error:function(){
			alert("修改失败!")
		},
		success:function(data){
			alert(data);
			if(data != "" && data =="success"){
				if(state == 1){
					modify.attr("state",2);
				}else{
					modify.attr("state",1);
				}
			}else if(data =="nodata"){
				alert("对不起，没有任何数据需要操作!请重试。");
			}else if(data == "failed"){
				alert("修改套餐状态失败!");
			}
		}
	});
});

$(".addGoodsPack").click(function(e){
	window.location.href="/backend/addgoodspack.html";
});