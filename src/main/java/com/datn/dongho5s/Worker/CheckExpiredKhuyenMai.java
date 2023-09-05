package com.datn.dongho5s.Worker;

import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Service.KhuyenMaiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CheckExpiredKhuyenMai {

    @Autowired
    KhuyenMaiService khuyenMaiService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deactiveExpiredKhuyenMai (){
        List<KhuyenMai> listKhuyenMai = khuyenMaiService.getExpiredKhuyenMai();
        log.info("Số khuyến mại hết hạn : {}", listKhuyenMai.size());
        listKhuyenMai.forEach(item->{
            item.setEnabled(false);
            khuyenMaiService.save(item);
        });
    }
}
