<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Sport ID Contest</title>
<link rel='stylesheet' id='theme-style-css'
	href='/SportID/contest/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/SportID/js/StartReadBtn.js"></script>
<script type="text/javascript" src="/SportID/js/StopReadBtn.js"></script>
<script type="text/javascript" src="/SportID/js/GetListOfLists.js"></script>
<script type="text/javascript" src="/SportID/js/ReaderStatusWorker.js"></script>
<script type="text/javascript" src="/SportID/js/RetrieveBtn.js"></script>
<script type="text/javascript" src="/SportID/js/StartCompet.js"></script>
<script type="text/javascript" src="/SportID/js/StopCompet.js"></script>
<script type="text/javascript" src="/SportID/js/CompetUpdateWorker.js"></script>
<script type="text/javascript" src="/SportID/js/NewCompet.js"></script>
<script type="text/javascript" src="/SportID/js/SaveResult.js"></script>
<script type="text/javascript" src="/SportID/js/CompetRestore.js"></script>
<script type="text/javascript">
	var Contest = false;
	$(document).ready(function() {	
		setTimeout(ReaderStatusWorker,500);
		setTimeout(CompetUpdateWorker,500);
	    var xmlHttp = new XMLHttpRequest();
	    xmlHttp.open( "GET", "/SportID/NewServlet", false ); 
	    xmlHttp.send( null );
	});
</script>
</head>
<body>
	<div id="MainContainer">
		<div class="nav-container">
			<div class="centered">
				<p id="pageTitle">Соревнование</p>
				<a class="nav-element" href="/SportID/register/">Регистрация</a>
				<a class="nav-element" href="/SportID/">Меню</a>
				<a class="nav-element" href="/SportID/results/">Результаты</a>
			</div>
		</div>
		<div class="container">
			<div class="column">
				<div class="indiContainer">
					<div class="ReadyContainer">
						<p class = "ReaderP">Ридер готов</p> <img id="RR" alt="ридер готов"
							src="/SportID/images/onlineIndicator.png">
					</div>
					<div class="ReadContainer">
						<p class = "ReaderP">Идет чтение</p> <img id="RI" alt="чтение остановлено"
							src="/SportID/images/offlineINdicator.png">
					</div>
				</div>

				<button id="ReadBttn">Начать чтение</button>
				<button id="StopBttn">Остановить чтение</button>

				<div class="listControls"></div>
				<div class="listLog"></div>
				<label class="listsTip">Участники</label>
				<select id="lists">
					<option>Выберите список</option>
				</select>
				<select id="laps">
					<option value="2">2 контрольных точки</option>
					<option value="3">3 контрольных точки</option>
					<option value="4">4 контрольных точки</option>
					<option value="5">5 контрольных точек</option>
					<option value="6">6 контрольных точек</option>
					<option value="7">7 контрольных точек</option>
					<option value="8">8 контрольных точек</option>
					<option value="9">9 контрольных точек</option>
					<option value="10">10 контрольных точек</option>
				</select>
				<button style="width:100%" id="CreateTableBtn">Сформировать таблицу</button>
				<button style="width:100%" id="StartBtn">Старт</button>
				<button style="width:100%" id="StopBtn">Стоп</button>	
				<button style="width:100%" id="NewCompetBtn">Новое соревнование</button>
				
				<div style="width:100%">
					<h2>Назовите соревнование :</h2>
					<input type="text" id="ContestName">
				
				<button id="SaveBtn">Сохранить результат</button>
				<a class="download_link" id="DownloadRes">Скачать результат</a>
				</div>
			</div>
			<!--column1 -->

			<div class="column">
				
			</div>
			<!--column2 -->

			<div class="column" style="text-align: center;">
				
			</div>
			<!--column 3 -->
			<div id="TimerContainer">
			<div class = "Timer">00:00:00:000</div>
			</div>
			<div id="CompetTable">	</div>
			<div id="FinalistTable">	</div>
	</div>
</div>
</body>
</html>