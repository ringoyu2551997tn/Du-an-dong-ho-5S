package com.datn.dongho5s.Entity;

import lombok.Builder;

import javax.persistence.*;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    private static final int EXPIRATION = 30;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = KhachHang.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_khach_hang")
    private KhachHang khachHang;


    private Date expiryDate;


    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(final String token) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public PasswordResetToken(final String token, final KhachHang khachHang) {
        super();

        this.token = token;
        this.khachHang = khachHang;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    //
    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setUser(final KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }


    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    //

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExpiryDate() == null) ? 0 : getExpiryDate().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getKhachHang() == null) ? 0 : getKhachHang().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PasswordResetToken other = (PasswordResetToken) obj;
        if (getExpiryDate() == null) {
            if (other.getExpiryDate() != null) {
                return false;
            }
        } else if (!getExpiryDate().equals(other.getExpiryDate())) {
            return false;
        }
        if (getToken() == null) {
            if (other.getToken() != null) {
                return false;
            }
        } else if (!getToken().equals(other.getToken())) {
            return false;
        }
        if (getKhachHang() == null) {
            if (other.getKhachHang() != null) {
                return false;
            }
        } else if (!getKhachHang().equals(other.getKhachHang())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]");
        return builder.toString();
    }
}
