package com.datn.dongho5s.Repository;

import com.datn.dongho5s.Entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends PagingAndSortingRepository<NhanVien,Integer> {

    public NhanVien findByEmail(String email);

    public NhanVien findByTen(String ten);

    boolean existsByEmail(String email);

    boolean existsByTen(String ten);

    @Query("SELECT nv FROM NhanVien nv WHERE nv.email = :email")
    public  NhanVien getNhanVienByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("SELECT nv FROM NhanVien nv WHERE UPPER(CONCAT(nv.id, ' ', nv.email, ' ', nv.diaChi, ' ', nv.soDienThoai, ' ', nv.ho, ' ', nv.ten)) LIKE %?1%")
    public Page<NhanVien> findAll(String keyword, Pageable pageable);

    @Query("UPDATE NhanVien nv SET nv.enabled = ?2 WHERE nv.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}
