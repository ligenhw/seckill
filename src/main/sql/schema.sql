CREATE DATABASE seckill;
use seckill;
<!-- 创建秒杀商品表 -->
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '数量',
`start_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
`end_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
`create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET='utf8' COMMENT '秒杀库存表';

INSERT INTO
 seckill(name, number, start_time, end_time)
VALUES
 ('1000元秒杀iphone7s', 100, '2018-01-11 00:00:00', '2018-11-11 00:00:00'),
 ('500元秒杀iphone7s', 200, '2018-01-11 00:00:00', '2018-01-12 00:00:00'),
 ('300元秒杀iphone7s', 300, '2018-10-11 00:00:00', '2018-10-12 00:00:00'),
 ('100元秒杀iphone7s', 400, '2018-01-11 00:00:00', '2018-01-12 00:00:00'),
 ('50元秒杀小米3', 500, '2018-01-01 00:00:00', '2019-01-01 00:00:00');

CREATE TABLE success_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL COMMENT '状态标识：-1：无效，0：成功，1：已付款...',
`create_time` timestamp NOT NULL COMMENT '创建时间',
PRIMARY KEY(seckill_id, user_phone),
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';
