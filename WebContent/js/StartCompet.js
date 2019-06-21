$(document).ready(function(){
	$('#StartBtn').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'StartCompet'},
			url:'/SportID/ContestSrv',
			success: function(result){
				window.Contest = true;
				$('#htmlLog').append(result);
			}
		});
	});
});