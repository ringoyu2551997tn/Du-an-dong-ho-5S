package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DayDeo;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Exception.DayDeoNotFoundException;
import com.datn.dongho5s.Exception.ThuongHieuNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DayDeoService {

    public static final int STRAPS_PER_PAGE = 4;

    List<DayDeo> getAllDayDeo();

    public Page<DayDeo> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    public DayDeo save(DayDeo dayDeo);

    public DayDeo get(Integer id) throws DayDeoNotFoundException, Exception;

    public boolean checkUnique(Integer id, String ten);

    public void updateDayDeoEnabledStatus(Integer id, boolean enabled);

}
