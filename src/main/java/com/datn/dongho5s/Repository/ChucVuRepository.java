package com.datn.dongho5s.Repository;

import com.datn.dongho5s.Entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu,Integer> {

    Optional<ChucVu> findByTenChucVu(String roleName);
}
