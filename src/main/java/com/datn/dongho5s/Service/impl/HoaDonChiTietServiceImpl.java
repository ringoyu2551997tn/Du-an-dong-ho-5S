package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;
import com.datn.dongho5s.Repository.HoaDonChiTietRepository;
import com.datn.dongho5s.Repository.SeriRepository;
import com.datn.dongho5s.Request.HoaDonChiTietRequest;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    DonHangService donHangService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    SeriRepository seriRepository;

    @Override
    public HoaDonChiTiet save(HoaDonChiTiet hdct) {
        return hoaDonChiTietRepository.save(hdct);
    }

    @Override
    public List<HoaDonChiTiet> convertToListHoaDonChiTiet(List<HoaDonChiTietRequest> list, Integer idDonHang) {
        List<HoaDonChiTiet> result = new ArrayList<>();

        list.forEach(item -> {

            Integer chietKhau = null;
            ChiTietSanPham ctsp = chiTietSanPhamService.getChiTietSanPhamById(item.getIdChiTietSanPham());
            if(ctsp.getKhuyenMai() == null || ctsp.getKhuyenMai().isEnabled()== false){
                chietKhau = null;
            }else{
                chietKhau = ctsp.getKhuyenMai().getChietKhau();
            }
            HoaDonChiTiet hdct = HoaDonChiTiet.builder()
                    .donHang(donHangService.getById(idDonHang))
                    .chiTietSanPham(ctsp)
                    .soLuong(item.getSoLuong())
                    .chietKhau(chietKhau)
                    .build();

            if(chietKhau== null){
                hdct.setGiaBan(ctsp.getGiaSanPham());
            }else{
                hdct.setGiaBan(ctsp.getGiaSanPham() - ctsp.getGiaSanPham()*chietKhau/100);
            }
            result.add(hdct);
        });
        return result;
    }

    @Override
    public List<HoaDonChiTiet> saveAll(List<HoaDonChiTiet> listHDCT) {
        hoaDonChiTietRepository.saveAll(listHDCT);
        return null;
    }

    @Override
    public Double getTongGia(List<HoaDonChiTietRequest> list) {
        Double result = 0D;
        for (int i = 0; i < list.size(); i++) {
            Double giaBan = 0.0;
            ChiTietSanPham ctsp = chiTietSanPhamRepository.findByIdChiTietSanPham(list.get(i).getIdChiTietSanPham());
            if( ctsp.getKhuyenMai()!= null && ctsp.getKhuyenMai().isEnabled()){
                giaBan = ctsp.getGiaSanPham()-ctsp.getGiaSanPham()*ctsp.getKhuyenMai().getChietKhau()/100;
            }else{
                giaBan = ctsp.getGiaSanPham();
            }
            result += list.get(i).getSoLuong() * giaBan;
        }
        return result;
    }

    @Override
    public List<HoaDonChiTiet> getByIdDonHang(int id) {
        return hoaDonChiTietRepository.findHDCTBYIdDonHang(id);
    }
  
    @Override
    public List<HoaDonChiTiet> getByHoaDonId(DonHang donHang) {
        return hoaDonChiTietRepository.findByDonHang(donHang);
    }

    @Override
    public List<HoaDonChiTiet> getHDCTByMaDonHang(String maDonHang) {
        List<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepository.findByMaDonHang(maDonHang);
        return hoaDonChiTietPage;
    }

    @Override
    public void themSoLuongSanPham(
            int soLuong,
            ChiTietSanPham chiTietSanPham,
            DonHang donHang
    ) {
        int existIdHCT = -1;
        for (HoaDonChiTiet hoaDonChiTiet : donHang.getListHoaDonChiTiet()){
            if (hoaDonChiTiet.getChiTietSanPham().getIdChiTietSanPham() == chiTietSanPham.getIdChiTietSanPham()){
                existIdHCT = hoaDonChiTiet.getIdHoaDonChiTiet();
                break;
            }
        }

        //if not exist
        if (existIdHCT==-1){
            // step 1: save quantity to hdct
            HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.save(HoaDonChiTiet
                    .builder()
                    .chiTietSanPham(chiTietSanPham)
                    .donHang(donHang)
//                    .giaBan(chiTietSanPham.getGiaSanPham())
                    .soLuong(soLuong)
                    .chietKhau((chiTietSanPham.getKhuyenMai() == null || chiTietSanPham.getKhuyenMai().isEnabled() == false) ? null : chiTietSanPham.getKhuyenMai().getChietKhau())
                    .build());

            if(chiTietSanPham.getKhuyenMai() == null || chiTietSanPham.getKhuyenMai().isEnabled() == false){
                hoaDonChiTiet.setGiaBan(chiTietSanPham.getGiaSanPham());
            }else{
                hoaDonChiTiet.setGiaBan(chiTietSanPham.getGiaSanPham() - chiTietSanPham.getGiaSanPham()* hoaDonChiTiet.getChietKhau()/100);
            }
            List<Integer> listSeri = seriRepository.getListSeri(soLuong,chiTietSanPham.getIdChiTietSanPham());
            // step 2: update status seri is 3
            seriRepository.themSoLuongAdmin(hoaDonChiTiet.getIdHoaDonChiTiet(),listSeri);
        } else{
            // else ctsp exist -> update quantity by idHDCT
            hoaDonChiTietRepository.updateSoLuongSanPham(soLuong,existIdHCT);
            List<Integer> listSeri = seriRepository.getListSeri(soLuong,chiTietSanPham.getIdChiTietSanPham());
                    // step 2: update status seri is 3
            seriRepository.themSoLuongAdmin(existIdHCT,listSeri);
        }
    }

    @Override
    public void xoaHDCT(
            HoaDonChiTiet hoaDonChiTiet
    ){
        seriRepository.xoaSoLuongSanPham(hoaDonChiTiet.getIdHoaDonChiTiet());
        // xoa HDCT
        hoaDonChiTietRepository.xoaHDCT(hoaDonChiTiet.getIdHoaDonChiTiet());
    }

    @Override
    public HoaDonChiTiet findHoaDonChiTietById(
            int id
    ){
        return hoaDonChiTietRepository.findByIdHoaDonChiTiet(id);
    }

    @Override
    public void xoaHDCTByIdDonHang(DonHang donHang) {
        List<HoaDonChiTiet> listHDCT = hoaDonChiTietRepository.findByDonHang(donHang);
        hoaDonChiTietRepository.deleteAll(listHDCT);
    }
}
