package com.datn.dongho5s.Repository;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc,Integer> {
    @Query("UPDATE DanhMuc dm SET dm.enabled = ?2 WHERE dm.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);


    @Query("SELECT dm FROM DanhMuc dm WHERE UPPER(CONCAT(dm.id, ' ', dm.ten)) LIKE %?1%")
    public Page<DanhMuc> findAll(String keyword, Pageable pageable);

    public DanhMuc findByTen(String ten);


}
