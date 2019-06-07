$(document).ready(function(){
	$('#CreateTableBtn').click(function(){
		var listSel = document.getElementById("lists");
		var listName = listSel.options[listSel.selectedIndex].text;
		var laps = document.getElementById("lists");
		var lapsCount = laps.optiond[laps.selectedIndex].value;
		$.ajax({
			type:'POST',
			data: {action: 'createTable',
			listName : listName,
			lapsCount : lapsCount},
			url:'/SportID/ContestSrv',
			success: function(result){
				//alert(result);
				$('#CompetTable').html(result);
			}
		});
	});
});