package com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhuongXaResponse {
    @JsonProperty("DistrictID")
    private Integer districtID;
    @JsonProperty("WardCode")
    private String wardCode;
    @JsonProperty("WardName")
    private String wardName;
}
