package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.CuaHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuaHangRepository extends JpaRepository<CuaHang,Integer> {
}
