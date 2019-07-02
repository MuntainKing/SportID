$(document).ready(function(){
	$.ajax({
		type:'POST',
		data: {action: 'CompetRestore'},
			url:'/SportID/ContestSrv',
			success: function(result){
				//alert(result);
				window.Contest = result;
			}
	});

});