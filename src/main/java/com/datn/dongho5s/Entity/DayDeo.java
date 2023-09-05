package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@Table(name = "daydeo")
public class DayDeo {
    @Id
    @Column(name = "id_day_deo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDayDeo;

    @Column(name = "ten_day_deo",length = 128, nullable = false, unique = true)
    private String tenDayDeo;

    @Column(name = "mo_ta_day_deo")
    private String moTaDayDeo;

    @Column(name = "chieu_dai")
    private Float chieuDai;

    @Column(name = "chat_lieu")
    private String chatLieu;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;

    public DayDeo() {
    }

    public DayDeo(Integer idDayDeo, String tenDayDeo, String moTaDayDeo, Float chieuDai, String chatLieu, boolean enabled) {
        this.idDayDeo = idDayDeo;
        this.tenDayDeo = tenDayDeo;
        this.moTaDayDeo = moTaDayDeo;
        this.chieuDai = chieuDai;
        this.chatLieu = chatLieu;
        this.enabled = enabled;
    }

    public Integer getIdDayDeo() {
        return idDayDeo;
    }

    public void setIdDayDeo(Integer idDayDeo) {
        this.idDayDeo = idDayDeo;
    }

    public String getTenDayDeo() {
        return tenDayDeo;
    }

    public void setTenDayDeo(String tenDayDeo) {
        this.tenDayDeo = tenDayDeo;
    }

    public String getMoTaDayDeo() {
        return moTaDayDeo;
    }

    public void setMoTaDayDeo(String moTaDayDeo) {
        this.moTaDayDeo = moTaDayDeo;
    }

    public Float getChieuDai() {
        return chieuDai;
    }

    public void setChieuDai(Float chieuDai) {
        this.chieuDai = chieuDai;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
