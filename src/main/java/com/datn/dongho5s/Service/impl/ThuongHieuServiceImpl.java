package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Exception.ThuongHieuNotFoundException;
import com.datn.dongho5s.Repository.ThuongHieuRepository;
import com.datn.dongho5s.Service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ThuongHieuServiceImpl implements ThuongHieuService {

    @Autowired
    ThuongHieuRepository thuongHieuRepository;

    @Override
    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll(Sort.by("tenThuongHieu").ascending());
    }

    @Override
    public List<ThuongHieu> layDanhSachTenThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    @Override
    public Page<ThuongHieu> listByPage(int pageNumber,String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 ,BRANDS_PER_PAGE, sort);
        if (keyword != null){
            return thuongHieuRepository.findAll(keyword,pageable);
        }
        return thuongHieuRepository.findAll(pageable);

    }

    @Override
    public ThuongHieu save(ThuongHieu thuongHieu){
        return  thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public void updateThuongHieuEnabledStatus(Integer id, boolean enabled) {
        thuongHieuRepository.updateEnabledStatus(id,enabled);
    }
    @Override
    public ThuongHieu get(Integer id) throws ThuongHieuNotFoundException, Exception {
        try {
            return thuongHieuRepository.findById(id)
                    .orElseThrow(() -> new ThuongHieuNotFoundException("Không tìm thấy thương hiệu nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        ThuongHieu thuongHieuTheoTen = thuongHieuRepository.findByTenThuongHieu(ten);
        if (thuongHieuTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (thuongHieuTheoTen != null) {
                return false;
            }
        }else {
                if (thuongHieuTheoTen.getIdThuongHieu() != id) {
                    return false;
                }
            }
            return true;
        }
    }
