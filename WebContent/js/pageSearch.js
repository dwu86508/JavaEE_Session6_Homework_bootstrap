$(document).ready(function() {
		var searchKeyword="";
		var pageNo="1";
		$("#search").bind("click",function (){
			searchKeyword=document.getElementById("searchKeyword").value;
			
			console.log(searchKeyword);
			var search = {
					searchKeyword : searchKeyword,
					pageNo:pageNo		
			};
			searchAjax(search);
		});
		var search = {
				searchKeyword : searchKeyword,
				pageNo:pageNo		
		};
		searchAjax(search);
		
	});
	function pageSearch(pageNo){
		searchKeyword=document.getElementById("searchKeyword").value;
		var search = {
				searchKeyword : searchKeyword,
				pageNo:pageNo
		};
		searchAjax(search);
	}
	function checkoutRf(){
		var searchKeyword="";
		var pageNo="1";
		var search = {
				searchKeyword : searchKeyword,
				pageNo:pageNo
		};
		searchAjax(search);
	}
	function searchAjax(search){
		$.ajax({
			url: 'FrontendServlet.do?action=searchGoods', // 指定要進行呼叫的位址
			type: "GET", // 請求方式 POST/GET
			data: search, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
			dataType :'JSON', // Server回傳的資料類型
			success: function(searchGoodsRtn) {// 請求成功時執行函式				  
				$("#goodsShow").html('');
				$("#pageShow").html('');
				$("#pageNo").val(searchGoodsRtn[0].page);
				$("#searchKeyword").val(searchGoodsRtn[0].searchKeyWord);					
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
				var show="";
				$.each(searchGoodsRtn[0].goodsList,function(i,goods){
					var divSt="";
					var divEn="";
					console.log((i+1)%3);
					if((i+1)%3==1){
						divSt='<br><div class="card-deck col-md-12 ">';
					}
					if((i+1)%3==0){
						divEn="</div>";
					}
					console.log(divSt);
					console.log(divEn);
					show=show+divSt+					
					'<div class="card col-md-4 my-lg-3">'+						
        				'<img border="0" class="card-img-top img-fluid" src="DrinksImage/'+goods.goodsImageName+'" width="150" height="150">'+
        				'<div class="card-body">'+
	        				'<h5 class="card-title">'+goods.goodsName+'</h5>'+
	        				'<h6 class="card-title">$'+goods.goodsPrice+'元</h5>'+	        				
	        					'<div class="form-row">'+        					
					    			'<div class="form-group col-md-4">'+
					        			'<input class="form-control" type="number" name="buyQuantity" min="0" max="30" size="5" value="0">'+
					     			'</div>'+			     			
							        '<div class="form-group col-md-3">'+
							        	'<button class="btn btn-primary" onclick="addCartGoods('+goods.goodsID+','+i+')">加入購物車</button>'+						        	
							        '</div>'+					        						        						        						        
				      			'</div>'+      
				      			'<h6 class="text-danger">庫存'+goods.goodsQuantity+'</h5>'+				      			 									        
				        '</div>'+        
				      '</div>'+divEn;	
										
				});	
				$("#goodsShow").html(show);
			},
			error: function(error) { // 請求發生錯誤時執行函式
			 	alert("Ajax Error!");
			}
		});
	}