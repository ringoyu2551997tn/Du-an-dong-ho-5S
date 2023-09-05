package com.datn.dongho5s.GiaoHangNhanhService;

import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.BaseResponse;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.PhiVanChuyenResponse;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.ThemDonHangResponseGHN;
import com.datn.dongho5s.GiaoHangNhanhService.Request.PhiVanChuyenRequest;
import com.datn.dongho5s.GiaoHangNhanhService.Request.TaoDonHangRequestGHN;
import com.datn.dongho5s.Utils.PhuongThucThanhToan;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@Slf4j
public class DonHangAPI {

    private static final String FeeAPI = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private static final String CreateOrderAPI = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";

    public static Integer getFee(PhiVanChuyenRequest request) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(FeeAPI);
        httpPost.setHeader("Token", Constant.TOKEN);
        httpPost.setHeader("shopid", String.valueOf(Constant.ID_SHOP));
        httpPost.setHeader("Content-Type", Constant.CONTENT_TYPE);
        request.getTrungBinhCacCanh();
            try {
                StringBuilder body = new StringBuilder("{ \"to_district_id\":" + request.getIdQuanHuyen() + " ,");
                body.append(" \"to_ward_code\": \"" + request.getStringPhuongXa() + "\" ,");
                body.append(" \"service_type_id\":" + 2 + " ,");
                body.append(" \"height\":" + request.getTrungBinhCacCanh() + " ,");
                body.append(" \"length\":" + request.getTrungBinhCacCanh() + " ,");
                body.append(" \"width\":" + request.getTrungBinhCacCanh() + " ,");
                body.append(" \"weight\":" + request.getSoLuongSanPham()*Constant.TRONG_LUONG_SAN_PHAM + " }");
                System.out.println(body);
                StringEntity requestEntity = new StringEntity(body.toString());
                httpPost.setEntity(requestEntity);
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                ObjectMapper objectMapper = new ObjectMapper();
                BaseResponse<PhiVanChuyenResponse> responseObject = objectMapper.readValue(responseBody, new TypeReference<>() {
                });
                PhiVanChuyenResponse thongTinPhi = responseObject.getData();
                System.out.println(thongTinPhi.toString());
                return thongTinPhi.getTotal();
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
    }

    public static Integer createOrder(TaoDonHangRequestGHN request) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(CreateOrderAPI);
        httpPost.setHeader("Token", Constant.TOKEN);
        httpPost.setHeader("shopid", String.valueOf(Constant.ID_SHOP));
        httpPost.setHeader("Content-Type", Constant.CONTENT_TYPE);
        request.getTrungBinhCacCanh();
        try {
//            StringBuilder body = new StringBuilder("{ \"to_district_id\":" + request.getIdQuanHuyen() + " ,");
//            body.append(" \"to_ward_code\": \"" + request.getStringPhuongXa() + "\" ,");
//            body.append(" \"to_address\": \"" + request.getToAddress() + "\" ,");
//            body.append(" \"payment_type_id\":" + 2 + " ,");
//            body.append(" \"note\":" +"\""+ request.getNote() +"\""+ " ,");
//            body.append(" \"required_note\":" + "\""+ Constant.REQUIRED_NOTE +"\""+ " ,");
//            body.append(" \"to_name\":" +"\""+ request.getToName() +"\""+ " ,");
//            body.append(" \"to_phone\":" +"\""+ request.getToPhone() +"\""+ " ,");
//            body.append(" \"service_type_id\":" + 2 + " ,");
//            body.append(" \"height\":" + request.getTrungBinhCacCanh() + " ,");
//            body.append(" \"length\":" + request.getTrungBinhCacCanh() + " ,");
//            body.append(" \"width\":" + request.getTrungBinhCacCanh() + " ,");
//            body.append(" \"weight\":" + request.getSoLuongSanPham()*Constant.TRONG_LUONG_SAN_PHAM + " }");
//            body.append(" \"items\":" + request.getListItems() );
            JsonObject body = new JsonObject();
            body.addProperty("to_district_id",request.getIdQuanHuyen());
            body.addProperty("to_ward_code",request.getStringPhuongXa());
            body.addProperty("to_address",request.getToAddress());
            body.addProperty("note",request.getNote());
            body.addProperty("required_note",Constant.REQUIRED_NOTE);
            body.addProperty("to_name",request.getToName());
            body.addProperty("to_phone",request.getToPhone());
            body.addProperty("service_type_id",2);
            body.addProperty("height",request.getTrungBinhCacCanh());
            body.addProperty("length",request.getTrungBinhCacCanh());
            body.addProperty("width",request.getTrungBinhCacCanh());
            body.addProperty("weight",request.getSoLuongSanPham()*Constant.TRONG_LUONG_SAN_PHAM);

            if(request.getPhuongThuc()== PhuongThucThanhToan.TRA_SAU){
                body.addProperty("cod_amount",request.getThanhTien().intValue());
                body.addProperty("payment_type_id",2);
            }else if(request.getPhuongThuc()== PhuongThucThanhToan.VNPAY){
                body.addProperty("payment_type_id",1);
            }else {
                body.addProperty("payment_type_id",1);
            }
            JsonArray items = new JsonArray();
            request.getListItems().forEach(item->{
                JsonObject covertJO = new JsonObject();
                covertJO.addProperty("name",item.getName());
                covertJO.addProperty("quantity",item.getSoLuong());
                covertJO.addProperty("price", item.getGiaBan().intValue());
                covertJO.addProperty("id",item.getCtsp().getIdChiTietSanPham());
                items.add(covertJO);
            });
            body.add("items",items);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(body);
            log.info("Thông tin đơn hàng gửi lên Giao hàng nhanh {}", jsonString);
            StringEntity requestEntity = new StringEntity(body.toString());
            httpPost.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();

            //Check code
            Integer code = jsonObject.get("code").getAsInt();

            //Mapper
//            ObjectMapper objectMapper = new ObjectMapper();
//            BaseResponse<ThemDonHangResponseGHN> responseObject = objectMapper.readValue(responseBody, new TypeReference<>() {
//            });
//            ThemDonHangResponseGHN thongTinDonHang = responseObject.getData();
            log.info("Thông tin đơn hàng trả về của Giao hàng nhanh {}",responseBody);
            return code;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
