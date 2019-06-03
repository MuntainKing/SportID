$(document).ready(function(){
	$('#getLists').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'getLists'},
			url:'/SportID/ContestSrv',
			success: function(result){
				console.log(result);
				list = JSON.parse(result);
				console.log("JSON");
				console.log(list);
				var listCount = list.count;
				console.log(listCount);
				var i;
				var x = document.getElementById("lists");
				for (i = 0; i < listCount; i++) { 	
					var option = document.createElement("option");
					option.text = list.data[i];
					console.log(list.data[i]);
					x.add(option);
				}
			}
		});
	});
});