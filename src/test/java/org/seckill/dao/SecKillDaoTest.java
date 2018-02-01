package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void queryById() {
        long id = 1007L;
        SecKill secKill = secKillDao.queryById(id);
        System.out.println(secKill.getName());
        System.out.println(secKill);
    }

    @Test
    public void reduceNumber() {
        long id = 1003;
        int count = secKillDao.reduceNumber(id,new Date());
        System.out.println(count);
    }

    @Test
    public void queryAll() {
        List<SecKill> secKills = secKillDao.queryAll(0,100);
        for (SecKill seckill :
                secKills) {
            System.out.println(seckill);
        }
    }
}