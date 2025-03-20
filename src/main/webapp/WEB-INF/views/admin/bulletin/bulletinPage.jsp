<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC -HelloWorld</title>

</head>

<body>
	<div class="container-fluid">
		<div class="pageContent row">
			<!-- panel 區塊 -->
			<div class="panel panel-default">

				<!-- 標題列 -->
				<div class="panel-heading">
					<!-- 標題 -->
					<h1 class="panel-title">
						<b>${sessionScope.funcName}</b>
					</h1>
					<!-- 標題END -->
				</div>
				<!-- 標題列 END -->

				<!-- panel-body 區塊 -->
				<div class="panel-body">
					<div class="queryForm ">


						<div class="container-fluid">
							<div class="row ">
								<div class="col-sm-4 text-left">
									公告文號 <input type="text" name="input1" value="">
								</div>
							</div>


							<div class="row top-buffer">
								<div class="col-sm-4 text-left">
									公告起日 <input type="text" id="input2" name="input2" value="">
								</div>
								<div class="col-sm-4 text-left">
									公告訖日 <input type="text" id="input3" name="input3" value="">
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- panel-body 區塊END -->

				<!-- my_btn 區塊 -->
				<div class="my_btn">
					<div class="customBtn right ">
						<ul>
							<button type="button" id="btn_search" class="btn btn-primary ">
								<span class="glyphicon glyphicon-search"></span>查詢
							</button>
							<button type="button" id="resetBtn" class="btn btn-primary ">
								<span class="glyphicon glyphicon-refresh"></span>重填
							</button>
						</ul>
					</div>

				</div>
				<!-- my_btn 區塊END -->




			</div>
			<!-- panel 區塊END -->
		</div>
	</div>
	<div class="container-fluid">
		<div id="grid"></div>

	</div>


	<script>
		$(document).ready(function() {

			$("#input2,#input3").kendoDatePicker({
				// defines the start view
				start : "year",
				// display month and year in the input
				format : "yyyy/MM/dd"
			});

			$("#grid").kendoGrid({
				columns : [ {field : "bFrom", title: "公告來源",width : 150},{field : "bNo", title: "公告文號",width : 170}, {field : "bSDate", title: "公告日期",width : 150}, {field : "bEDate", title: "截止日期",width : 150}, {field : "bTitle", title: "公告標題",width : 300} ],
				dataSource : [ {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020040",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "107年4檔DM申報作業公告"
				}, {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020039",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "107年3檔DM申報作業公告"
				}, {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020038",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "107年2檔DM申報作業公告"
				}, {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020037",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "107年1檔DM申報作業公告"
				}, {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020036",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "106年12檔DM申報作業公告"
				}, {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020035",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "106年11檔DM申報作業公告"
				}, {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020034",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "106年10檔DM申報作業公告"
				}, {
					bFrom : "全聯-XX部-Axxx",
					bNo : "171023A00020033",
					bSDate : "20180616",
					bEDate : "20180630",
					bTitle : "106年9檔DM申報作業公告"
				} ],
				height: 300,
			    sortable: true
			});

		});
	</script>
</body>

</html>