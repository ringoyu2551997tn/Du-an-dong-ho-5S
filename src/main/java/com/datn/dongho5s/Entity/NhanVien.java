package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@Builder
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ho", length = 45,nullable = false)
    private String ho;

    @Column(name = "ten", length = 45,nullable = false)
    private String ten;

    @Column(name = "gioi_tinh")
    private Integer gioiTinh;

    @Column(name = "ngay_sinh")
    private String ngaySinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email",length = 128,nullable = false,unique = true)
    private String email;

    @Column(name = "mat_khau")
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "anh", length = 64)
    private String anh;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "nhanvien_chucvu",
            joinColumns = @JoinColumn(name="nhanvien_id"),
            inverseJoinColumns = @JoinColumn(name = "chucvu_id")
    )
    private Set<ChucVu> chucVu = new HashSet<>();

    public NhanVien() {
    }


    public NhanVien(String ho, String ten, String email, String matKhau) {
        this.ho = ho;
        this.ten = ten;
        this.email = email;
        this.password = matKhau;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public String getMatKhau() {
        return password;
    }

    public void setMatKhau(String matKhau) {
        this.password = matKhau;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return  authorities;
//    }

//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

//    public Set<ChucVu> getChucVu() {
//        return chucVu;
//    }
//
//    public void setChucVu(Set<ChucVu> chucVu) {
//        this.chucVu = chucVu;
//    }
//
//    public void addChucVu(ChucVu chucVu){
//        this.chucVu.add(chucVu);
//    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "id=" + id +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", email='" + email + '\'' +
//                ", chucVu=" + chucVu +
                '}';
    }

    @Transient
    public String getPhotoImagesPath(){
        if(id == null || anh == null) return "/images/default-user.png";

        return "/user-photos/" + this.id + "/" + this.anh;
    }

    public NhanVien get() {
        return this;
    }
}
