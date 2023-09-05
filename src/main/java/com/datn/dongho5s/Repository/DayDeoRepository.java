package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.DayDeo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DayDeoRepository extends JpaRepository<DayDeo,Integer> {
    @Query("UPDATE DayDeo  dd SET dd.enabled = ?2 WHERE dd.idDayDeo = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT dd FROM DayDeo dd WHERE UPPER(CONCAT(dd.idDayDeo,' ', dd.tenDayDeo,' ', dd.moTaDayDeo, ' ', dd.chatLieu, ' ', dd.chieuDai))LIKE %?1% ")
    public Page<DayDeo> findAll(String keyword, Pageable pageable);

    public DayDeo findByTenDayDeo(String tenDayDeo);

}
