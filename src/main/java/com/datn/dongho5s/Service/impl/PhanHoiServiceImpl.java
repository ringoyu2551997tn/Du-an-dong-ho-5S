package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.PhanHoi;
import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;
import com.datn.dongho5s.Repository.DonHangRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Repository.PhanHoiRepository;
import com.datn.dongho5s.Request.PhanHoiRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import com.datn.dongho5s.Response.PhanHoiResponse;
import com.datn.dongho5s.Service.PhanHoiService;
import com.datn.dongho5s.mapper.ChiTietGioHangMapping;
import com.datn.dongho5s.mapper.PhanHoiMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhanHoiServiceImpl implements PhanHoiService {

    @Autowired
    PhanHoiRepository phanHoiRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    DonHangRepository donHangRepository;

    @Autowired
    KhachHangRepository khachHangRepository;
    @Override
    public List<PhanHoiResponse> findAll(Integer idChiTietSanPham) {
        List<PhanHoi> phanHoiList = phanHoiRepository.findAll(idChiTietSanPham);
        List<PhanHoiResponse> responseList = phanHoiList.stream().map(PhanHoiMapping::mapEntitytoResponse).collect(Collectors.toList());
        responseList.sort((o1,o2) -> o2.getNgayTao().compareTo(o1.getNgayTao()));
        return responseList;
    }

    @Override
    public boolean checkPhanHoi(Integer idKhachHang, Integer idChiTietSanPham) {
        List<HoaDonChiTiet> donHangList = donHangRepository.findHDDonHang(idKhachHang,idChiTietSanPham);
        if (donHangList.isEmpty()){
            return true;
        }
        Long countPH =phanHoiRepository.countPhanHoi(idKhachHang,idChiTietSanPham);
        Long countHDCT =phanHoiRepository.countHDCT(idKhachHang,idChiTietSanPham);
        if(donHangList != null){
            if(countPH < countHDCT){
                return false;
            }else{
               return true;
            }
//            System.out.println("sai rồi");
//            Optional<PhanHoi> phanHoi = phanHoiRepository.findPhanHoi(idKhachHang,idChiTietSanPham);
//            if (phanHoi.isPresent()){
//                return  true;
//            }else {
//                return false ;
//            }
        }else {
            return true;
        }
    }

    @Override
    public PhanHoiResponse addPhanHoi(PhanHoiRequest phanHoiRequest) {
        KhachHang khachHang = khachHangRepository.findById(phanHoiRequest.getIdKhachHang()).get();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(phanHoiRequest.getIdChiTietSanPham()).get();
        PhanHoi phanHoi =  PhanHoi.builder()
                .idPhanHoi(null)
                .chiTietSanPham(chiTietSanPham)
                .danhGia(phanHoiRequest.getDanhGia())
                .ghiChu(null)
                .noiDungPhanHoi(phanHoiRequest.getNoiDungPhanHoi())
                .khachHang(khachHang)
                .ngaySua(null)
                .thoiGianPhanHoi(new Timestamp(new Date().getTime()))
                .trangThaiPhanHoi(0)
                .ngayTao(new Date())
                .build();

        PhanHoiResponse phanHoiResponse = PhanHoiMapping.mapEntitytoResponse(phanHoiRepository.save(phanHoi));
        return phanHoiResponse;
    }

    @Override
    public Long countPH(Integer idKhachHang, Integer idChiTietSanPham) {
        return phanHoiRepository.countPhanHoi(idKhachHang,idChiTietSanPham);
    }

    @Override
    public Long countHDCT(Integer idKhachHang, Integer idChiTietSanPham) {
        return phanHoiRepository.countHDCT(idKhachHang,idChiTietSanPham);
    }


}
