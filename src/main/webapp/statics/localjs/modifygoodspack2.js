//当在商品列表中选择商品 向套餐中添加的时候 需要重新计算该套餐的总价钱
function addGoods(gid,goodsname,rprice){
    alert(gid+"-"+goodsname+"-"+rprice);
    var ok = true;
    $(".goodsname").each(function(){
        title = $(this).html();
        if(title == goodsname ){ //如果当前遍历的元素值 等于 传递过来的值 则说明该商品已存在于该套餐下 退出循环
            ok = false
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

        var tempprice = $("#m_totalPrice").val();
        if(tempprice == "" || tempprice == null){
            tempprice = 0;
        }
        tprice = parseInt(tprice) + parseInt(tempprice);
        $("#m_totalPrice").val(tprice);
        $(".del").click(function(e){
            $(this).parents("#selectdiv").remove();
            $("#selectgoodslist").change();
        })
    }

}

$(".del").click(function(){
    $(this).parent("#selectdiv").remove();
    $("#selectgoodslist").change();
});

$("#selectgoodslist").change(function(){
   var totalPrice = 0;
   $(".finalresult").each(function(){
       id = $(this).attr("goodsid");
       rprice = $(this).attr("rprice");
       gcount = $(this).attr("gcount");
       totalPrice = parseInt(totalPrice) + parseInt(rprice*gcount);
   });
    $("#m_totalPrice").val(totalPrice);
});

//页面传递过去商品ID+商品的数量
function modifyGoodsPackFunc(){
    $("#modify_formtip").html('');
    var flag = true;
    var json = "[";
    //循环遍历该套餐下的所有商品 注意需要传递商品id goodsInfoId 以及 套餐下商品的数量 goodsNum
    $(".finalresult").each(function(){
        var goodsid = $(this).attr("goodsid");
        var gcount = $(this).val();
        json = json+"{\"goodsInfoId\":\""+goodsid+"\",\"goodsNum\":\""+gcount+"\"},";
    });
    json = json+"{\"goodsInfoId\":\"0\",\"goodsNum\":\"0\"}";
    json = json+"]";

    $("#goodsJson").val(json);

    if( $.trim($("#m_goodsPackName").val()) == "" || $("#m_goodsPackName").val() == null){
        $("#modify_formtip").css("color","red");
        $("#modify_formtip").append("<li>对不起，套餐名称不能为空。</li>");
        flag = false;
    }
    if( $.trim($("#m_goodsPackCode").val()) == "" || $("#m_goodsPackCode").val() == null){
        $("#modify_formtip").css("color","red");
        $("#modify_formtip").append("<li>对不起，套餐编码不能为空。</li>");
        flag = false;
    }

    if($("#m_typeId").val() == ""){
        $("#modify_formtip").css("color","red");
        $("#modify_formtip").append("<li>对不起，套餐类型不能为空。</li>");
        flag = false;
    }
    if( $.trim($("#m_num").val()) == "" || $("#m_num").val() == null){
        $("#modify_formtip").css("color","red");
        $("#modify_formtip").append("<li>对不起，套餐库存量不能为空。</li>");
        flag = false;
    }
    if( $.trim($("#m_totalPrice").val()) == "" || $("#m_totalPrice").val() == null){
        $("#modify_formtip").css("color","red");
        $("#modify_formtip").append("<li>对不起，套餐总价不能为空。</li>");
        flag = false;
    }
    return flag;
}