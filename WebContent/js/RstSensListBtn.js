$(document).ready(function(){
	$('#RstBttn').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'resetTags'},
			url:'/SportID/NewServlet',
			success: function(result){
				$('#htmlLog').append(result);
			}
		});
	});
});