//package com.accenture.codingtest.springbootcodingtest.configuration;
//
//import com.accenture.codingtest.springbootcodingtest.entity.Project;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//public class RedisConfiguration {
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }
//
//    @Bean
//    RedisTemplate<String, Project> redisTemplate() {
//        RedisTemplate<String, Project> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        return redisTemplate;
//    }
//}