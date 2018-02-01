package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<SecKill> list = seckillService.getSeckillList();
        logger.info("secklist={}", list);
    }

    @Test
    public void getById() {
        long id = 1007L;
        SecKill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long id = 1007L;
        Exposer exposer1 = seckillService.exportSeckillUrl(id);
        logger.info("exposer1={}",exposer1);
    }

    @Test
    public void executeSeckill() {
        long id = 1009L;
        long userPhone = 13812345644L;
        Exposer exposer2 = seckillService.exportSeckillUrl(id);
        SeckillExecution execution = null;
        try {
            if (exposer2.isExposed()) {
                logger.info("isExposed is true");
                execution = seckillService.executeSeckill(
                        id, userPhone, exposer2.getMd5());
            } else {
                logger.info("isExposed is false");
                execution = seckillService.executeSeckill(id, userPhone, "123");
            }
        } catch (RepeatKillException e) {
            e.printStackTrace();
        } catch (SeckillCloseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeSeckillProcedure() {
        long id = 1009L;
        long phone = 1361234567;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService
                    .executeSeckillProcedure(id, phone, md5);
            logger.info(execution.getStateInfo());
        }
    }
}