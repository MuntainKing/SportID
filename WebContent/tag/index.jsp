<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Demo Ajax</title>
<link rel='stylesheet' id='theme-style-css'  href='/SportID/tag/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#bttHello').click(function(){
			var fullname = $('#fullname').val();
			$.ajax({
				type:'POST',
				data: {fullname: fullname},
				url:'/SportID/NewServlet',
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
			  url: '/SportID/NewServlet', 
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
<div class = "nav-container">
<div class = "centered">
<a class = "nav-element" href="/SportID/">
Назад
</a>
</div>
</div>
<div class = "container">
<div class = "column">


</div><!--column1 -->

<div class = "column">
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
</table>
</div>
</div>

<div class = "column">
</div>
</div>
</div>
</body>
</html>