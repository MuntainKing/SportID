$(document).ready(function(){
	$('#SaveBttn').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'savingAct'},
			url:'/SportID/NewServlet',
			success: function(result){
				$('#htmlLog').append(result);
			}
		});
	});
});