$(document).ready(function(){
	$('#saveList').click(function(){
		var name = $("#listname").val();
		$.ajax({
			type:'POST',
			data: {action: 'saveList',listname : name},
			url:'/SportID/NewServlet',
			success: function(result){
				$('#listLog').append(result);
			}
		});
	});
});