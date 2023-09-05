package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.KichCo;
import com.datn.dongho5s.Entity.KichCo;
import com.datn.dongho5s.Exception.KichCoNotFoundException;
import com.datn.dongho5s.Repository.KichCoRepository;
import com.datn.dongho5s.Service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KichCoServiceImpl implements KichCoService {
    @Autowired
    KichCoRepository kichCoRepository;
    @Override
    public List<KichCo> getAllKichCo() {
        return kichCoRepository.findAll();
    }
    @Override
    public List<KichCo> getAllPaginationKichCo() {
        return kichCoRepository.findAll(Sort.by("tenKichCo").ascending());
    }


    @Override
    public Page<KichCo> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , SIZES_PER_PAGE, sort);
        if (keyword != null){
            return kichCoRepository.findAll(keyword,pageable);
        }
        return kichCoRepository.findAll(pageable);

    }

    @Override
    public KichCo save(KichCo KichCo) {
        return  kichCoRepository.save(KichCo);
    }

    @Override
    public KichCo get(Integer id) throws KichCoNotFoundException, Exception {
        try {
            return kichCoRepository.findById(id)
                    .orElseThrow(() -> new KichCoNotFoundException("Không tìm thấy Kích Cỡ nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        KichCo KichCoTheoTen = kichCoRepository.findByTenKichCo(ten);
        if (KichCoTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (KichCoTheoTen != null) {
                return false;
            }
        }else {
            if (KichCoTheoTen.getIdKichCo() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateKichCoEnabledStatus(Integer id, boolean enabled) {
        kichCoRepository.updateEnabledStatus(id,enabled);
    }
}
