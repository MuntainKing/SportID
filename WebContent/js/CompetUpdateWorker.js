	function CompetUpdateWorker() {
		$.ajax({
			type : 'POST',
			url : '/SportID/ContestSrv',
			data : {
				action : 'UpdateCompet'
			},
			success : function(result) {
				if (window.Contest){
					var inputStr = JSON.parse(result);
					$('#TimerContainer').html(inputStr.timer);
					$('#CompetTable').html(inputStr.data);
				}
			},
			complete : function() {

				setTimeout(CompetUpdateWorker, 160);
			}
		});
	}