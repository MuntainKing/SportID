$(document).ready(function(){
	$('#NewCompetBtn').click(function(){
		$.ajax({
			type:'POST',
			data: {action: 'NewCompet'},
			url:'/SportID/ContestSrv',
			success: function(result){
				$('#FinalistTable').html('');
				$('#CompetTable').html(result);
			}
		});
	});
});