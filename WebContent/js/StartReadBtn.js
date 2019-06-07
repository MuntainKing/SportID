$(document).ready(function(){
	$('#ReadBttn').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'startRead'},
			url:'/SportID/NewServlet',
			success: function(result){
				$('#htmlLog').append(result);
			}
		});
	});
});