package com.statics.transactionstatistics.controller;

import com.statics.transactionstatistics.service.StatisticsService;
import com.statics.transactionstatistics.vo.StatisticResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @Transactional
    public StatisticResponseVO getStatisticsReportInLast60Seconds() {
        return statisticsService.buildStatisticsReportBy60Seconds();
    }

}
