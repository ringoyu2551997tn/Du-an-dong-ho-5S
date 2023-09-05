package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.KichCo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo,Integer> {
    @Query("UPDATE KichCo kc SET kc.enabled = ?2 WHERE kc.idKichCo = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT kc FROM KichCo kc WHERE UPPER(CONCAT(kc.idKichCo, ' ', kc.tenKichCo, ' ', kc.ngayTaoKichCo,' ', kc.moTaKichCo)) LIKE %?1%")
    public Page<KichCo> findAll(String keyword, Pageable pageable);

    public KichCo findByTenKichCo(String ten);
}
