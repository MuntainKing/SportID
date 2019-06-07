<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Sport ID Tags check</title>
<link rel='stylesheet' id='theme-style-css'
	href='/SportID/tag/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/SportID/js/StartReadBtn.js"></script>
<script type="text/javascript" src="/SportID/js/StopReadBtn.js"></script>
<script type="text/javascript" src="/SportID/js/RstSensListBtn.js"></script>
<script type="text/javascript" src="/SportID/js/SaveSensListBtn.js"></script>
<script type="text/javascript" src="/SportID/js/TagWorker.js"></script>
<script type="text/javascript" src="/SportID/js/ReaderStatusWorker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		setTimeout(TagWorker, 500);
		setTimeout(ReaderStatusWorker,500);
	});

	$(window).resize(function() {
		if(this.resizeTO) clearTimeout(this.resizeTO);
		    this.resizeTO = setTimeout(function() {
		      $(this).trigger('windowResize');
		    }, 500); 
		});
</script>
</head>
<body>
	<div id="MainContainer">
		<div class="nav-container">
			<div class="centered">
				<p id="pageTitle">Проверка меток</p>
				<a class="nav-element" href="/SportID/"> Назад </a> <a
					class="nav-element" href="/SportID/register/"> Регистрация </a>
			</div>
		</div>
		<div class="container">
			<div class="column">
				<div class="indiContainer">
					<div class="ReadyContainer">
						<a>Ридер готов</a> <img id="RR" alt="ридер готов"
							src="/SportID/images/onlineIndicator.png">
					</div>
					<div class="ReadContainer">
						<a>Идет чтение</a> <img id="RI" alt="чтение остановлено"
							src="/SportID/images/offlineINdicator.png">
					</div>
				</div>

				<button id="ReadBttn">Начать чтение</button>

				<button id="StopBttn">Остановить чтение</button>

				<button id="RstBttn">Сброс</button>
				<button id="SaveBttn">Сохранить</button>

			</div>
			<!--column1 -->

			<div class="column" style="width: 47%;">
				<div id="tableContainer">
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
				</div>

				<div id="saveStatus"></div>
			</div>

			<div class="column" style="width: 19%;">

				<div id="htmlLog"></div>

			</div>
		</div>
	</div>
</body>
</html>