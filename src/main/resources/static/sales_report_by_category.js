// Sales Report by Category
var data;
var chartOptions;

$(document).ready(function() {
	setupButtonEventHandlers("_category", loadSalesReportByDateForCategory);
});

function loadSalesReportByDateForCategory(period) {
	if (period == "customizedDate") {
		startDate = $("#startDate_category").val();
		endDate = $("#endDate_category").val();

		requestURL = contextPath + "statisticals/category/" + startDate + "/" + endDate;
	} else {
		requestURL = contextPath + "statisticals/category/" + period;
	}

	$.get(requestURL, function(responseJSON) {
		prepareChartDataForSalesReportByCategory(responseJSON);
		customizeChartForSalesReportByCategory();
		formatChartData(data, 1, 2);
		drawChartForSalesReportByCategory(period);
		setSalesAmount(period, '_category', "Tổng sản phẩm");
	});
}

function prepareChartDataForSalesReportByCategory(responseJSON) {
	data = new google.visualization.DataTable();
	data.addColumn('string', 'Danh mục');
	data.addColumn('number', 'Tổng doanh thu');
	data.addColumn('number', 'Doanh thu thuần');

	totalGrossSales = 0;
	totalNetSales = 0;
	totalItems = 0;

	$.each(responseJSON, function(index, reportItem) {
		data.addRows([[reportItem.identifier, reportItem.netSales, reportItem.netSales]]);
		totalGrossSales += parseInt(reportItem.grossSales);
		totalNetSales += parseInt(reportItem.netSales);
		totalItems += parseInt(reportItem.productsCount);
	});
}

function customizeChartForSalesReportByCategory() {
	chartOptions = {
		height: 360, legend: { position: 'right' }
	};
}

function drawChartForSalesReportByCategory() {
	var formatter = new google.visualization.NumberFormat({
		groupingSymbol: '.',
		decimalSymbol: ',',
		fractionDigits: 0
	});

	formatter.format(data, 1); // Format the third column (Tổng doanh thu)
	formatter.format(data, 2); // Format the fourth column (Doanh thu thuần)
	var salesChart = new google.visualization.PieChart(document.getElementById('chart_sales_by_category'));
	salesChart.draw(data, chartOptions);
}