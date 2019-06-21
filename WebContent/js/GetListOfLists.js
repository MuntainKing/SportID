$(document).ready(function(){
//	$('#getLists').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'getLists'},
			url:'/SportID/ContestSrv',
			success: function(result){
				//alert(result);
				//console.log(result);
				list = JSON.parse(result);
				var listCount = list.count;

				var i;
				var x = document.getElementById("lists");
				for (i = 0; i < listCount; i++) { 	
					var option = document.createElement("option");
					option.text = list.data[i];
	
					x.add(option);
				}
			}
		});
//	});
});