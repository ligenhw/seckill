package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private JedisPool jedisPool;

    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public SecKill getSeckill(long seckillId) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:"+seckillId;
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes != null) {
                SecKill secKill = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
                return secKill;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public String putSeckill(SecKill seckill) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:"+seckill.getSeckillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            int timeout = 60 * 60;
            String result = jedis.setex(key.getBytes(), timeout, bytes);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
