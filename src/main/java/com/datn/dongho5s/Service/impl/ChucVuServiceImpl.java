package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.ChucVu;
import com.datn.dongho5s.Repository.ChucVuRepository;
import com.datn.dongho5s.Service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChucVuServiceImpl implements ChucVuService {
    @Autowired
    private ChucVuRepository chucVuRepository;
    @Override
    public Optional<ChucVu> findByTenChucVu(String roleName) {
        return chucVuRepository.findByTenChucVu(roleName);
    }
}
