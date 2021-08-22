//当套餐类型下拉框的值发生变化时
$("#a_typeId").change(function(){
	$("#a_typeName").val($("#a_typeId").find("option:selected").text());
});

function addGoods(gid,goodsname,rprice){
	var ok = true;
	$(".goodsname").each(function(){
		var title = $(this).html();
		if(goodsname == title){
			ok = false;
			return false;
		}
	});

	if(ok){
		str = "<div id=\"selectdiv\">" +
			"<label class=\"goodsname\">"+goodsname+"</label>" +
			"<label class=\"goodscount\"><input type=\"text\" class=\"finalresult\" goodsid=\""+gid+"\" rprice=\""+rprice+"\" value=\"1\"/></label>" +
			"<label class=\"del\" rprice=\""+rprice+"\"><img src=\"/statics/img/cancel-on.png\"></label>" +
			"<label class=\"clear\"></label>" +
			"</div>";
		$("#selectgoodslist").append(str);

		var gcount = $(".finalresult").val();
		var tprice = rprice * gcount;

		var tempprice = $("#a_totalPrice").val();
		if(tempprice == "" || tempprice == null){
			tempprice = 0;
		}
		tprice = parseInt(tempprice) + parseInt(tprice);

		//每添加一件商品向总金额中写入总价钱
		$("#a_totalPrice").val(tprice);
	}

}

//删除套餐中的商品
$(".del").click(function(){
	$(this).parent("#selectdiv").remove();
	$("#selectgoodslist").change();
});

//
$("#selectgoodslist").change(function(){
	var tempprice = 0;
	$(".finalresult").each(function (){
		var gcount = $(this).val();
		var rprice = $(this).attr("rprice");
		var tprice = gcount * rprice;
		tempprice = parseInt(tempprice) + parseInt(tprice);
	});
	$("#a_totalPrice").val(tempprice);
});

function addGoodsPackFunc(){

}