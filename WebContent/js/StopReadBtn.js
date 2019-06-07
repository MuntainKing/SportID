$(document).ready(function(){
	$('#StopBttn').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'stopRead'},
			url:'/SportID/NewServlet',
			success: function(result){
				$('#htmlLog').append(result);
			}
		});
	});
});