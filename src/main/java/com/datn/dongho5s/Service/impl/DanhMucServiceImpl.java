package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Exception.DanhMucNotFoundException;
import com.datn.dongho5s.Repository.DanhMucRepository;
import com.datn.dongho5s.Service.DanhmucService;
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
public class DanhMucServiceImpl implements DanhmucService {
    public static final int CATEGORIES_PER_PAGE = 4;
    @Autowired
    private DanhMucRepository repo;

    @Override
    public List<DanhMuc> listAll(){
        return (List<DanhMuc>) repo.findAll(Sort.by("ten").ascending());
    }

    @Override
    public Page<DanhMuc> listByPage(int pageNumber, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber -1, CATEGORIES_PER_PAGE, sort);

        if(keyword != null){
            return repo.findAll(keyword,pageable);
        }

        return repo.findAll(pageable);
    }

    @Override
    public void updateDanhMucEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id,enabled);
    }

    @Override
    public DanhMuc save(DanhMuc danhMuc){
        return repo.save(danhMuc);
    }

    @Override
    public DanhMuc get(Integer id) throws DanhMucNotFoundException, Exception {
        try {
            return repo.findById(id)
                    .orElseThrow(() -> new DanhMucNotFoundException("Không tìm thấy danh mục nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten){
        DanhMuc danhMucTheoTen = repo.findByTen(ten);
        if (danhMucTheoTen == null) return true;
        boolean isCreatingNew = (id == null);
        if(isCreatingNew){
            if(danhMucTheoTen != null){
                return false;
            }
        }else{
            if(danhMucTheoTen.getId() != id){
                return false;
            }
        }
        return true;
    }


}
