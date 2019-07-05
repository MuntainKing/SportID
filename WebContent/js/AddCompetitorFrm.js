$(document).ready(function(){
	$('#regForm').submit(function(event){
		event.preventDefault();
		var name = $("#fname").val();
		var surname = $("#surname").val();
		var Pname = $("#Pname").val();
		var number = $("#number").val();
		var byear = $("#byear").val();
		var radioValue = $("input[name='gender']:checked").val();
		var EditCheckBox = document.getElementById("edit");
		if (EditCheckBox.checked == true){
			var edit = true;
		} else {
			var edit = false;
		}
		try{
		var targetEPC = document.getElementById("newTag").innerHTML;
		$.ajax({
			type:'POST',
			data: {action: 'saveCompetitor',
			name:name,
			surname:surname,
			Pname:Pname,
			number:number,
			byear:byear,
			radioValue:radioValue,
			targetEPC:targetEPC,
			edit:edit},
			url:'/SportID/NewServlet',
			success: function(result){
				$('#form-msg').html(result);
			}
		});
		} catch(err){
			$('#form-msg').html("<span class=\"form-error\"> Поднесите метку </span>");
		}
		
		//if(typeof(document.getElementById("targetEPC").innerHTML) != 'undefined' && targetEPC != null){

		//} else{
			
		//}
		
		
	});
});