package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Entity.PasswordResetToken;
import com.datn.dongho5s.Exception.CustomException.BadRequestException;
import com.datn.dongho5s.Exception.ErrorResponse;
import com.datn.dongho5s.Exception.GenericResponse;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Repository.NhanVienRepository;
import com.datn.dongho5s.Repository.PasswordResetTokenRepository;
import com.datn.dongho5s.Request.ChangePassForgetRequest;
import com.datn.dongho5s.Request.ChangePassRequest;
import com.datn.dongho5s.Request.RegisterRequest;
import com.datn.dongho5s.Response.RegisterResponse;
import com.datn.dongho5s.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    NhanVienRepository nhanVienRepository;


    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    DiaChiRepository diaChiRepository;
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) throws Exception {
//        try {
            KhachHang findKHbyEmail = khachHangRepository.getKhachHangByEmail(registerRequest.getEmail());
            KhachHang khachHangBySdt = khachHangRepository.getKhachHangBySdt(registerRequest.getSoDienThoai());
            if(registerRequest.getIdTinhThanh() ==null || registerRequest.getIdQuanHuyen()== null || registerRequest.getIdPhuongXa() == null){
                throw new BadRequestException("Mời bạn nhập địa chỉ ");
            }
            if(registerRequest.getNgaySinh().after(new Date())){
                throw new BadRequestException("Ngày sinh sai rùi ");
            }
        if(registerRequest.getPassword().isEmpty()){
            throw new BadRequestException("Password không được trống ");
        }
        if (findKHbyEmail != null) {
                throw new BadRequestException("Email đã tồn tại ");
            }
        if (khachHangBySdt != null && khachHangBySdt.getPassword() == null) {
            khachHangBySdt.setEmail(registerRequest.getEmail());
            khachHangBySdt.setEnabled(true);
            khachHangBySdt.setGioiTinh(registerRequest.getGioiTinh());
            khachHangBySdt.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            khachHangBySdt.setTenKhachHang(registerRequest.getTenKhachHang());
            khachHangBySdt.setNgaySinh(registerRequest.getNgaySinh());
            khachHangBySdt.setNgaySua(new Date());
            khachHangBySdt.setThoiGianTaoTaiKhoan(new Timestamp(new Date().getTime()));
            KhachHang khachHang0 = khachHangRepository.save(khachHangBySdt);
            DiaChi diaChi = DiaChi.builder()
                    .diaChi(registerRequest.getDiaChi())
                    .idTinhThanh(registerRequest.getIdTinhThanh())
                    .idQuanHuyen(registerRequest.getIdQuanHuyen())
                    .idPhuongXa(registerRequest.getIdPhuongXa())
                    .ghiChu("")
                    .maBuuChinh(123)
                    .khachHang(khachHang0)
                    .trangThaiMacDinh(1)
                    .soDienThoai(registerRequest.getSoDienThoai())
                    .build();
            DiaChi diaChi1 = diaChiRepository.save(diaChi);
            return new ResponseEntity<>(khachHang0, HttpStatus.OK);
        } else if (khachHangBySdt != null) {
            throw new BadRequestException("Số điện thoại đã được đăng ký");
        } else {
                KhachHang khachHang = KhachHang.builder()
                        .idKhachHang(null)
                        .email(registerRequest.getEmail())
                        .enabled(true)
                        .gioiTinh(registerRequest.getGioiTinh())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .tenKhachHang(registerRequest.getTenKhachHang())
                        .ngaySinh(registerRequest.getNgaySinh())
                        .ngaySua(new Date())
                        .soDienThoai(registerRequest.getSoDienThoai())
                        .thoiGianTaoTaiKhoan(new Timestamp(new Date().getTime()))
                        .build();
                KhachHang khachHang1 = khachHangRepository.save(khachHang);

                DiaChi diaChi = DiaChi.builder()
                        .diaChi(registerRequest.getDiaChi())
                        .idTinhThanh(registerRequest.getIdTinhThanh())
                        .idQuanHuyen(registerRequest.getIdQuanHuyen())
                        .idPhuongXa(registerRequest.getIdPhuongXa())
                        .ghiChu("")
                        .maBuuChinh(123)
                        .khachHang(khachHang1)
                        .trangThaiMacDinh(1)
                        .soDienThoai(registerRequest.getSoDienThoai())
                        .build();
                DiaChi diaChi1 = diaChiRepository.save(diaChi);
                return new ResponseEntity<>(khachHang1, HttpStatus.OK);
            }
    }

    @Override
    public HashMap<Integer,String> getListTP(){
        return DiaChiCache.hashMapTinhThanh;
    }

    @Override
    public HashMap<Integer, String> getListQuan( Integer idTP) throws Exception {
        DiaChiAPI.callGetQuanHuyenAPI(idTP);
        return DiaChiCache.hashMapQuanHuyen.get(idTP);
    }

    @Override
    public HashMap<String, String> getListPhuong( Integer idQH) throws Exception {
        DiaChiAPI.callGetPhuongXaAPI(idQH);
        return DiaChiCache.hashMapPhuongXa.get(idQH);

    }


    @Override
    public ResponseEntity<?> changePass(Principal p ,ChangePassRequest changePassRequest) throws Exception {
        if (changePassRequest.getNewPass() == null){
            throw new BadRequestException("Mật khẩu không được để trống");
        }
        if (changePassRequest.getConfirmPass() == null){
            throw new BadRequestException("Mật khẩu không được để trống");
        }
        String email = p.getName();
        KhachHang user = khachHangRepository.getKhachHangByEmail(email);
        boolean f = passwordEncoder.matches(changePassRequest.getOldPass(), user.getPassword());
        if(f){
            if(!changePassRequest.getNewPass().equals(changePassRequest.getConfirmPass())){
              throw  new BadRequestException("Xác nhận mật khẩu không khớp");
            }else {
                user.setPassword(passwordEncoder.encode(changePassRequest.getNewPass()));
                 khachHangRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body( khachHangRepository.save(user));
            }
        }else {
            throw new BadRequestException("Mật khẩu không đúng");
        }
    }

    public Optional<KhachHang> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token) .getKhachHang());
    }

    @Override
    public void changeforgotPass(KhachHang khachHang, String pass) {
        khachHang.setPassword(passwordEncoder.encode(pass));
        khachHangRepository.save(khachHang);
    }

    public void createPasswordResetTokenForUser(KhachHang user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

}
