package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Entity.VatLieu;
import com.datn.dongho5s.Exception.ThuongHieuNotFoundException;
import com.datn.dongho5s.Exception.VatLieuNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VatLieuService {
    List<VatLieu> getAllVatLieu();
    public static final int MATERIALS_PER_PAGE = 4;

    public List<VatLieu> getAllPaginationVatLieu();
    public Page<VatLieu> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    public VatLieu save(VatLieu vatLieu);

    public VatLieu get(Integer id) throws VatLieuNotFoundException, Exception;

    public boolean checkUnique(Integer id, String ten);

    public void updateVatLieuEnabledStatus(Integer id, boolean enabled);
}
