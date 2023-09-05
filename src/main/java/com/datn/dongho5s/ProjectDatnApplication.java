package com.datn.dongho5s;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class ProjectDatnApplication {
    public static void main(String[] args) throws Exception {
        DiaChiAPI.callGetTinhThanhAPI();
//		Integer idTP = 207;
//		Integer idQH = 2118;
//		Integer idPX = 381301;,381308,381306
//		9704198526191432198
//		NGUYEN VAN A
//		07/15
//		123456
        SpringApplication.run(ProjectDatnApplication.class, args);
    }

}