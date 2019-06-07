	function CompRetriever() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				action : 'retrieveCompetitors'
			},
			success : function(data) {
				$('#registerTable').html(data);
			},
			complete : function() {
				setTimeout(CompRetriever, 500);
			}
		});
	}