package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        long id = 1000L;
        long phoneNumber = 13812345677L;
        int result = successKilledDao.insertSuccessKilled(id, phoneNumber);
        System.out.println("insert count = " + result);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1000L;
        long phoneNumber = 13812345677L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id,
                phoneNumber);
        System.out.println(successKilled);
    }
}