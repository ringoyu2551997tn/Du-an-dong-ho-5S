// Sales Report Common
var MILLISECONDS_A_DAY = 24 * 60 * 60 * 1000;
var period;
function setupButtonEventHandlers(reportType, callbackFunction) {
	$(".button-sales-by" + reportType).on("click", function() {
		$(".button-sales-by" + reportType).each(function(e) {
			$(this).removeClass('btn-primary').addClass('btn-light');
		});

		$(this).removeClass('btn-light').addClass('btn-primary');

		period = $(this).attr("period");
		if (period) {
			callbackFunction(period);
			$("#divCustomDateRange" + reportType).addClass("d-none");
		} else {
			$("#divCustomDateRange" + reportType).removeClass("d-none");
		}
	});

	initCustomDateRange(reportType);

	$("#buttonViewReportByDateRange" + reportType).on("click", function(e) {
		validateDateRange(reportType, callbackFunction);
	});
}

function validateDateRange(reportType, callbackFunction) {
	startDateField = document.getElementById('startDate' + reportType);
	days = calculateDays(reportType);

	startDateField.setCustomValidity("");

	if (days >= 7 && days <= 30) {
		callbackFunction("customizedDate");
	} else {
		startDateField.setCustomValidity("Dates must be in the range of 7..30 days");
		startDateField.reportValidity();
	}
}

function calculateDays(reportType) {
	startDateField = document.getElementById('startDate' + reportType);
	endDateField = document.getElementById('endDate' + reportType);

	startDate = startDateField.valueAsDate;
	endDate = endDateField.valueAsDate;

	differenceInMilliseconds = endDate - startDate;
	return differenceInMilliseconds / MILLISECONDS_A_DAY;
}

function initCustomDateRange(reportType) {
	startDateField = document.getElementById('startDate' + reportType);
	endDateField = document.getElementById('endDate' + reportType);

	toDate = new Date();
	endDateField.valueAsDate = toDate;

	fromDate = new Date();
	fromDate.setDate(toDate.getDate() - 30);
	startDateField.valueAsDate = fromDate;
}

function formatCurrency(amount) {
	formattedAmount = $.number(amount, decimalDigits, decimalPointType, thousandsPointType);
	return prefixCurrencySymbol + formattedAmount + suffixCurrencySymbol;
}

function getChartTitle(){
	if (period == "last_7_days") return "Tình hình kinh doanh trong 7 ngày";
	if (period == "last_28_days") return "Tình hình kinh doanh trong 28 ngày";
	if (period == "last_6_months") return "Tình hình kinh doanh trong 6 tháng";
	if (period == "last_year") return "Tình hình kinh doanh trong 1 năm";

	if (period == "customizedDate") return "Tùy chỉnh số ngày, tháng, năm báo cáo";
	return "";
}

function getDenominator(period, reportType) {
	if (period == "last_7_days") return 7;
	if (period == "last_28_days") return 28;
	if (period == "last_6_months") return 6;
	if (period == "last_year") return 12;
	if (period == "customizedDate") return calculateDays(reportType);

	return 7;
}

function setSalesAmount(period, reportType, labelTotalItems) {
	if (reportType === "_category" || reportType === "_product"){
		$("#textTotalGrossSales" + reportType).closest(".col-sm-2").hide();
		$("#textAvgGrossSales" + reportType).closest(".col-sm-2").hide();
		$(".col-sm-2:visible").addClass("col-sm-3").removeClass("col-sm-2");
		$("#textTotalNetSales" + reportType).text(formatCurrency(totalNetSales) + ' Đ');
		denominator = getDenominator(period, reportType);
		$("#textAvgNetSales" + reportType).text(formatCurrency(parseInt(totalNetSales / denominator)) + ' Đ');
		$("#labelTotalItems" + reportType).text(labelTotalItems);
		$("#textTotalItems" + reportType).text(totalItems + ' Đơn');
	}
	else
	{
		$("#textTotalGrossSales" + reportType).text(formatCurrency(totalGrossSales) + ' Đ');
		$("#textTotalNetSales" + reportType).text(formatCurrency(totalNetSales) + ' Đ');

		denominator = getDenominator(period, reportType);

		$("#textAvgGrossSales" + reportType).text(formatCurrency(parseInt(totalGrossSales / denominator)) + ' Đ');
		$("#textAvgNetSales" + reportType).text(formatCurrency(parseInt(totalNetSales / denominator)) + ' Đ');
		$("#labelTotalItems" + reportType).text(labelTotalItems);
		$("#textTotalItems" + reportType).text(totalItems + ' Đơn');
	}
}

function formatChartData(data, columnIndex1, columnIndex2) {
	var formatter = new google.visualization.NumberFormat({
		prefix: prefixCurrencySymbol,
		suffix: suffixCurrencySymbol,
		decimalSymbol: decimalPointType,
		groupingSymbol: thousandsPointType,
		fractionDigits: decimalDigits
	});

	formatter.format(data, columnIndex1);
	formatter.format(data, columnIndex2);
}