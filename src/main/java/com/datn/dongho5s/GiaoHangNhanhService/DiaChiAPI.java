package com.datn.dongho5s.GiaoHangNhanhService;

import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.BaseListResponse;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.PhuongXaResponse;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.QuanHuyenResponse;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.TinhThanhResponse;
import com.datn.dongho5s.Cache.DiaChiCache;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class DiaChiAPI {
    private static final String apiTinhThanh = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
    private static final String apiQuanHuyen = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
    private static final String apiPhuongXa = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id";



    public static void callGetTinhThanhAPI() throws Exception {
        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(apiTinhThanh);
            httpGet.setHeader("Token", Constant.TOKEN);
            httpGet.setHeader("Content-Type", Constant.CONTENT_TYPE);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            ObjectMapper objectMapper = new ObjectMapper();
            BaseListResponse<TinhThanhResponse> responseObject = objectMapper.readValue(responseBody, new TypeReference<>() {
            });
            List<TinhThanhResponse> listTinhThanh = responseObject.getData();
            for (TinhThanhResponse data : listTinhThanh) {
                DiaChiCache.hashMapTinhThanh.put(data.getProvinceID(), data.getProvinceName());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static HashMap<Integer,String> callGetQuanHuyenAPI(Integer idTinhThanh) throws Exception {
        if(DiaChiCache.hashMapQuanHuyen.containsKey(idTinhThanh)){
            return DiaChiCache.hashMapQuanHuyen.get(idTinhThanh);
        }

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(apiQuanHuyen);
        httpPost.setHeader("Token", Constant.TOKEN);
        httpPost.setHeader("Content-Type", Constant.CONTENT_TYPE);
            try {
                String body = "{ \"province_id\":" + idTinhThanh + " }";
                StringEntity requestEntity = new StringEntity(body);
                httpPost.setEntity(requestEntity);
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                ObjectMapper objectMapper = new ObjectMapper();
                BaseListResponse<QuanHuyenResponse> responseObject = objectMapper.readValue(responseBody, new TypeReference<>() {
                });
                List<QuanHuyenResponse> listQuanHuyen = responseObject.getData();
                HashMap<Integer, String> hashMapQuanHuyenByTP = new HashMap<>();
                for (QuanHuyenResponse data : listQuanHuyen) {
                    hashMapQuanHuyenByTP.put(data.getDistrictID(), data.getDistrictName());
                }
                DiaChiCache.hashMapQuanHuyen.put(idTinhThanh, hashMapQuanHuyenByTP);
            } catch (Exception e) {
                System.out.println(e);
            }
        return DiaChiCache.hashMapQuanHuyen.get(idTinhThanh);
    }

    public static HashMap<String,String> callGetPhuongXaAPI(Integer idQuanHuyen) throws Exception {
        if(DiaChiCache.hashMapPhuongXa.containsKey(idQuanHuyen)){
            return DiaChiCache.hashMapPhuongXa.get(idQuanHuyen);
        }

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(apiPhuongXa);
        httpPost.setHeader("Token", Constant.TOKEN);
        httpPost.setHeader("Content-Type", Constant.CONTENT_TYPE);

        try {
            String body = "{\"district_id\":" + idQuanHuyen + "}";
            StringEntity requestEntity = new StringEntity(body);
            httpPost.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            ObjectMapper objectMapper = new ObjectMapper();
            BaseListResponse<PhuongXaResponse> responseObject = objectMapper.readValue(responseBody, new TypeReference<>() {
            });
            List<PhuongXaResponse> listPhuongXa = responseObject.getData();
            HashMap<String, String> hashMapPhuongXaByQH = new HashMap<>();
            for (PhuongXaResponse data : listPhuongXa) {
                hashMapPhuongXaByQH.put(data.getWardCode(), data.getWardName());
            }
            DiaChiCache.hashMapPhuongXa.put(idQuanHuyen, hashMapPhuongXaByQH);
        } catch (Exception e) {
            System.out.println(e);
        }
        return DiaChiCache.hashMapPhuongXa.get(idQuanHuyen);
    }
}
