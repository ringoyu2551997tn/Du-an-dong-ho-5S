package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.ReportItem;
import com.datn.dongho5s.Entity.ReportType;
import com.datn.dongho5s.Repository.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HoaDonChiTietReportServiceImpl extends AbstractReportService {

	@Autowired private HoaDonChiTietRepository repo;
	
	@Override
	protected List<ReportItem> getReportDataByDateRangeInternal(
			Date startDate, Date endDate, ReportType reportType) {
		List<HoaDonChiTiet> listHoaDonChiTiets = new ArrayList<>();

		if (reportType.equals(ReportType.CATEGORY)) {
			List<HoaDonChiTiet> combinedList1 = repo.findWithCategoryAndTimeBetween(startDate, endDate, 3);
			List<HoaDonChiTiet> combinedList2 = repo.findWithCategoryAndTimeBetween(startDate, endDate, 5);
			listHoaDonChiTiets.addAll(combinedList1);
			listHoaDonChiTiets.addAll(combinedList2);
		} else if (reportType.equals(ReportType.PRODUCT)) {
			List<HoaDonChiTiet> combinedList1 = repo.findWithProductAndTimeBetween(startDate, endDate, 3);
			List<HoaDonChiTiet> combinedList2 = repo.findWithProductAndTimeBetween(startDate, endDate, 5);
			listHoaDonChiTiets.addAll(combinedList1);
			listHoaDonChiTiets.addAll(combinedList2);
		} else if (reportType.equals(ReportType.ORDERDETAIL)) {
			List<HoaDonChiTiet> combinedList1 = repo.findWithOrderDetailAndTimeBetween(startDate, endDate, 3);
			List<HoaDonChiTiet> combinedList2 = repo.findWithOrderDetailAndTimeBetween(startDate, endDate, 5);
			listHoaDonChiTiets.addAll(combinedList1);
			listHoaDonChiTiets.addAll(combinedList2);
		}

		//printRawData(listHoaDonChiTiets);

		Set<String> identifiers = new HashSet<>();
		List<ReportItem> listReportItems = new ArrayList<>();
		for (HoaDonChiTiet detail : listHoaDonChiTiets) {
			String identifier = "";

			if (reportType.equals(ReportType.CATEGORY)) {
				identifier = detail.getChiTietSanPham().getSanPham().getDanhMuc().getTen();
			} else if (reportType.equals(ReportType.PRODUCT)) {
				identifier = detail.getChiTietSanPham().getSanPham().getTenSanPham();
			} else if (reportType.equals(ReportType.ORDERDETAIL)) {
				identifier = String.valueOf(detail.getIdHoaDonChiTiet());
			}

			if (!identifiers.contains(identifier)) {
				// Add a new ReportItem for each unique identifier
				listReportItems.add(new ReportItem(identifier));
				identifiers.add(identifier);
			}
		}

		// Now perform the data processing
		for (HoaDonChiTiet detail : listHoaDonChiTiets) {
			String identifier = "";

			if (reportType.equals(ReportType.CATEGORY)) {
				identifier = detail.getChiTietSanPham().getSanPham().getDanhMuc().getTen();
			} else if (reportType.equals(ReportType.PRODUCT)) {
				identifier = detail.getChiTietSanPham().getSanPham().getTenSanPham();
			} else if (reportType.equals(ReportType.ORDERDETAIL)) {
				identifier = String.valueOf(detail.getIdHoaDonChiTiet());
			}
			Integer soLuong = repo.countHD(detail.getDonHang().getIdDonHang());
			ReportItem reportItem = new ReportItem(identifier);
			double netSales = detail.getGiaBan() * detail.getSoLuong();
			double grossSales = 0.0;
			if(detail.getDonHang().getPhiVanChuyen() == null){
				 grossSales = netSales ;
			}else{
				 grossSales = netSales + detail.getDonHang().getPhiVanChuyen();
			}

			int itemIndex = listReportItems.indexOf(reportItem);

			if (itemIndex >= 0) {
				reportItem = listReportItems.get(itemIndex);
				reportItem.addGrossSales(grossSales);
				reportItem.addNetSales(netSales);
				reportItem.increaseProductsCount(detail.getSoLuong());
			} else {
				listReportItems.add(new ReportItem(identifier, grossSales, netSales, detail.getSoLuong()));
			}
		}

		// Other print statements or code
		//printRawData();
		//printReportData();
		//printReportData(listReportItems);

		return listReportItems;
	}

//	@Override
//	public List<DonHang> getAllDonHang() {
//		return null;
//	}
//
//	@Override
//	public List<DonHang> getAllPaginationDonHang() {
//		return null;
//	}
//
//	@Override
//	public Page<DonHang> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
//		return null;
//	}

	private void printReportData(List<ReportItem> listReportItems) {
		for (ReportItem item : listReportItems) {
			System.out.printf("%-20s, %.3f, %.3f, %d \n",
					item.getIdentifier(), item.getGrossSales(), item.getNetSales(), item.getOrdersCount());
		}
	}

	private void printRawData(List<HoaDonChiTiet> listHoaDonChiTiets) {
		for (HoaDonChiTiet detail : listHoaDonChiTiets) {
			System.out.printf("%d, %-20s, %.3f, %.3f \n",
					detail.getSoLuong(), detail.getChiTietSanPham().getSanPham().getTenSanPham().substring(0, 20),
					(detail.getGiaBan()*detail.getSoLuong()), detail.getDonHang().getPhiVanChuyen());
		}
	}

}
