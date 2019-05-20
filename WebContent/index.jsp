<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Demo Ajax</title>
<link rel='stylesheet' id='theme-style-css'  href='/SportID/style.css' type='text/css' media='all' />
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#bttHello').click(function(){
			var fullname = $('#fullname').val();
			$.ajax({
				type:'POST',
				data: {fullname: fullname},
				url:'NewServlet',
				success: function(result){
					$('#ajaxresponse').html(result);
				}
			});
		});
		setTimeout(worker,3000);
	});
	
	function worker() {
		var updcmd = "check";
		  $.ajax({
			  type:'POST',
			  url: 'NewServlet', 
			  data: {fullname: updcmd},
			  success: function(result) {
				  $('#ajaxresponse').html(result);
		    },
		    complete: function() {
		      // Schedule the next request when the current one's complete
		      setTimeout(worker, 3000);
		    }
		  });
		}

</script>
</head>
<body>
<div id="MainContainer">
<form>
	Command: <input type="text" id="fullname">
	<input type="button" value="Send" id="bttHello">
	<br>
	<span id="result1"></span>
</form>


<a href="https://www.w3schools.com">
<img src="images/black-web-button.png" alt="Go to W3Schools!" width="42" height="42" border="0">
</a>


<div id="ajaxresponse">
<table>
	<tr>
		<td>
			<p><strong><em>№</em></strong></p>
		</td>
		<td>
			<p><strong><em>EPC</em></strong></p>
		</td>
		<td>
			<p><strong><em>Last seen</em></strong></p>
		</td>
	</tr>
	<!--<tr>
		<td>
			<p>1.</p>
		</td>
		<td>
			<p>Профаза</p>
		</td>
		<td>
			<p>В профазе митоза происходит растворение ядерной оболочки и ядрышка, центриоли расходятся к разным полюсам, начинается формирование микротрубочек, так называемых нитей веретена деления, конденсируются хроматиды в хромосомах.</p>
		</td>
	</tr>
	
	<tr>
		<td>
			<p>2.</p>
		</td>
		<td>
			<p>Метафаза</p>
		</td>
		<td>
			<p>ляютсямежду полюсами.</p>-
		</td>
	</tr>-->

</table>
</div>
</div>

</body>
</html>