package com.datn.dongho5s.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "khachhang")
public class KhachHang implements UserDetails {
    @Id
    @Column(name = "id_khach_hang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKhachHang;

    @OneToMany( fetch = FetchType.EAGER,mappedBy = "khachHang", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<DiaChi> listDiaChi ;

    @Column(name = "ten_khach_hang")
    private String tenKhachHang;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "ngay_sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;

    @Column(name = "gioi_tinh")
    private Integer gioiTinh;

    @Column(name = "thoi_gian_tao_tai_khoan")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp thoiGianTaoTaiKhoan;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySua;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        return  authorities;
    }

    @Override
    public String getPassword() {
      return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public List<DiaChi> getListDiaChi() {
        return listDiaChi;
    }

    public void setListDiaChi(List<DiaChi> listDiaChi) {
        this.listDiaChi = listDiaChi;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Timestamp getThoiGianTaoTaiKhoan() {
        return thoiGianTaoTaiKhoan;
    }

    public void setThoiGianTaoTaiKhoan(Timestamp thoiGianTaoTaiKhoan) {
        this.thoiGianTaoTaiKhoan = thoiGianTaoTaiKhoan;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public KhachHang get() {
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
