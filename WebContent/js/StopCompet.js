$(document).ready(function(){
	$('#StopBtn').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'StopCompet'},
			url:'/SportID/ContestSrv',
			success: function(result){
				window.Contest = false;
				$('#FinalistTable').html(result);
			}
		});
	});
});