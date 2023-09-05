package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.*;
import com.datn.dongho5s.Exception.NhanVienNotFoundException;
import com.datn.dongho5s.Exception.SanPhamNotFountException;
import com.datn.dongho5s.Repository.DanhMucRepository;
import com.datn.dongho5s.Repository.SanPhamRepository;
import com.datn.dongho5s.Repository.ThuongHieuRepository;
import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.ChiTietSanPhamResponse;
import com.datn.dongho5s.Response.SanPhamDetailResponse;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Service.SanPhamService;
import com.datn.dongho5s.mapper.ChiTietSanPhamMapping;
import com.datn.dongho5s.mapper.SanPhamMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements SanPhamService {


    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    DanhMucRepository danhMucRepository;

    @Autowired
    ThuongHieuRepository thuongHieuRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<TimKiemResponse> getSanPhamByCondition(TimKiemRequest timKiemRequest) {
        HashMap<String,Object> values = new HashMap<>();
        StringBuilder query = new StringBuilder("SELECT sp FROM SanPham sp ");
        query.append("INNER JOIN ThuongHieu th ON sp.thuongHieu.idThuongHieu = th.idThuongHieu ");
        query.append("INNER JOIN DanhMuc dm ON sp.danhMuc.id = dm.id ");
        query.append("INNER JOIN ChiTietSanPham ctsp ON sp.idSanPham = ctsp.sanPham.idSanPham ");
        query.append("INNER JOIN DayDeo dd ON ctsp.dayDeo.idDayDeo = dd.idDayDeo ");
        query.append("INNER JOIN KichCo kc ON ctsp.kichCo.idKichCo = kc.idKichCo ");
        query.append("INNER JOIN VatLieu vl ON ctsp.vatLieu.idVatLieu = vl.idVatLieu ");
        query.append("INNER JOIN MauSac ms ON ctsp.mauSac.idMauSac = ms.idMauSac ");
        query.append("WHERE true = true ");
        if (timKiemRequest.getThuongHieuId() != null && timKiemRequest.getThuongHieuId().size() != 0) {
            query.append("AND sp.thuongHieu.idThuongHieu IN :ths ");
            values.put("ths",timKiemRequest.getThuongHieuId());
        }
        if (timKiemRequest.getDanhMucId() != null && timKiemRequest.getDanhMucId().size() != 0) {
            query.append("AND sp.danhMuc.id IN :dms ");
            values.put("dms",timKiemRequest.getDanhMucId());
        }
        if (timKiemRequest.getDayDeoId() != null && timKiemRequest.getDayDeoId().size() != 0) {
            query.append("AND ctsp.dayDeo.idDayDeo IN :dds ");
            values.put("dds",timKiemRequest.getDayDeoId());
        }
        if (timKiemRequest.getVatLieuId() != null && timKiemRequest.getVatLieuId().size() != 0) {
            query.append("AND ctsp.vatLieu.idVatLieu IN :vls ");
            values.put("vls",timKiemRequest.getVatLieuId());
        }
        if (timKiemRequest.getSizeId() != null && timKiemRequest.getSizeId().size() != 0) {
            query.append("AND ctsp.kichCo.idKichCo IN :kcs ");
            values.put("kcs",timKiemRequest.getSizeId());
        }
        if (timKiemRequest.getMauSacId() != null && timKiemRequest.getMauSacId().size() != 0) {
            query.append("AND ctsp.mauSac.idMauSac IN :mss ");
            values.put("mss",timKiemRequest.getMauSacId());
        }
        if (timKiemRequest.getTenSanPham() != null && !timKiemRequest.getTenSanPham().trim().isBlank()) {
            query.append("AND sp.tenSanPham like :ten ");
            values.put("ten", "%"+timKiemRequest.getTenSanPham()+"%");
        }
        Query queryCreated = entityManager.createQuery(query.toString(), SanPham.class);
        values.forEach((key,value)->{
            queryCreated.setParameter(key,value);
        });
        System.out.println(queryCreated.toString());
//        List<SanPham> listSanPham = sanPhamRepository.getListSanPhamByCondition(timKiemRequest.getThuongHieuId(),
//                timKiemRequest.getDanhMucId(),
//                timKiemRequest.getDayDeoId(),
//                timKiemRequest.getSizeId(),
//                timKiemRequest.getVatLieuId(),
//                timKiemRequest.getMauSacId(),
//                timKiemRequest.getTenSanPham());
        List<SanPham> listSanPham = queryCreated.getResultList();
        Set<TimKiemResponse> result = new HashSet<>();
        listSanPham.forEach(sanPham -> result.add(toTimKiemResponse(sanPham)));
        return result;
    }

    private TimKiemResponse toTimKiemResponse(SanPham sp) {
        TimKiemResponse result = new TimKiemResponse();
        result.setSanPhamID(sp.getIdSanPham());
        result.setTenSanPham(sp.getTenSanPham());
//        result.setGiaSanPham(sp.getGiaSanPham());
        result.setListAnhSanPham(sp.getListAnhSanPham());
        result.setListChiTietSanPham(sp.getListChiTietSanPham());
        return result;
    }

    @Override
    public List<SanPhamDetailResponse> getSPnew() {
        List<SanPham> listSanPham = sanPhamRepository.getSPnew();
        List<SanPhamDetailResponse> responseList = listSanPham.stream().map(SanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<SanPhamDetailResponse> getSPFeature() {
        List<SanPham> listSanPham = sanPhamRepository.findAll();
        List<SanPhamDetailResponse> responseList = listSanPham.stream().map(SanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<SanPhamDetailResponse> getSPchay() {
        List<SanPham> listSanPham = sanPhamRepository.getSPchay();
        List<SanPhamDetailResponse> responseList = listSanPham.stream().map(SanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;

    }

    @Override
    public SanPhamDetailResponse getDetailSanPhamById(Integer sanPhamId) {
        SanPham sanPham = sanPhamRepository.findById(sanPhamId).get();
        return toSanPhamRepository(sanPham);
    }

    private SanPhamDetailResponse toSanPhamRepository(SanPham sp) {
        return SanPhamDetailResponse.builder()
                .idSanPham(sp.getIdSanPham())
                .listAnhSanPham(sp.getListAnhSanPham())
                .moTaSanPham(sp.getMoTaSanPham())
                .tenSanPham(sp.getTenSanPham())
                .maSanPham(sp.getMaSanPham())
                .danhMuc(sp.getDanhMuc())
                .listChiTietSanPham(sp.getListChiTietSanPham())
                .thuongHieu(sp.getThuongHieu())
                .trangThai(sp.getTrangThai())
                .build();
    }

    public List<ChiTietSanPhamResponse> getSPchayKM(Integer idChiTietSanPham) {
        List<ChiTietSanPham> chiTietSanPhamList = new ArrayList<>();
        List<ChiTietSanPham> chiTietSanPhams = sanPhamRepository.getCTSP(idChiTietSanPham);
        Date currentDate = new Date();
        for (ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            if (chiTietSanPham.getKhuyenMai() == null || chiTietSanPham.getKhuyenMai().isEnabled() == false
                    || chiTietSanPham.getKhuyenMai().getNgayKetThuc().before(currentDate)
                    || chiTietSanPham.getKhuyenMai().getNgayBatDau().after(currentDate)) {
                chiTietSanPhamList.add(chiTietSanPham);
            } else {
                chiTietSanPham.setGiaSanPham(chiTietSanPham.getGiaSanPham() - chiTietSanPham.getGiaSanPham() * chiTietSanPham.getKhuyenMai().getChietKhau() / 100);
                chiTietSanPhamList.add(chiTietSanPham);
            }
        }
        return chiTietSanPhamList.stream().map(ChiTietSanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());

    }

//    @Override
//    public Page<SanPham> getPageSanPham(int pageNumber) {
//        Page<SanPham> sanPhams = sanPhamRepository.getPageSanPham(PageRequest.of(pageNumber - 1, SANPHAM_PAGE));
//        return sanPhams;
//    }

    @Override
    public List<SanPham> listAll(){
        return sanPhamRepository.findAll(Sort.by("tenSanPham").ascending());

    }



    @Override
    public Page<SanPham> listByPage(int pageNumber,String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 ,PRODUCT_PER_PAGE, sort);
        if (keyword != null){
            return sanPhamRepository.findAll(keyword,pageable);
        }
        return sanPhamRepository.findAll(pageable);

    }

    @Override
    public List<SanPham> getSPCungTH(ThuongHieu thuongHieu) {
        return sanPhamRepository.findByThuongHieu(thuongHieu);
    }

    @Override
    public SanPham save(SanPham sanPham){
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public boolean checkUniqueTenAndMa(String ten, String ma) {
        SanPham sanPhamTheoTen = sanPhamRepository.findByTenSanPham(ten);
        SanPham sanPhamTheoMa = sanPhamRepository.findByMaSanPham(ma);

        if (sanPhamTheoTen == null && sanPhamTheoMa == null) {
            return true; // Tên và mã đều không bị trùng
        }

        return false; // Tên hoặc mã đã bị trùng
    }

    @Override
    public boolean checkUniqueTenMaId(String ten, String ma, Integer id) {
        SanPham sanPhamTheoTen = sanPhamRepository.findByTenSanPham(ten);
        SanPham sanPhamTheoMa = sanPhamRepository.findByMaSanPham(ma);

        if (sanPhamTheoTen == null && sanPhamTheoMa == null) {
            return true; // Tên và mã đều không bị trùng
        }

        if (id != null) {
            SanPham existingSanPham = sanPhamRepository.findById(id).orElse(null);
            if (existingSanPham != null) {
                if (sanPhamTheoTen != null && !sanPhamTheoTen.getIdSanPham().equals(id)) {
                    return false; // Tên trùng nhưng không phải sản phẩm hiện tại
                }
                if (sanPhamTheoMa != null && !sanPhamTheoMa.getIdSanPham().equals(id)) {
                    return false; // Mã trùng nhưng không phải sản phẩm hiện tại
                }
            }
        }

        return true; // Trường hợp còn lại
    }



    @Override
    public SanPham get(Integer id) throws SanPhamNotFountException {
        try{
            return sanPhamRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new SanPhamNotFountException("không tìm thấy sản phẩm có id" + id);
        }
    }


}
