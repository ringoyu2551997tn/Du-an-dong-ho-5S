package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DayDeo;
import com.datn.dongho5s.Exception.DayDeoNotFoundException;
import com.datn.dongho5s.Repository.DayDeoRepository;
import com.datn.dongho5s.Service.DayDeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DayDeoServiceImpl implements DayDeoService {

    @Autowired
    DayDeoRepository dayDeoRepository;

    @Override
    public List<DayDeo> getAllDayDeo() {
        return dayDeoRepository.findAll(Sort.by("tenDayDeo").ascending());
    }

    @Override
    public Page<DayDeo> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber -1 , STRAPS_PER_PAGE,sort);
        if(keyword != null){
            return dayDeoRepository.findAll(keyword,pageable);
        }
        return dayDeoRepository.findAll(pageable);
    }

    @Override
    public DayDeo save(DayDeo dayDeo) {
        return dayDeoRepository.save(dayDeo);
    }

    @Override
    public DayDeo get(Integer id) throws DayDeoNotFoundException, Exception {
        try{
            return dayDeoRepository.findById(id)
                    .orElseThrow(()-> new DayDeoNotFoundException("Không tìm thấy dây đeo nào theo ID: " + id));
        }catch (Exception ex){
            throw new Exception((ex.getMessage()));
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        DayDeo dayDeoTheoTen = dayDeoRepository.findByTenDayDeo(ten);
        if (dayDeoTheoTen == null) return true;
        boolean isCreatingNew = (id == null);

        if(isCreatingNew){
            if(dayDeoTheoTen != null){
                return false;
            }
        }else{
            if(dayDeoTheoTen.getIdDayDeo() != id){
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateDayDeoEnabledStatus(Integer id, boolean enabled) {
        dayDeoRepository.updateEnabledStatus(id,enabled);
    }


}
