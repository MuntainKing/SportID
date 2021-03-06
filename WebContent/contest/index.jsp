<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="../assets/img/favicon.png">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>
    Sport ID Timing Systems
  </title>
  <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
  <!--     Fonts and icons     -->
  <link rel="stylesheet" type="text/css" href="../assets/fonts/fonts.css" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
  <!-- CSS Files -->
  <link href="../assets/css/material-dashboard.css?v=2.1.1" rel="stylesheet" />
</head>

<body class="">
  <div class="wrapper ">
    <div class="sidebar" data-color="purple" data-background-color="white" data-image="../assets/img/sidebar-1.jpg">
      <!--
        Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"

        Tip 2: you can also add an image using data-image tag
    -->
      <div class="logo">
        <a href="../" class="simple-text logo-normal">
          Sport ID
        </a>
      </div>
      <div class="sidebar-wrapper">
        <ul class="nav">
          <li class="nav-item  ">
            <a class="nav-link" href="../tag/">
              <i class="material-icons">dashboard</i>
              <p>Проверка меток</p>
            </a>
          </li>
          <li class="nav-item ">
            <a class="nav-link" href="../register/">
              <i class="material-icons">person</i>
              <p>Регистрация</p>
            </a>
          </li>
          <li class="nav-item active ">
            <a class="nav-link" href="../contest/">
              <i class="material-icons">content_paste</i>
              <p>Соревнование</p>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="../results/">
              <i class="material-icons">library_books</i>
              <p>Результаты</p>
            </a>
        </ul>
      </div>
    </div>
    <div class="main-panel">
      <!-- Navbar -->
      <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
        <div class="container-fluid">
          <div class="navbar-wrapper">
            <a class="navbar-brand">Sport ID Соревнование</a>
          </div>
          <button class="navbar-toggler" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
          </button>
        </div>
      </nav>
      <!-- End Navbar -->
			<div class="content">
				<div class="container-fluid">
					<div class="row">
					<div class="col-lg-6 col-md-12">
						<div class="card">
							<div class="card-header card-header-primary">
								<h4 class="card-title">Ридер</h4>
							</div>
							<div class="card-body table-responsive">
								<div class="indiContainer">
									<div class="ReadyContainer">
										<p class="ReaderP">Ридер готов</p>
										<img id="RR" alt="ридер готов"
											src="/SportID/images/onlineIndicator.png">
									</div>
									<div class="ReadContainer">
										<p class="ReaderP">Идет чтение</p>
										<img id="RI" alt="чтение остановлено"
											src="/SportID/images/offlineINdicator.png">
									</div>
								</div>
								<div class="ReaderControls">
									<button class="btn btn-primary pull-right" id="ReadBttn">Начать чтение</button>
									<button class="btn btn-primary pull-right" id="StopBttn">Остановить чтение</button>
								</div>
							</div>
						</div>
						</div>
						<div class="col-lg-6 col-md-12">
						<div class="card">
							<div class="card-header card-header-primary">
								<h4 class="card-title">Подготовка к соревнованию</h4>
							</div>
							<div class="card-body table-responsive">
								<label class="listsTip">Участники</label> <select id="lists">
									<option>Выберите список</option>
								</select> <select id="laps">
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
								<button class="btn btn-primary contestControls" id="CreateTableBtn">Сформировать таблицу</button>
							</div>
						</div>
						</div>
						<div class="col-lg-6 col-md-12">
						<div class="card">
							<div class="card-header card-header-primary">
								<h4 class="card-title">Управление соревнованием</h4>
							</div>
							<div class="card-body table-responsive">
								<button class="btn btn-primary contestControls" id="StartBtn">Старт</button>
								<button class="btn btn-primary contestControls" id="StopBtn">Стоп</button>	
								<button class="btn btn-primary contestControls" id="NewCompetBtn">Новое соревнование</button>
							</div>
						</div>
						</div>
						<div class="col-lg-6 col-md-12">
							<div class="card">
								<div class="card-header card-header-primary">
									<h4 class="card-title">Окончание соревнования</h4>
								</div>
								<div class="card-body table-responsive">
									<h4>Назовите соревнование :</h4>
									<input type="text" id="ContestName">
									<button class="btn btn-primary" id="SaveBtn">Сохранить результат</button>
									<a class="btn btn-primary download_link" id="DownloadRes">Скачать результат</a>
								</div>
							</div>
						</div>
						<div id="TimerContainer">
						<div class = "Timer">00:00:00:000</div>
						</div>
						<div id="CompetTable">	</div>
						<div id="FinalistTable">	</div>
				</div>
			</div>
			<footer class="footer">

      </footer>
    </div>
  </div>
  </div>
  <!--   Core JS Files   -->
  <script src="../assets/js/core/jquery.min.js"></script>
  <script src="../assets/js/core/popper.min.js"></script>
  <script src="../assets/js/core/bootstrap-material-design.min.js"></script>
  <script src="../assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
  <!-- Plugin for the momentJs  -->
  <script src="../assets/js/plugins/moment.min.js"></script>
  <!--  Plugin for Sweet Alert -->
  <script src="../assets/js/plugins/sweetalert2.js"></script>
  <!-- Forms Validations Plugin -->
  <script src="../assets/js/plugins/jquery.validate.min.js"></script>
  <!-- Library for adding dynamically elements -->
  <script src="../assets/js/plugins/arrive.min.js"></script>
  <!-- Chartist JS -->
  <script src="../assets/js/plugins/chartist.min.js"></script>
  <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
  <script src="../assets/js/material-dashboard.js?v=2.1.1" type="text/javascript"></script>
  <script>
  $(document).ready(function() {
      $().ready(function() {
        $sidebar = $('.sidebar');

        $sidebar_img_container = $sidebar.find('.sidebar-background');

        $full_page = $('.full-page');

        $sidebar_responsive = $('body > .navbar-collapse');

        window_width = $(window).width();

        fixed_plugin_open = $('.sidebar .sidebar-wrapper .nav li.active a p').html();

        if (window_width > 767 && fixed_plugin_open == 'Dashboard') {
          if ($('.fixed-plugin .dropdown').hasClass('show-dropdown')) {
            $('.fixed-plugin .dropdown').addClass('open');
          }

        }

        $('.fixed-plugin a').click(function(event) {
          
          if ($(this).hasClass('switch-trigger')) {
            if (event.stopPropagation) {
              event.stopPropagation();
            } else if (window.event) {
              window.event.cancelBubble = true;
            }
          }
        });

        $('.fixed-plugin .active-color span').click(function() {
          $full_page_background = $('.full-page-background');

          $(this).siblings().removeClass('active');
          $(this).addClass('active');

          var new_color = $(this).data('color');

          if ($sidebar.length != 0) {
            $sidebar.attr('data-color', new_color);
          }

          if ($full_page.length != 0) {
            $full_page.attr('filter-color', new_color);
          }

          if ($sidebar_responsive.length != 0) {
            $sidebar_responsive.attr('data-color', new_color);
          }
        });

        $('.fixed-plugin .background-color .badge').click(function() {
          $(this).siblings().removeClass('active');
          $(this).addClass('active');

          var new_color = $(this).data('background-color');

          if ($sidebar.length != 0) {
            $sidebar.attr('data-background-color', new_color);
          }
        });

        $('.fixed-plugin .img-holder').click(function() {
          $full_page_background = $('.full-page-background');

          $(this).parent('li').siblings().removeClass('active');
          $(this).parent('li').addClass('active');


          var new_image = $(this).find("img").attr('src');

          if ($sidebar_img_container.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
            $sidebar_img_container.fadeOut('fast', function() {
              $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
              $sidebar_img_container.fadeIn('fast');
            });
          }

          if ($full_page_background.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $full_page_background.fadeOut('fast', function() {
              $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
              $full_page_background.fadeIn('fast');
            });
          }

          if ($('.switch-sidebar-image input:checked').length == 0) {
            var new_image = $('.fixed-plugin li.active .img-holder').find("img").attr('src');
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
            $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
          }

          if ($sidebar_responsive.length != 0) {
            $sidebar_responsive.css('background-image', 'url("' + new_image + '")');
          }
        });

        $('.switch-sidebar-image input').change(function() {
          $full_page_background = $('.full-page-background');

          $input = $(this);

          if ($input.is(':checked')) {
            if ($sidebar_img_container.length != 0) {
              $sidebar_img_container.fadeIn('fast');
              $sidebar.attr('data-image', '#');
            }

            if ($full_page_background.length != 0) {
              $full_page_background.fadeIn('fast');
              $full_page.attr('data-image', '#');
            }

            background_image = true;
          } else {
            if ($sidebar_img_container.length != 0) {
              $sidebar.removeAttr('data-image');
              $sidebar_img_container.fadeOut('fast');
            }

            if ($full_page_background.length != 0) {
              $full_page.removeAttr('data-image', '#');
              $full_page_background.fadeOut('fast');
            }

            background_image = false;
          }
        });

        $('.switch-sidebar-mini input').change(function() {
          $body = $('body');

          $input = $(this);

          if (md.misc.sidebar_mini_active == true) {
            $('body').removeClass('sidebar-mini');
            md.misc.sidebar_mini_active = false;

            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar();

          } else {

            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar('destroy');

            setTimeout(function() {
              $('body').addClass('sidebar-mini');

              md.misc.sidebar_mini_active = true;
            }, 300);
          }

          // we simulate the window Resize so the charts will get updated in realtime.
          var simulateWindowResize = setInterval(function() {
            window.dispatchEvent(new Event('resize'));
          }, 180);

          // we stop the simulation of Window Resize after the animations are completed
          setTimeout(function() {
            clearInterval(simulateWindowResize);
          }, 1000);

        });
      });
    });
  </script>
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
</body>

</html>
