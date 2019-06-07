function TagWorker() {
		var updcmd = "check";
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				fullname : updcmd,
				action : 'epcCheckAct'
			},
			success : function(result) {
				$('#ajaxresponse').html(result);
			},
			complete : function() {
				setTimeout(TagWorker, 500);
			}
		});
	}