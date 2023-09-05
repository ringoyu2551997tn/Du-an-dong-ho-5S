package com.datn.dongho5s.Repository;



import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Entity.VatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VatLieuRepository extends JpaRepository<VatLieu,Integer> {
    @Query("UPDATE VatLieu vl SET vl.enabled = ?2 WHERE vl.idVatLieu = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT vl FROM VatLieu vl WHERE UPPER(CONCAT(vl.idVatLieu, ' ', vl.tenVatLieu, ' ', vl.moTaVatLieu)) LIKE %?1%")
    public Page<VatLieu> findAll(String keyword, Pageable pageable);

    public VatLieu findByTenVatLieu(String ten);

}
