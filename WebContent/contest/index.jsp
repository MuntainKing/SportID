<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Sport ID Contest</title>
<link rel='stylesheet' id='theme-style-css'
	href='/SportID/contest/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/SportID/js/x008.js"></script>

</head>
<body>
	<div id="MainContainer">
		<div class="nav-container">
			<div class="centered">
				<p id="pageTitle">Соревнование</p>
				<a class="nav-element" href="/SportID/register/">Регистрация</a>
				<a class="nav-element" href="/SportID/">Меню</a>
				<a class="nav-element" href="/SportID/">Результаты</a>
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

				<div class="listControls">
					<button id="getLists">Получить списки участников</button>
				</div>
				<div class="listLog"></div>
				<select id="lists" >
					<option>Выберите список</option>
				</select>

			</div>
			<!--column1 -->

			<div class="column">
				
			</div>
			<!--column2 -->

			<div class="column" style="text-align: center;">
				
			</div>
			<!--column 3 -->
			
	</div>
</body>
</html>