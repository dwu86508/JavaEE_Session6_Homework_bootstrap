	function msgWindows(btnText){
			$("#msgShow").html('');
			$("#exampleModalLongTitle").html('');					  
			$('#exampleModalCenter').modal('show');
		if(btnText=="cancel"){
			$("#btnCf").html('取消');
			$("#btnCf").removeClass("btn-primary");
			$("#btnCf").addClass("btn-danger");
		}else{
			$("#btnCf").html('確定');
			$("#btnCf").removeClass("btn-danger");
			$("#btnCf").addClass("btn-primary");
		}
		
	}
	
	function addCartGoods(goodsID, buyQuantityIdx){
			console.log("goodsID:", goodsID);			
			var buyQuantity = document.getElementsByName("buyQuantity")[buyQuantityIdx].value;
			console.log("buyQuantity:", buyQuantity);
			var addShoppingParam = {
					goodsID : goodsID,
					buyQuantity : buyQuantity		
			};
			$.ajax({
				  url: 'ShoppingCarServlet.do?action=addCartGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET
				  data:addShoppingParam, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
				  dataType :'JSON', // Server回傳的資料類型
				  success: function(addRtn) {// 請求成功時執行函式
					msgWindows('confirm');					  
				  	if(addRtn[0].msgFrom=="error"){							  
				  		$("#exampleModalLongTitle").append("系統訊息");
				  		$("#msgShow").append(addRtn[0].msg);				  		
				  	}else if(addRtn[0].msgFrom=="addGood"){
				  		$("#shoppingCarBtn").text("購物車("+addRtn[0].msg.length+")");
				  		$("#exampleModalLongTitle").append("新增成功");
				  		$.each(addRtn[0].msg,function(i,goods){
				  			$("#msgShow").append("<p>"+
				  								goods.goodsName+"  "+goods.goodsPrice+" * "+goods.buyQuantity+" = "+goods.goodsPrice*goods.buyQuantity
				  								+"</p>");
				  		});
				  	}
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
					  	alert("Ajax Error!");
				  }
			});
		}
	//刪除購物車內商品
		function carRemove(goodsID){
			$.ajax({
				  url: 'ShoppingCarServlet.do?action=removeGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET	
				  data: {"goodsID":goodsID},
				  dataType :'JSON', // Server回傳的資料類型
				  
				  success: function(addRtn) {// 請求成功時執行函式				   	
				  	carShow(addRtn);
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
			});
		}
		//變更購物車內商品數量
		function goodsQuantity(goodsID,buyQuantity){
			$.ajax({
				  url: 'ShoppingCarServlet.do?action=updateCar', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET	
				  data: {"goodsID":goodsID,"buyQuantity":buyQuantity},
				  dataType :'JSON', // Server回傳的資料類型
				  
				  success: function(addRtn) {// 請求成功時執行函式				   	
				  	carShow(addRtn);
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
			});
		}
		//結帳視窗
		function checkoutWindows(){			
    		$.ajax({
				  url: 'ShoppingCarServlet.do?action=queryCartGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET				 
				  dataType :'JSON', // Server回傳的資料類型				  
				  success: function(addRtn) {// 請求成功時執行函式				   	
				  	if(addRtn.msgFrom=="error"){
						msgWindows('confirm');	
				  		$("#exampleModalLongTitle").append("系統訊息");
				  		$("#msgShow").append(addRtn.msg);
				  	}else if(addRtn.msgFrom=="shoppingCar"){
						msgWindows('cancel');	
						$("#exampleModalLongTitle").append("結帳");
						$("#msgShow").append("<p>請輸入金額</p>");
						$("#msgShow").append('<div class="form-row">'+
							'<div class="form-group col-md-9">'+				    				
								'<input class="form-control" type="number" id="inputMoney" placeholder="請輸入金額" aria-label="Search">'+
							'</div><div class="form-group col-md-3">'+	
			    				'<input class="btn btn-outline-success" type="button" value="購買" onclick="checkout()">'+
			    			'</div></div>');				  		
				  		$("#msgShow").append("<p>購物車明細</p>");				  		
				  		$.each(addRtn.msg.shoppingCartGoods,function(i,goods){
				  			$("#msgShow").append("<p>"+
				  									goods.goodsName+"  "+goods.goodsPrice+" * "+goods.buyQuantity+" = "+goods.goodsPrice*goods.buyQuantity
				  								+"</p>");
				  		});
				  		$("#msgShow").append("<p>總金額："+addRtn.msg.totalAmount+"</p>");			  			
				  	}	
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
			});					
		}
	
		//結帳
		function checkout(){					
			var input = document.getElementById("inputMoney").value;
			console.log("input:", input);	
			var inputMoney = {inputMoney:input}; 
			$.ajax({
				  url: 'FrontendServlet.do?action=buyGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET
				  data:inputMoney, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
				  dataType :'JSON', // Server回傳的資料類型
				  success: function(addRtn) { // 請求成功時執行函式					  
					  msgWindows('confirm');
				  	if(addRtn[0].msgFrom=="error"){	
				  		$("#exampleModalLongTitle").append("系統訊息");
				  		$("#msgShow").append(addRtn[0].msg);
				  	}else if(addRtn[0].msgFrom=="checkout"){
									  		
				  		$("#exampleModalLongTitle").append("消費明細");
				  		$("#msgShow").append("<p>投入金額："+addRtn[0].msg.inputMoney+"</p>");
				  		$("#msgShow").append("<p>購買金額："+addRtn[0].msg.buyTotal+"</p>");
				  		$("#msgShow").append("<p>找零金額："+addRtn[0].msg.orderChange+"</p>");
				  		if(addRtn[0].msg.buyYn){
							$("#checkoutClose").html("");
	 				  		$("#checkoutCf").html('<a class="btn btn-primary form-control" href="FrontendServlet.do?action=buyGoodsView">確定</a>');				  			
				  			$.each(addRtn[0].msg.buyGoodsRtn,function(i,goods){
	 				  			$("#msgShow").append("<p>"+
	 				  								goods.goodsName+"  "+goods.goodsPrice+" * "+goods.buyQuantity+" = "+goods.goodsPrice*goods.buyQuantity
	 				  								+"</p>");
	 				  		});
	 				  	
	 				  		checkoutRf();
				  		}else{
				  			$("#msgShow").append("<p style='color:blue;'>投入金額不足!!!</p>");
				  		}

				  	}
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
					  	alert("Ajax Error!");
				  }
			});
		}
		//顯示購物車內商品
		function carShow(addRtn){
			if(addRtn.msgFrom=="error"){	
		  		$("#collapseOne").html('<p align="center">'+addRtn.msg+'</p>');
		  		document.getElementById("checkout").style.display ="none";
		  	}else if(addRtn.msgFrom=="shoppingCar"){
		  		$("#collapseOne").html('<table class="table table-sm">'+
                        '<tr><th width="80"></th><th class="text-center">商品名稱</th><th class="text-center">價格</th><th class="text-center" width="80">數量</th><th class="text-center" width="120">小計</th><th class="text-center" width="80">刪除</th></tr>'+
                    	'<tbody id="carShow"></tbody>'+
                    	'</table>');				  		
		  		$.each(addRtn.msg.shoppingCartGoods,function(i,goods){
		  			var quantityminus = "";
		  			if(goods.buyQuantity-1>0){
		  				quantityminus = '<span class="material-symbols-sharp" onclick="goodsQuantity('+goods.goodsID+','+(goods.buyQuantity-1)+')">remove</span>'
		  			}
		  			$("#carShow").append('<tr>'+
		  				'<td class="align-middle"><div class="card p-1 card-bottom"><img src=DrinksImage/'+goods.goodsImageName+' width="80" height="80"></div></td>'+
		  				'<td class="align-middle">'+goods.goodsName+'</td>'+
		  				'<td class="align-middle text-right">$'+goods.goodsPrice+'</td>'+
		  				'<td class="align-middle text-center">'+quantityminus+goods.buyQuantity+
		  					'<span class="material-symbols-sharp" onclick="goodsQuantity('+goods.goodsID+','+(goods.buyQuantity+1)+')">add</span></td>'+
		  				'<td class="align-middle text-right">$'+goods.goodsPrice*goods.buyQuantity+'</td>'+
		  				'<td class="align-middle text-center"><span class="material-symbols-sharp" style="color:red;" onclick="carRemove('+goods.goodsID+')">delete</span></td>');
		  		});
		  		$("#carShow").append('<tr class="text-right">'+
                        '<td colspan="5"><strong>合計</strong></td>'+
                        '<td><strong>$'+addRtn.msg.totalAmount+'</strong></td>'+
                    	'</tr>');
		  		document.getElementById("checkout").style.display ="";
	  			
		  	}
		}
	$(document).ready(function() {		
		//清空購物車
		$("#cleanShoppingCar").bind("click",function (){			
			$.ajax({
				  url: 'ShoppingCarServlet.do?action=clearCartGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET				 
				  dataType :'JSON', // Server回傳的資料類型
				  success: function() { // 請求成功時執行函式
					  msgWindows('confirm');	
					  $("#shoppingCarBtn").html("購物車(0)");
					  $("#exampleModalLongTitle").append("系統訊息");
					  $("#msgShow").append("購物車已清空");					  
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
				});
		});
	});