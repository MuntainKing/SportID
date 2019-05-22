$(document).ready(function(){
	$('#SaveBttn').click(function(){
		$.ajax({
			type:'POST',
			data: {saving: 'true', action: 'savingAct'},
			url:'/SportID/NewServlet',
			success: function(result){
				$('#htmlLog').append(result);
			}
		});
	});
});