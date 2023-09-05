// Sales Report by Product
var data;
var chartOptions;

$(document).ready(function() {
    setupButtonEventHandlers("_orderdetail", loadSalesReportByDateForOrderDetail);
});

// function loadSalesReportByDateForOrderDetail(period) {
//     if (period == "customizedDate") {
//         startDate = $("#startDate_product").val();
//         endDate = $("#endDate_product").val();
//
//         requestURL = contextPath + "statisticals/orderdetail/" + startDate + "/" + endDate;
//     } else {
//         requestURL = contextPath + "statisticals/orderdetail/" + period;
//     }
//
//     $.get(requestURL, function(responseJSON) {
//         console.log(responseJSON)
//         prepareChartDataForSalesReportByOrderDetail(responseJSON);
//         customizeChartForSalesReportByOrderDetail();
//         formatChartData(data, 2, 3);
//         drawChartForSalesReportByOrderDetail(period);
//         setSalesAmount(period, '_orderdetail', "Hóa đơn chi tiết");
//     });
// }
//
// function prepareChartDataForSalesReportByOrderDetail(responseJSON) {
//     data = new google.visualization.DataTable();
//     data.addColumn('string', 'ID Order Detail');
//     data.addColumn('number', 'Quantity');
//     data.addColumn('number', 'Gross Sales');
//     data.addColumn('number', 'Net Sales');
//
//     totalGrossSales = 0.0;
//     totalNetSales = 0.0;
//     totalItems = 0;
//     console.log(data)
//     $.each(responseJSON, function(index, reportItem) {
//         console.log(reportItem);
//         data.addRows([[reportItem.identifier, reportItem.productsCount, reportItem.grossSales, reportItem.netSales]]);
//         totalGrossSales += parseFloat(reportItem.grossSales);
//         totalNetSales += parseFloat(reportItem.netSales);
//         totalItems += parseInt(reportItem.productsCount);
//     });
//     console.log(data)
// }
//
// function customizeChartForSalesReportByOrderDetail() {
//     chartOptions = {
//         height: 360, width: '98%',
//         showRowNumber: true,
//         page: 'enable',
//         sortColumn: 2,
//         sortAscending: false
//     };
// }
//
// function drawChartForSalesReportByOrderDetail() {
//     var salesChart = new google.visualization.Table(document.getElementById('chart_sales_by_orderdetail'));
//     salesChart.draw(data, chartOptions);
// }