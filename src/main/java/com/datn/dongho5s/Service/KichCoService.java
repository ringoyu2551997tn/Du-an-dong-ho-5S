package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.KichCo;
import com.datn.dongho5s.Exception.KichCoNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KichCoService {
    List<KichCo> getAllKichCo();

    public static final int SIZES_PER_PAGE = 4;

    public List<KichCo> getAllPaginationKichCo();
    public Page<KichCo> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    public KichCo save(KichCo KichCo);

    public KichCo get(Integer id) throws KichCoNotFoundException, Exception;

    public boolean checkUnique(Integer id, String ten);

    public void updateKichCoEnabledStatus(Integer id, boolean enabled);
}
