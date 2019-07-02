<%@page import="com.smartrfid.sportid.Competitor"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.smartrfid.sportid.FileController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Sport ID Results</title>
<link rel='stylesheet' id='theme-style-css'
	href='/SportID/results/style.css' type='text/css' media='all' />
<script type="text/javascript" src="/SportID/js/jquery-3.4.1.min.js"></script>
<script>
	$(document).ready(function() {
		$('.DeleteReg').click(function() {
			var filename = this.value;
			$.ajax({
				type : 'POST',
				data : {action : 'deleteFile',value : filename, filetype : false},
				url : '/SportID/ContestSrv',
				success: location.reload()
			});
		});
		
		$('.DeleteRes').click(function() {
			var filename = this.value;
			$.ajax({
				type : 'POST',
				data : {action : 'deleteFile',value : filename, filetype : true},
				url : '/SportID/ContestSrv',
				success: location.reload()
			});
		});
	});
</script>
</head>
<body>
	<div id="MainContainer">
		<div class="nav-container">
			<div class="centered">
				<p id="pageTitle">Результаты</p>
				<a class="nav-element" href="/SportID/contest/">Соревнование</a>
				<a class="nav-element" href="/SportID/">Меню</a>
			</div>
		</div>
		<div class="container">
			<div class="column"></div>
			<!--column1 -->

			<div class="column">
			<h1>Списки участников</h1>
				<%
					FileController fc = new FileController();
					String[] lists = fc.getListofLists();
					int listCount = fc.getListCount();
					Competitor[] competitors;

					String ResultPath = "../../files/results/";
					String CompListPath = "../../files/reglist/";
					
					if (listCount > 0)
					for (int i = 0; i < listCount; i++) {
						String hmtlInsert = lists[i];
						%>
				
				<h2><%= hmtlInsert %></h2>
				<a class="downloadLink" target="_blank" href="<%= CompListPath %><%= hmtlInsert %>.csv">Скачать</a>
				<button value="<%= hmtlInsert %>" class="DeleteReg">Удалить</button>
				<table>
					<tr>
						<td><p>Порядковый</p></td>
						<td><p>Имя</p></td>
						<td><p>Фамилия</p></td>
						<td><p>Отчество</p></td>
						<td><p>Номер</p></td>
						<td><p>Год рождения</p></td>
						<td><p>Пол</p></td>
						<td><p>Метка</p></td>
					</tr>
					<% 
						competitors = fc.retrieveCompetitors(hmtlInsert);
						int CompetCount = fc.getCompetCount();
						for (int j = 0; j < CompetCount; j++) {
							%>
					<tr>
						<td><%= j+1 %></td>
						<td>
							<% String compInsertString = competitors[j].Name; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = competitors[j].Surname; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = competitors[j].Patron; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Integer.toString(competitors[j].Number); %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Integer.toString(competitors[j].BYear); %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = competitors[j].Gender; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = competitors[j].EPC; %> <%= compInsertString %>
						</td>
					</tr>

					<% 
			
						}%>
				</table>
				<% 
					}
					
					
				%>
			<h2>Списки Результатов</h2>
			<%
				String[] resultLists = fc.getListofResults();
				int ResultListCount = fc.getResultListCount();
				String[][] Results;
			
				if (ResultListCount > 0)
				for (int j = 0; j < ResultListCount; j++) {
					String ResultListName = resultLists[j];
					%>
			
				<h2><%= ResultListName %></h2>
				<a class="downloadLink" target="_blank" href="<%= ResultPath %><%= ResultListName %>.csv">Скачать</a>
				<button value="<%= ResultListName %>" class="DeleteRes">Удалить</button>
				<table>
					<tr>
						<td><p>Порядковый</p></td>
						<td><p>Имя</p></td>
						<td><p>Фамилия</p></td>
						<td><p>Отчество</p></td>
						<td><p>Номер</p></td>
						<td><p>Год рождения</p></td>
						<td><p>Пол</p></td>
						<td><p>Метка</p></td>
						<td><p>Результат</p></td>
					</tr>
					<% 
						Results = fc.retrieveResult(ResultListName);
						int FinalistCount = fc.getFinalistCompetCount();
						for (int k = 0; k < FinalistCount; k++) {
							%>
					<tr>
						<td><%= k+1 %></td>
						<td>
							<% String compInsertString = Results[k][0]; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Results[k][1]; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Results[k][2]; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Results[k][3]; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Results[k][4]; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Results[k][5]; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Results[k][6]; %> <%= compInsertString %>
						</td>
						<td>
							<% compInsertString = Results[k][7]; %> <%= compInsertString %>
						</td>
					</tr>

					<% 
			
						}%>
				</table>
				<% 
					}
				%>
			
			</div>
			<!--column2 -->

			<div class="column" style="text-align: center;"></div>
			<!--column 3 -->
		</div>
	</div>
</body>
</html>