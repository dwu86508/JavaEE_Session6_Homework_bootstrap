$(document).ready(function(){
			$("#goodsID").bind("change",function(){
				
				var goodsID = $("#goodsID option:selected").val();
				
				var goodsIDParam = {goodsID : goodsID};

				if(goodsID != ""){
					$.ajax({
					  url: 'BackendServlet.do?action=getupdateGoods', // 指定要進行呼叫的位址
					  type: "GET", // 請求方式 POST/GET
					  data:goodsIDParam, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
					  dataType :'JSON', // Server回傳的資料類型
					  success: function(goodInfo) { // 請求成功時執行函式
					  	$("#goodsPrice").val(goodInfo.goodsPrice);
					  	$("#goodsQuantityShow").html(goodInfo.goodsQuantity);
					  	$("#status option[value='" + goodInfo.status + "']").attr("selected","selected"); 					  	
					  },
					  error: function(error) { // 請求發生錯誤時執行函式
					  	alert("Ajax Error!");
					  }
					});
				}else{
					$("#goodsPrice").val('');
				  	$("#goodsQuantityShow").html('');
				  	$("#status option[value='0']").attr("selected","selected"); 
				}
			});
		});
		
		function submitCheck(){
			var goodsID = $("#goodsID option:selected").val();
			if(goodsID==""){
				alert("請選擇商品");
			}else{
				var fromData = $('#updateGoods').serialize();
				$.ajax({
					  url: 'BackendServlet.do?action=updateGoods', // 指定要進行呼叫的位址
					  type: "GET", // 請求方式 POST/GET	
					  data: fromData,
					  dataType :'JSON', // Server回傳的資料類型
					  success: function(msg) { // 請求成功時執行函式
						  $("#msgShow").html(goodsID+msg.msg);
					  	  $("#goodsQuantityShow").html(msg.quantity);
					  	  $('#exampleModalCenter').modal('show');
					  },error: function(error) { // 請求發生錯誤時執行函式
						  	alert("Ajax Error!");
					  }
				});
			}
		}