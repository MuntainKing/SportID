<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Demo Ajax</title>
<link rel='stylesheet' id='theme-style-css'
	href='/SportID/tag/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/SportID/js/x001.js"></script>
<script type="text/javascript" src="/SportID/js/x002.js"></script>
<script type="text/javascript" src="/SportID/js/x003.js"></script>
<script type="text/javascript" src="/SportID/js/x004.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#bttHello').click(function() {
			var fullname = $('#fullname').val();
			$.ajax({
				type : 'POST',
				data : {
					fullname : fullname,
					action : 'epcCheckAct'
				},
				url : '/SportID/NewServlet',
				success : function(result) {
					$('#ajaxresponse').append(result);
				}
			});
		});
		setTimeout(worker, 3000);
	});

	function worker() {
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
			<p id="pageTitle">Проверка меток</p>
				<a class="nav-element" href="/SportID/"> Назад </a>
				<a class="nav-element" href="/SportID/register/"> Регистрация </a>
			</div>
		</div>
		<div class="container">
			<div class="column">

				<button id="SaveBttn">Сохранить</button>

				<button id="ReadBttn">Чтение</button>

				<button id="StopBttn">Стоп</button>

				<button id="RstBttn">Сброс</button>

			</div>
			<!--column1 -->

			<div class="column">
				<div id="ajaxresponse">
					<table>
						<tr>
							<td>
								<p>№</p>
							</td>
							<td>
								<p>EPC</p>
							</td>
							<td>
								<p>Last seen</p>
							</td>
						</tr>
					</table>
				</div>


				<div id="saveStatus"></div>
			</div>

			<div class="column">

				<div id="htmlLog"></div>

			</div>
		</div>
	</div>
</body>
</html>