package com.betfair.logistics.config;

import com.betfair.logistics.service.CompanyInfo;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActuatorConfig implements InfoContributor {

    private final CompanyInfo companyInfo;

    public ActuatorConfig(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> actuatorDetails = new HashMap<>();
        actuatorDetails.put("currentDate", companyInfo.getCurrentDateAsString());
        actuatorDetails.put("companyProfit", companyInfo.getCompanyProfit());
        builder.withDetails(actuatorDetails);
    }
}
