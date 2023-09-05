package com.datn.dongho5s.Export;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HoaDonPdf {

    public void exportToPDF(
            HttpServletResponse response,
            List<HoaDonChiTiet> lst,
            DonHang donHang
    ) throws Exception {

        DiaChiAPI diaChiAPI = new DiaChiAPI();
        DiaChiCache diaChiCache = new DiaChiCache();

        // Tạo tài liệu PDF mới
        Document document = new Document();


        // Thiết lập tên file khi xuất ra
        response.setContentType("application/pdf;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"HD" + donHang.getIdDonHang() + ".pdf\"");
        // Tạo đối tượng PdfWriter để ghi dữ liệu vào tài liệu PDF

        PdfWriter.getInstance(document, response.getOutputStream());
        Font font = new Font(BaseFont.createFont("arial/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        font.setColor(220, 20, 60);
        font.setStyle(Font.BOLD);
        document.setPageSize(PageSize.A4);
        // Mở tài liệu PDF để bắt đầu viết
        document.open();

        // Thiết kế tài liệu PDF giống như đoạn mã HTML
        Paragraph header1 = new Paragraph("Đồng hồ 5S",new Font(font));
        header1.setAlignment(Element.ALIGN_CENTER);
        header1.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD));
        String diaChi = "";
        if (donHang.getDiaChi() != null){
            diaChi = donHang.getDiaChi() + ", " +
                    diaChiAPI.callGetPhuongXaAPI(donHang.getIdQuanHuyen()).get(donHang.getIdPhuongXa()) + ", " +
                    diaChiAPI.callGetQuanHuyenAPI(donHang.getIdTinhThanh()).get(donHang.getIdQuanHuyen()) + ", " +
                    diaChiCache.hashMapTinhThanh.get(donHang.getIdTinhThanh());
        }

            Paragraph paragraph1 = new Paragraph("CỬA HÀNG ĐỒNG HỒ                                           ĐẢM BẢO CHẤT LƯỢNG",
                new Font(font));
        paragraph1.setIndentationLeft(50);
        paragraph1.setIndentationRight(60);
        paragraph1.setAlignment(Element.ALIGN_LEFT);
        paragraph1.setSpacingAfter(0);


        Paragraph paragraph2 = new Paragraph("        ___ 5S ___                                                     uy tín quý hơn vàng", new Font(font));
        paragraph2.setSpacingBefore(5);
        paragraph2.setAlignment(Element.ALIGN_LEFT);
        paragraph2.setIndentationLeft(50);
        paragraph2.setIndentationRight(60);

        font.setStyle(Font.NORMAL);
        font.setSize(11.5f);
        Paragraph paragraph3 = new Paragraph("DC: Đại Mỗ - Nam Từ Liêm - Hà Nội \nSĐT: 0816.134.100 - 0355.728.316", new Font(font));
        paragraph3.setSpacingBefore(10);
        paragraph3.setAlignment(Element.ALIGN_LEFT);
        paragraph3.setIndentationLeft(55);
        paragraph3.setIndentationRight(55);

        font.setSize(10.5f);
        Paragraph paragraph5 = new Paragraph("Bán cho ông (bà): "+donHang.getKhachHang().getTenKhachHang() , new Font(font));
        paragraph5.setIndentationLeft(50);
        paragraph5.setIndentationRight(60);
        paragraph5.setAlignment(Element.ALIGN_LEFT);
        paragraph5.setSpacingAfter(0);

        Paragraph paragraph12 = new Paragraph("Mã Hóa Đơn: "+donHang.getMaDonHang(),new Font(font));
        paragraph12.setIndentationLeft(50);
        paragraph12.setIndentationRight(60);
        paragraph12.setAlignment(Element.ALIGN_LEFT);
        paragraph12.setSpacingAfter(0);

        Paragraph header2 = new Paragraph(diaChi,new Font(font));
        header2.setAlignment(Element.ALIGN_CENTER);
        header2.setFont(FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));

        Paragraph header3 = new Paragraph("Hóa đơn thanh toán",new Font(font));
        header3.setAlignment(Element.ALIGN_CENTER);
        paragraph5.setSpacingBefore(10);
        header3.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));

        float columnWidth[] = {40,13,23,24};

        PdfPTable table = new PdfPTable(columnWidth);
        table.setSpacingBefore(25);
        table.setSpacingAfter(25);

        PdfPCell cell1 = new PdfPCell(new Phrase("Tên sản phẩm", new Font(font)));
        paragraph5.setSpacingBefore(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell2 = new PdfPCell(new Phrase("Số lượng",new Font(font)));
        paragraph5.setSpacingBefore(10);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell3 = new PdfPCell(new Phrase("Đơn giá", new Font(font)));
        paragraph5.setSpacingBefore(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell4 = new PdfPCell(new Phrase("Thành tiền",new Font(font)));
        paragraph5.setSpacingBefore(10);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);

        // Lấy danh sách hóa đơn chi tiết từ model
        //List<HoaDonChiTiet> lstHDCT = (List<HoaDonChiTiet>) model.getAttribute("lstHDCT");
        NumberFormat currencyFormat02 = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        currencyFormat02.setMinimumFractionDigits(0);
        // Thêm dữ liệu vào bảng
        for (HoaDonChiTiet item : lst) {
            PdfPCell cell5 = new PdfPCell(new Phrase(item.getChiTietSanPham().getSanPham().getTenSanPham() +"\n Phân loại :" +item.getChiTietSanPham().getVatLieu().getTenVatLieu() + "," + item.getChiTietSanPham().getMauSac().getTenMauSac(), new Font(font)));
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell6 = new PdfPCell(new Phrase("x " +  item.getSoLuong(), new Font(font)));
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell6.setMinimumHeight(10);
            PdfPCell cell7 = new PdfPCell(new Phrase( currencyFormat02.format(item.getGiaBan() ), new Font(font)));
            cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell8 = new PdfPCell(new Phrase( currencyFormat02.format((item.getGiaBan() * item.getSoLuong())), new Font(font)));
            cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
        }


        // Đơn giá các sản phẩm
        Paragraph donGia = new Paragraph("Tổng tiền các sản phẩm: " + currencyFormat02.format((donHang.getTongTien())), new Font(font));
        donGia.setAlignment(Element.ALIGN_RIGHT);
        donGia.setIndentationLeft(50);
        donGia.setIndentationRight(50);
        donGia.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

        // phí vận chuyển
        Paragraph pvc = new Paragraph("Phí vận chuyển: " +  currencyFormat02.format((donHang.getPhiVanChuyen() == null ? 0 : donHang.getPhiVanChuyen())) , new Font(font));
        pvc.setAlignment(Element.ALIGN_RIGHT);
        pvc.setIndentationLeft(50);
        pvc.setIndentationRight(50);
        pvc.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

        // Tổng cộng tiền

        Paragraph tongCong = new Paragraph("Tổng tiền: " + (currencyFormat02.format(donHang.getTongTien() + (donHang.getPhiVanChuyen() == null ? 0 : donHang.getPhiVanChuyen()))) , new Font(font));
        tongCong.setAlignment(Element.ALIGN_RIGHT);
        tongCong.setIndentationLeft(50);
        tongCong.setIndentationRight(50);
        tongCong.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));


        //lưu ý
        font.setStyle(Font.BOLD);
        Paragraph paragraph6 = new Paragraph("LƯU Ý: ", new Font(font));
        paragraph6.setSpacingBefore(5);
        paragraph6.setAlignment(Element.ALIGN_LEFT);
        paragraph6.setIndentationLeft(55);
        paragraph6.setIndentationRight(55);
        //+
        font.setStyle(Font.NORMAL);
        Paragraph paragraph7 = new Paragraph("Thưa quý khách chúng tôi có giá ưu đãi cho khách hàng mua \n - Quý khách giữ lại giấy đảm bảo để tiện mua bán, đổi ", new Font(font));
        paragraph7.setSpacingBefore(5);
        paragraph7.setAlignment(Element.ALIGN_LEFT);
        paragraph7.setIndentationLeft(55);
        paragraph7.setIndentationRight(55);

        font.setStyle(Font.BOLD);
        Paragraph paragraph8 = new Paragraph("Rất hân hạnh được phục vụ quý khách ! ", new Font(font));
        paragraph8.setSpacingBefore(5);
        paragraph8.setAlignment(Element.ALIGN_LEFT);
        paragraph8.setIndentationLeft(55);
        paragraph8.setIndentationRight(55);

        font.setStyle(Font.NORMAL);
        Date date = new Date();// the date instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Paragraph paragraph9 = new Paragraph("Hà Nội, ngày " + new Date().getDate() + " tháng " + (calendar.get(Calendar.MONTH) + 1 ) + " năm " + calendar.get(Calendar.YEAR), new Font(font));
        paragraph9.setSpacingBefore(5);
        paragraph9.setAlignment(Element.ALIGN_RIGHT);
        paragraph9.setIndentationLeft(55);
        paragraph9.setIndentationRight(55);

        font.setStyle(Font.BOLD);
        Paragraph paragraph10 = new Paragraph("ĐẠI DIỆN CỬA HÀNG", new Font(font));
        paragraph10.setSpacingBefore(5);
        paragraph10.setAlignment(Element.ALIGN_RIGHT);
        paragraph10.setIndentationLeft(55);
        paragraph10.setIndentationRight(75);

        font.setStyle(Font.NORMAL);
        Paragraph paragraph11 = new Paragraph("5S", new Font(font));
        paragraph11.setSpacingBefore(5);
        paragraph11.setAlignment(Element.ALIGN_RIGHT);
        paragraph11.setIndentationLeft(55);
        paragraph11.setIndentationRight(115);
        // Thêm các phần tử vào tài liệu PDF
        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(header1);
        document.add(header2);
        document.add(header3);
        document.add(paragraph5);
        document.add(paragraph12);
        document.add(table);
        document.add(donGia);
        document.add(pvc);
        document.add(tongCong);
        document.add(paragraph6);
        document.add(paragraph7);
        document.add(paragraph8);
        document.add(paragraph9);
        document.add(paragraph10);
        document.add(paragraph11);
        // Đóng tài liệu PDF
        document.close();
    }
}
