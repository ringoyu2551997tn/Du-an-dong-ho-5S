package com.datn.dongho5s.Service;

import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Response.GiohangResponse;

public interface GioHangService {
    GiohangResponse addGioHang(GioHangRequest gioHangRequest);

    GiohangResponse findGioHang(Integer idKhachHang);
}
