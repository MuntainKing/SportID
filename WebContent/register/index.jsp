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
	$(document).ready(function() {
		setTimeout(worker, 3000);
	});

	function worker() {
		$.ajax({
			type : 'POST',
			url : '/SportID/NewServlet',
			data : {
				action : 'SensListCheck'
			},
			success : function(result) {
				$('#senslistcont').html(result);
			},
			complete : function() {
				// Schedule the next request when the current one's complete
				setTimeout(worker, 500);
			}
		});
	}
</script>
</head>
<body>
	<div id="MainContainer">
		<div class="nav-container">
			<div class="centered">
			<p id="pageTitle">Регистрация</p>
				<a class="nav-element" href="/SportID/tag/">Метки</a> <a
					class="nav-element" href="/SportID/">Меню</a> <a
					class="nav-element" href="/SportID/">Забег</a>
			</div>
		</div>
		<div class="container">
			<div class="column">
				<form action="/action_page.php">
					<label for="fname">Имя</label> <input type="text" id="fname"
						name="firstname" placeholder="Имя участника"> <label
						for="surname">Фаимлия</label> <input type="text" id="surname"
						name="lastname" placeholder="Фамилия участника"> <label
						for="Pname">Отчество</label> <input type="text" id="Pname"
						name="patronymic" placeholder="Отчество участника"> <label
						for="number">Номер</label> <input type="text" id="number"
						name="number" placeholder="Номер участника"> <label
						for="byear">Год рождения</label> <select id="byear"
						name="birth-year">
						<option value="2018">2018</option>
						<option value="2017">2017</option>
						<option value="2016">2016</option>
						<option value="2015">2015</option>
						<option value="2014">2014</option>
						<option value="2013">2013</option>
						<option value="2012">2012</option>
						<option value="2011">2011</option>
						<option value="2010">2010</option>
						<option value="2009">2009</option>
						<option value="2008">2008</option>
						<option value="2007">2007</option>
						<option value="2006">2006</option>
						<option value="2005">2005</option>
						<option value="2004">2004</option>
						<option value="2003">2003</option>
						<option value="2002">2002</option>
						<option value="2001">2001</option>
						<option value="2000">2000</option>
						<option value="1999">1999</option>
						<option value="1998">1998</option>
						<option value="1997">1997</option>
						<option value="1996">1996</option>
						<option value="1995">1995</option>
						<option value="1994">1994</option>
						<option value="1993">1993</option>
						<option value="1992">1992</option>
						<option value="1991">1991</option>
						<option value="1990">1990</option>
						<option value="1989">1989</option>
						<option value="1988">1988</option>
						<option value="1987">1987</option>
						<option value="1986">1986</option>
						<option value="1985">1985</option>
						<option value="1984">1984</option>
						<option value="1983">1983</option>
						<option value="1982">1982</option>
						<option value="1981">1981</option>
						<option value="1980">1980</option>
						<option value="1979">1979</option>
						<option value="1978">1978</option>
						<option value="1977">1977</option>
						<option value="1976">1976</option>
						<option value="1975">1975</option>
						<option value="1974">1974</option>
						<option value="1973">1973</option>
						<option value="1972">1972</option>
						<option value="1971">1971</option>
						<option value="1970">1970</option>
						<option value="1969">1969</option>
						<option value="1968">1968</option>
						<option value="1967">1967</option>
						<option value="1966">1966</option>
						<option value="1965">1965</option>
						<option value="1964">1964</option>
						<option value="1963">1963</option>
						<option value="1962">1962</option>
						<option value="1961">1961</option>
						<option value="1960">1960</option>
						<option value="1959">1959</option>
						<option value="1958">1958</option>
						<option value="1957">1957</option>
						<option value="1956">1956</option>
						<option value="1955">1955</option>
						<option value="1954">1954</option>
						<option value="1953">1953</option>
						<option value="1952">1952</option>
						<option value="1951">1951</option>
						<option value="1950">1950</option>
						<option value="1949">1949</option>
						<option value="1948">1948</option>
						<option value="1947">1947</option>
						<option value="1946">1946</option>
						<option value="1945">1945</option>
						<option value="1944">1944</option>
						<option value="1943">1943</option>
						<option value="1942">1942</option>
						<option value="1941">1941</option>
						<option value="1940">1940</option>
						<option value="1939">1939</option>
						<option value="1938">1938</option>
						<option value="1937">1937</option>
						<option value="1936">1936</option>
						<option value="1935">1935</option>
						<option value="1934">1934</option>
						<option value="1933">1933</option>
						<option value="1932">1932</option>
						<option value="1931">1931</option>
						<option value="1930">1930</option>
						<option value="1929">1929</option>
						<option value="1928">1928</option>
						<option value="1927">1927</option>
						<option value="1926">1926</option>
						<option value="1925">1925</option>
						<option value="1924">1924</option>
						<option value="1923">1923</option>
						<option value="1922">1922</option>
						<option value="1921">1921</option>
						<option value="1920">1920</option>
						<option value="1919">1919</option>
						<option value="1918">1918</option>
						<option value="1917">1917</option>
						<option value="1916">1916</option>
						<option value="1915">1915</option>
						<option value="1914">1914</option>
						<option value="1913">1913</option>
						<option value="1912">1912</option>
						<option value="1911">1911</option>
						<option value="1910">1910</option>
						<option value="1909">1909</option>
						<option value="1908">1908</option>
						<option value="1907">1907</option>
						<option value="1906">1906</option>
						<option value="1905">1905</option>
					</select> <label for="gender">Пол</label> <input type="radio" name="gender"
						value="male">Мужчина <input type="radio" name="gender"
						value="female">Женщина <input type="radio" name="gender"
						value="other">Другой <input type="submit"
						value="Зарегистрировать">
				</form>
			</div>
			<!--column1 -->

			<div class="column">
				<label for="senslistcont">EPC метки</label>
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
			<!--column2 -->

			<div class="column"></div>

			<!--column 3 -->
			<div class="competitors-table-container">
				<label for="registerTable">Участники</label>

				<div id="registerTable">
					<table>
						<tr>
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
</body>
</html>