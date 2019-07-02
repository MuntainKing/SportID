$(document).ready(function(){
	$('#SaveBtn').click(function(){
		var name = $("#ContestName").val();
		$.ajax({
			type:'POST',
			data: {action: 'saveResult',ResultTitle : name},
			url:'/SportID/ContestSrv',
			success: function(result){		
				var ListNameInput = document.getElementById("ContestName");
				var CurListName = ListNameInput.value;
				if (CurListName) {
				$('#DownloadRes').attr({target: '_blank', href  : '../../files/results/'+CurListName+'.csv'});
				}
			}
		});
	});
});