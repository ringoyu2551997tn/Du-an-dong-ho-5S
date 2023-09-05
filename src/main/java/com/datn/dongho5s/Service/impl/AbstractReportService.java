package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.ReportItem;
import com.datn.dongho5s.Entity.ReportType;
import org.springframework.data.domain.Page;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractReportService {
	public DateFormat dateFormatter;

	public static final int ORDERS_PER_PAGE = 4;

	public List<ReportItem> getReportDataLast7Days(ReportType reportType) {
		return getReportDataLastXDays(7, reportType);
	}

	public List<ReportItem> getReportDataLast28Days(ReportType reportType) {
		return getReportDataLastXDays(28, reportType);
	}

	protected List<ReportItem> getReportDataLastXDays(int days, ReportType reportType) {
		Date endTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -(days - 1));
		Date startTime = cal.getTime();

		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);

		dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		return getReportDataByDateRangeInternal(startTime, endTime, reportType);
	}

	public List<ReportItem> getReportDataLast6Months(ReportType reportType) {
		return getReportDataLastXMonths(6, reportType);
	}

	public List<ReportItem> getReportDataLastYear(ReportType reportType) {
		return getReportDataLastXMonths(12, reportType);
	}

	protected List<ReportItem> getReportDataLastXMonths(int months, ReportType reportType) {
		Date endTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -(months - 1));
		Date startTime = cal.getTime();

		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);

		dateFormatter = new SimpleDateFormat("yyyy-MM");

		return getReportDataByDateRangeInternal(startTime, endTime, reportType);
	}

	public List<ReportItem> getReportDataByDateRange(Date startTime, Date endTime, ReportType reportType) {
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return getReportDataByDateRangeInternal(startTime, endTime, reportType);
	}

	protected abstract List<ReportItem> getReportDataByDateRangeInternal(
			Date startDate, Date endDate, ReportType reportType);

	//    public List<ReportItem> getReportDataLastXYears(int years){
	//        Date endTime = new Date();
	//        Calendar cal = Calendar.getInstance();
	//        cal.add(Calendar.YEAR, -(years -1));
	//        Date startTime = cal.getTime();
	//        System.out.println("Start time: " + startTime);
	//        System.out.println("End time: " + endTime);
	//        dateFormater = new SimpleDateFormat("yyyy");
	//        return getReportDataByDateRangeR(startTime, endTime, "years");
	//    }
//	public abstract List<DonHang> getAllDonHang();
//
//	public abstract List<DonHang> getAllPaginationDonHang();
//
//	public abstract Page<DonHang> listByPage(int pageNumber, String sortField, String sortDir, String keyword);
}
