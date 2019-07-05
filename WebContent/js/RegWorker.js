function RegWorker() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				action : 'SensListCheck'
			},
			success : function(result) {
				//$('#senslistcont').html(result);
				var inputStr = JSON.parse(result);
				var numbInp = document.getElementById("number");
				var numbVal = numbInp.value;
				//if (numbVal == ""){
					if (inputStr.number != -1) {
						numbInp.value=inputStr.number;
						var numbDiv = document.getElementById("numbDiv");
						numbDiv.classList.add("is-filled");}
				//}
				
				$('#senslistcont').html(inputStr.table);
			},
			complete : function() {
				setTimeout(RegWorker, 500);
			}
		});
	}