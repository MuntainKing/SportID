<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Demo Ajax</title>
<link rel='stylesheet' id='theme-style-css'
	href='/SportID/register/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#bttHello').click(function(){setTimeout(worker,3000);});
	
	function worker() {
		var updcmd = "check";
		  $.ajax({
			  type:'POST',
			  url: '/SportID/NewServlet', 
			  data: {fullname: updcmd, action : 'epcCheckAct'},
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
		<div class="nav-container">
			<div class="centered">
				<a class="nav-element" href="/SportID/tag/">Метки</a>
				<a class="nav-element" href="/SportID/">Меню</a>
				<a class="nav-element" href="/SportID/">Забег</a>
			</div>
		</div>
		<div class="container">
			<div class="column">
				<formaction="/action_page.php"> <label for="fname">First
					Name</label> <input type="text" id="fname" name="firstname"
					placeholder="Your name.."> <label for="lname">Last
					Name</label> <input type="text" id="lname" name="lastname"
					placeholder="Your last name.."> <label for="country">Country</label>
				<select id="country" name="country">
					<option value="australia">Australia</option>
					<option value="canada">Canada</option>
					<option value="usa">USA</option>
				</select> <input type="submit" value="Submit">

				</form>
			</div>
			<!--column1 -->

			<div class="column"></div>
			<!--column2 -->

			<div class="column"></div>
			<!--column 3 -->

		</div>
	</div>
</body>
</html>