	function CompetUpdateWorker() {
		$.ajax({
			type : 'POST',
			url : '/SportID/ContestSrv',
			data : {
				action : 'UpdateCompet'
			},
			success : function(result) {
				if (window.Contest)
				$('#CompetTable').html(result);
			},
			complete : function() {

				setTimeout(CompetUpdateWorker, 100);
			}
		});
	}