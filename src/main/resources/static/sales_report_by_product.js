// Sales Report by Product
var data;
var chartOptions;

$(document).ready(function() {
	setupButtonEventHandlers("_product", loadSalesReportByDateForProduct);
});

function loadSalesReportByDateForProduct(period) {
	if (period == "customizedDate") {
		startDate = $("#startDate_product").val();
		endDate = $("#endDate_product").val();

		requestURL = contextPath + "statisticals/product/" + startDate + "/" + endDate;
	} else {
		requestURL = contextPath + "statisticals/product/" + period;
	}

	$.get(requestURL, function(responseJSON) {
		console.log(responseJSON)
		prepareChartDataForSalesReportByProduct(responseJSON);
		customizeChartForSalesReportByProduct();
		formatChartData(data, 2, 3);
		drawChartForSalesReportByProduct(period);
		setSalesAmount(period, '_product', "Tổng sản phẩm");
	});
}

function prepareChartDataForSalesReportByProduct(responseJSON) {
	data = new google.visualization.DataTable();
	data.addColumn('string', 'Sản phẩm');
	data.addColumn('number', 'Số lượng');
	data.addColumn('number', 'Tổng doanh thu');
	data.addColumn('number', 'Doanh thu thuần');

	totalGrossSales = 0;
	totalNetSales = 0;
	totalItems = 0;
	console.log(data)
	$.each(responseJSON, function(index, reportItem) {
		console.log(reportItem);
		data.addRows([
			[reportItem.identifier, reportItem.productsCount, reportItem.grossSales, reportItem.netSales]
		]);
		totalGrossSales += parseInt(reportItem.grossSales);
		totalNetSales += parseInt(reportItem.netSales);
		totalItems += parseInt(reportItem.productsCount);
	});
	console.log(data)
}

function customizeChartForSalesReportByProduct() {
	chartOptions = {
		height: 360, width: '98%',
		showRowNumber: true,
		page: 'enable',
		sortColumn: 2,
		sortAscending: false
	};
}

function drawChartForSalesReportByProduct() {
	var formatter = new google.visualization.NumberFormat({
		groupingSymbol: '.',
		decimalSymbol: ',',
		fractionDigits: 0
	});

	formatter.format(data, 2); // Format the third column (Tổng doanh thu)
	formatter.format(data, 3); // Format the fourth column (Doanh thu thuần)
	var salesChart = new google.visualization.Table(document.getElementById('chart_sales_by_product'));
	salesChart.draw(data, chartOptions);
}