<%@page import="java.util.Date"%>
<%@page import="jdk.nashorn.internal.runtime.linker.JavaAdapterFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Sport ID Registration</title>
<link rel='stylesheet' id='theme-style-css'
	href='/SportID/register/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/SportID/js/StartReadBtn.js"></script>
<script type="text/javascript" src="/SportID/js/StopReadBtn.js"></script>
<script type="text/javascript" src="/SportID/js/AddCompetitorFrm.js"></script>
<script type="text/javascript" src="/SportID/js/CompRetriever.js"></script>
<script type="text/javascript" src="/SportID/js/ReaderStatusWorker.js"></script>
<script type="text/javascript" src="/SportID/js/RegWorker.js"></script>
<script type="text/javascript" src="/SportID/js/SaveCmpListBtn.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				setTimeout(RegWorker, 500);
				setTimeout(CompRetriever, 500);
				setTimeout(ReaderStatusWorker, 500);

				$('#byear').each(
					function() {
						var current = (new Date()).getFullYear();
						var year = 1905;
						for (var i = 0; i < current - year; i++) {
						$(this).append('<option value="'
							+ (year + i) + '">'
							+ (year + i)
							+ '</option>');
						}

					})
			});
</script>
</head>
<body>
	<div id="MainContainer">
		<div class="nav-container">
			<div class="centered">
				<p id="pageTitle">Регистрация</p>
				<a class="nav-element" href="/SportID/tag/">Метки</a> <a
					class="nav-element" href="/SportID/">Меню</a> <a
					class="nav-element" href="/SportID/contest/">Забег</a>
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

				<div class="listControls">
					<h2>Назовите список :</h2>
					<input type="text" id="listname">
					<button id="saveList">Сохранить список</button>
					<a class="download_link" id="fileRequest">Скачать список</a>
				</div>
				<div class="listLog"></div>
				
			</div>
			<!--column1 -->

			<div class="column">
				<form id="regForm" action="/action_page.php">
					<label for="fname">Имя</label>
					<input type="text" id="fname" name="firstname" placeholder="Имя участника">
					
					<label for="surname">Фамилия</label>
					<input type="text" id="surname" name="lastname" placeholder="Фамилия участника">
					
					<label for="Pname">Отчество</label>
					<input type="text" id="Pname" name="patronymic" placeholder="Отчество участника">
					
					<label for="number">Номер</label>
					<input type="text" id="number" name="number" placeholder="Номер участника">
					
					<label for="byear">Год рождения</label>
					<select id="byear" name="birth-year"></select>
					
					<label for="gender">Пол</label>
					<div>
						<input type="radio" name="gender" value="male" checked>Мужчина
						<input type="radio" name="gender" value="female">Женщина
						<input type="radio" name="gender" value="other">Другой
					</div>
					<div>
					<input type="checkbox" name="edit" id="edit">
					<label>Редактировать</label>
					</div>
					<input type="submit" value="Зарегистрировать">
				</form>
				<div id="form-msg"></div>
			</div>
			<!--column2 -->

			<div class="column" style="text-align: center;">
				<label for="senslistcont" style="display:block;">Метки</label>
				<div id="senslistcont">
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
			<!--column 3 -->
			<div class="competitors-table-container">
				<div>
				<label for="registerTable">Участники</label>
				</div>
				<div id="registerTable">
					<table>
						<tr>
							<td>
								<p>Порядковый</p>
							</td>
							<td>
								<p>Имя</p>
							</td>
							<td>
								<p>Фаимлия</p>
							</td>
							<td>
								<p>Отчество</p>
							</td>
							<td>
								<p>Номер</p>
							</td>
							<td>
								<p>Год рождения</p>
							</td>
							<td>
								<p>Пол</p>
							</td>
							<td>
								<p>Метка</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="footer">
		</div>
	</div>
</body>
</html>