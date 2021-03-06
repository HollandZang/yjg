package com.holland.holland.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.holland.holland.pojo.Customer;
import com.holland.holland.pojo.User;
import com.holland.holland.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.redis.token-timeout:60}")
    private long tokenTimeout;
    @Value("${spring.redis.token-key-prefix:holland}")
    private String tokenKeyPrefix;

//    @PostConstruct
//    public void init() {
//        String fe = "fp:" + "tester" + "20220101000000" + "02";
//        String be = "fp:" + "tester" + "20220101000000" + "01";
//        String encodeFe = Base64.getEncoder().encodeToString(fe.getBytes());
//        String encodeBe = Base64.getEncoder().encodeToString(be.getBytes());
//
//        redisTemplate.opsForValue().set(encodeFe, JSON.toJSONString(new Customer()
//                .setId(1)
//                .setUser("17781671532")
//                .setName("tester")
//                .setPhone("17781671532")
//        ));
//        redisTemplate.opsForValue().set(encodeBe, JSON.toJSONString(new User()
//                .setId(1)
//                .setUser("tester")
//                .setName("tester")
//                .setRole("1")
//        ));
//
//        System.out.println("FE: " + encodeFe);
//        System.out.println("BE: " + encodeBe);
//    }

    public String setToken(String loginName, Object user, String from) {
        final String key = tokenKeyPrefix + loginName + DateUtil.getDateStr() + from;
        final String encode = Base64.getEncoder().encodeToString(key.getBytes());
        redisTemplate.opsForValue().set(encode, JSON.toJSONString(user), tokenTimeout, TimeUnit.MINUTES);
        return encode;
    }

    public JSONObject getFromToken(String token) {
        final Object o = redisTemplate.opsForValue().get(token);
        return o == null ? null : (JSONObject) JSON.parse((String) o);
    }

    public User getEmployeeFromToken(String token) {
        return (User) redisTemplate.opsForValue().get(token);
    }

    public Customer getCustomerFromToken(String token) {
        return (Customer) redisTemplate.opsForValue().get(token);
    }

    public void delToken(String token) {
        redisTemplate.delete(token);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        template.setHashValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        template.afterPropertiesSet();
        return template;
    }
}
