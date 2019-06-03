	function retriever() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				action : 'retrieveCompetitors'
			},
			success : function(data) {
				var parsed_data = JSON.parse(data);
				$("#RI").attr('src', parsed_data.sus);
				$('#registerTable').html(parsed_data.competitors);
			},
			complete : function() {
				// Schedule the next request when the current one's complete
				setTimeout(retriever, 1000);
			}
		});
	}