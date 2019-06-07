function RegWorker() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				action : 'SensListCheck'
			},
			success : function(result) {
				$('#senslistcont').html(result);
			},
			complete : function() {
				setTimeout(RegWorker, 500);
			}
		});
	}