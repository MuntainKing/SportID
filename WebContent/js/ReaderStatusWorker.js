	function ReaderStatusWorker() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				action : 'readStatus'
			},
			success : function(data) {
				$("#RI").attr('src', data);
			},
			complete : function() {
				setTimeout(ReaderStatusWorker, 500);
			}
		});
	}