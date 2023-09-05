package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Exception.KhachHangNotFoundException;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Response.ThongTinCaNhanResponse;
import com.datn.dongho5s.Response.ThongTinToCheckoutResponse;
import com.datn.dongho5s.Service.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachHangRepository;

    private final DiaChiRepository diaChiRepository;
    @Autowired
    private KhachHangRepository khachHangRepo;
    @Override
    public KhachHang findKhachHangById(Integer id) {
        if (id == null) return null;
        return khachHangRepository.findById(id).get();
    }
    @Override
    public KhachHang get(Integer id) throws KhachHangNotFoundException, Exception {
        try {
            return khachHangRepository.findById(id)
                    .orElseThrow(() -> new KhachHangNotFoundException("Không tìm thấy khách hàng nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }
    @Override
    public KhachHang updateThongTinCaNhan(Integer idKhachHang,ThongTinCaNhanResponse thongTinCaNhanResponse) {
        KhachHang khachHangExist = this.findKhachHangById(idKhachHang);
        if (khachHangExist == null) {
            return null;
        }
        khachHangExist.setTenKhachHang(thongTinCaNhanResponse.getTenKhachHang());
        khachHangExist.setGioiTinh(thongTinCaNhanResponse.getGioiTinh());
        khachHangExist.setNgaySinh(thongTinCaNhanResponse.getNgaySinh());
        khachHangExist.setSoDienThoai(thongTinCaNhanResponse.getSoDienThoai());

        return khachHangRepository.save(khachHangExist);
    }

    @Override
    public ThongTinCaNhanResponse getThongTinCaNhanById(Integer id) {
        KhachHang khachHangExist = this.findKhachHangById(id);
        DiaChi diaChi = diaChiRepository.getDiaChiDefault(id);
        if (diaChi == null) return null;
        if (khachHangExist == null) return null;

        return ThongTinCaNhanResponse.builder()
                .id(khachHangExist.getIdKhachHang())
                .gioiTinh(khachHangExist.getGioiTinh())
                .diaChi(diaChi.getDiaChi())
                .ngaySinh(khachHangExist.getNgaySinh())
                .soDienThoai(khachHangExist.getSoDienThoai())
                .email(khachHangExist.getEmail())
                .tenKhachHang(khachHangExist.getTenKhachHang())
                .build();
    }

    @Override
    public ThongTinToCheckoutResponse getThongTinToCheckout(Integer id) {
        System.out.println(id);
        KhachHang khachHangExist = khachHangRepo.findById(id).get();

        if (khachHangExist == null) return null;

        return ThongTinToCheckoutResponse.builder()
                .id(khachHangExist.getIdKhachHang())
                .listDiaChi(khachHangExist.getListDiaChi())
                .soDienThoai(khachHangExist.getSoDienThoai())
                .tenKhachHang(khachHangExist.getTenKhachHang())
                .build();
    }

    @Override
    public void saveKhachHang(KhachHang khachHang) {
        khachHangRepo.save(khachHang);
    }

    @Override
    public List<KhachHang> getAllKhachHang() {
        return khachHangRepo.findAll();
    }

    @Override
    public List<KhachHang> getAllPaginationVatLieu() {
        return khachHangRepo.findAll(Sort.by("tenKhachHang").ascending());
    }


    @Override
    public Page<KhachHang> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , CUSTOMERS_PER_PAGE, sort);
        if (keyword != null){
            return khachHangRepo.findAll(keyword,pageable);
        }
        return khachHangRepo.findAll(pageable);
    }

    @Override
    public KhachHang findByPhoneNumber(String phoneNumber){
        return khachHangRepo.findBySoDienThoai(phoneNumber);
    }

    @Override
    public boolean checkUnique(Integer id, String email, String soDienThoai) {
        KhachHang khachHangTheoEmailSDT = khachHangRepository.findByEmailAndSoDienThoai(email, soDienThoai);
        if (khachHangTheoEmailSDT == null) return true;
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            if (khachHangTheoEmailSDT != null) {
                return false;
            }
        }else {
            if (khachHangTheoEmailSDT.getIdKhachHang() != id) {
                return false;
            }
        }
        return true;
    }

//    @Override
//    public void updateKhachHangEnabledStatus(Integer id, boolean enabled) {
//        khachHangRepository.updateEnabledStatus(id,enabled);
//    }
}
