$(document).ready(function(){
	$('#saveList').click(function(){
		var name = $("#listname").val();
		$.ajax({
			type:'POST',
			data: {action: 'saveList',listname : name},
			url:'/SportID/NewServlet',
			success: function(result){
				var ListNameInput = document.getElementById("listname");
				var CurListName = ListNameInput.value;
				$('#fileRequest').attr({target: '_blank', href  : '../../files/reglist/'+CurListName+'.csv'});
				$('#listLog').append(result);
			}
		});
	});
});