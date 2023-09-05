package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChucVu;

import java.util.Optional;

public interface ChucVuService {
    Optional<ChucVu> findByTenChucVu(String roleName);
}
