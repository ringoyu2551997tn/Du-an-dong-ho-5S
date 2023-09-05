package com.datn.dongho5s.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TimKiemRequest{
    @JsonProperty("thuongHieuId")
    private List<Integer> thuongHieuId;
    @JsonProperty("danhMucId")
    private List<Integer> danhMucId;
    @JsonProperty("sizeId")
    private List<Integer> sizeId;
    @JsonProperty("mauSacId")
    private List<Integer> mauSacId;
    @JsonProperty("vatLieuId")
    private List<Integer> vatLieuId;
    @JsonProperty("dayDeoId")
    private List<Integer> dayDeoId;
    @JsonProperty("tenSanPham")
    private String tenSanPham;
}
