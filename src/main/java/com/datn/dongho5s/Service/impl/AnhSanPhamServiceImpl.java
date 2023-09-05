package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.AnhSanPham;
import com.datn.dongho5s.Repository.AnhSanPhamRepository;
import com.datn.dongho5s.Service.AnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnhSanPhamServiceImpl implements AnhSanPhamService {
    @Autowired
    AnhSanPhamRepository anhSanPhamRepository;
    @Override
    public AnhSanPham save(AnhSanPham anhSanPham) {
        return anhSanPhamRepository.save(anhSanPham);
    }
}
