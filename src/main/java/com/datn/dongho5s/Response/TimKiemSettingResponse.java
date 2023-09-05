package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.DayDeo;
import com.datn.dongho5s.Entity.KichCo;
import com.datn.dongho5s.Entity.MauSac;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Entity.VatLieu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class TimKiemSettingResponse {
    private List<DanhMuc> listDanhMuc;
    private List<DayDeo> listDayDeo;
    private List<KichCo> listKichCo;
    private List<MauSac> listMauSac;
    private List<ThuongHieu> listThuongHieu;
    private List<VatLieu> listVatLieu;
}
