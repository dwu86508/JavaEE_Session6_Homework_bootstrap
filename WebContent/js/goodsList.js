function pageSearch(page){
			$("#page").val(page);
			goodsSearch();
		}
		$(document).ready(function(){
			goodsSearch();		
		});
		
		function goodsSearch(){
			var fromData = $('#goodsSearchFrom').serialize();
			$.ajax({
				  url: 'BackendServlet.do?action=queryGoodsSearch', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET	
				  data: fromData,
				  dataType :'JSON', // Server回傳的資料類型
				  success: function(searchGoodsRtn) { // 請求成功時執行函式
				  	  $("#pageShow").html("");
				  	  $("#goodsShowHeader").html("");
				  	  $("#goodsShow").html("");
					  $("#nogoodsShow").html("");
					  if(searchGoodsRtn[0].goodsList.length>0){						
						$("#goodsShowHeader").append('<tr align="center">'+
					  			'<th scope="col"><b>商品編號</b></td>'+
								'<th scope="col"><b>商品名稱</b></td>'+ 
								'<th scope="col"><b>商品價格</b></td>'+
								'<th scope="col"><b>現有庫存</b></td>'+
								'<th scope="col"><b>商品狀態</b></td>'+				
								'</tr>');
						$.each(searchGoodsRtn[0].goodsList,function(i,goods){
							 
							var status="下架";
							if(goods.status=="1"){
								status="上架";
							}
							$("#goodsShow").append('<tr>'+
								'<td align="center">'+goods.goodsID+'</td>'+
								'<td>'+goods.goodsName+'</td>'+
								'<td align="center">'+goods.goodsPrice+'</td>'+
								'<td align="center">'+goods.goodsQuantity+'</td>'+
								'<td align="center">'+status+'</td>'+
								'</tr>');
						});					
						if(searchGoodsRtn[0].page!=1){
							$("#pageShow").append('<li class="page-item"><a href="#" class="page-link" onclick="pageSearch('+(searchGoodsRtn[0].page-1)+')" >上一頁</a></li>');
						}
						for(i=3 ; i>0 ; i--){
							if((searchGoodsRtn[0].pagePage*3-(i-1)) <= searchGoodsRtn[0].lastPage){
								if((searchGoodsRtn[0].pagePage*3-(i-1)) == searchGoodsRtn[0].page){
									$("#pageShow").append('<li class="page-item active"><a href="#" class="page-link">'+searchGoodsRtn[0].page+'</a></li>');									
								}else{
									$("#pageShow").append('<li class="page-item"><a href="#" class="page-link"'+ 
										'onclick="pageSearch('+(searchGoodsRtn[0].pagePage*3-(i-1))+')" >'+(searchGoodsRtn[0].pagePage*3-(i-1))+'</a></li>');									
								}
							}
						}
						if(searchGoodsRtn[0].page+1 <= searchGoodsRtn[0].lastPage){
							$("#pageShow").append('<li class="page-item"><a href="#" class="page-link" onclick="pageSearch('+(searchGoodsRtn[0].page+1)+')" >下一頁</a></li>');
						}
						
					  }else{
						  $("#nogoodsShow").html("查無商品!!!!");
					  }			  
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
			});
		}