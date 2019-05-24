	function retriever() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				action : 'retrieveCompetitors'
			},
			success : function(result) {
				$('#registerTable').html(result);
			},
			complete : function() {
				// Schedule the next request when the current one's complete
				setTimeout(retriever, 1000);
			}
		});
	}