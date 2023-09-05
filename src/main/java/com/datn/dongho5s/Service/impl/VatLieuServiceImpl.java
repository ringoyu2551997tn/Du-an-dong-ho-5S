package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.VatLieu;
import com.datn.dongho5s.Exception.VatLieuNotFoundException;
import com.datn.dongho5s.Repository.VatLieuRepository;
import com.datn.dongho5s.Service.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VatLieuServiceImpl implements VatLieuService {
    @Autowired
    VatLieuRepository vatLieuRepository;
    @Override
    public List<VatLieu> getAllVatLieu() {
        return vatLieuRepository.findAll();
    }

    @Override
    public List<VatLieu> getAllPaginationVatLieu() {
        return vatLieuRepository.findAll(Sort.by("tenVatLieu").ascending());
    }


    @Override
    public Page<VatLieu> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return vatLieuRepository.findAll(keyword,pageable);
        }
        return vatLieuRepository.findAll(pageable);

    }

    @Override
    public VatLieu save(VatLieu vatLieu) {
        return  vatLieuRepository.save(vatLieu);
    }

    @Override
    public VatLieu get(Integer id) throws VatLieuNotFoundException, Exception {
        try {
            return vatLieuRepository.findById(id)
                    .orElseThrow(() -> new VatLieuNotFoundException("Không tìm thấy vật liệu nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        VatLieu vatLieuTheoTen = vatLieuRepository.findByTenVatLieu(ten);
        if (vatLieuTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (vatLieuTheoTen != null) {
                return false;
            }
        }else {
            if (vatLieuTheoTen.getIdVatLieu() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateVatLieuEnabledStatus(Integer id, boolean enabled) {
        vatLieuRepository.updateEnabledStatus(id,enabled);
    }
}
