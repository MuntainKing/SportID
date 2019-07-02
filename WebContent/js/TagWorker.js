function TagWorker() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {action : 'epcCheckAct'},
			success : function(result) {
				$('#ajaxresponse').html(result);
			},
			complete : function() {
				setTimeout(TagWorker, 500);
			}
		});
	}