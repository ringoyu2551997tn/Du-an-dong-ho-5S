package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham,Integer> {
}
