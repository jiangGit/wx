package top.akte.base.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.TimeUnit;


@Configuration
@Slf4j
public class RedisConnection {

    @Autowired
    AppSettings appSettings;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    /**
     * redis sentinel config
     * @return RedisConnectionFactory
     */

    public RedisConnectionFactory jedisConnectionFactory() {
        /*
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
        String master = appSettings.getSentinelMaster();
        String nodes = appSettings.getSentinelNodes();
        String[] nodesArray = nodes.split(",");
        sentinelConfig.master(master);
        for (int i=0; i < nodesArray.length; i++) {
            String[] node = nodesArray[i].split(":");
            RedisNode redisNode = new RedisNode(node[0], Integer.parseInt(node[1]));
            sentinelConfig.sentinel(redisNode);
        }
        JedisConnectionFactory jedisConnectionFactory =  new JedisConnectionFactory();
        jedisConnectionFactory.setDatabase(appSettings.getDatabase());
        jedisConnectionFactory.afterPropertiesSet();
        */
        JedisConnectionFactory jedisConnectionFactory =  new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(appSettings.getHost());
        jedisConnectionFactory.setPort(appSettings.getPort());
        jedisConnectionFactory.setDatabase(appSettings.getDatabase());
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }


    /**
     * redis pool config
     * @return JedisPoolConfig
     */
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(appSettings.getMaxIdle());
        jedisPoolConfig.setMaxIdle(appSettings.getMaxIdle());
        jedisPoolConfig.setMaxTotal(appSettings.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(appSettings.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(appSettings.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(appSettings.isTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000L);
        jedisPoolConfig.setNumTestsPerEvictionRun(50);
        return jedisPoolConfig;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(jedisConnectionFactory());

        template.setKeySerializer(new StringRedisSerializer());
        //定义value的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // template.setKeySerializer(redisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

/**
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor(taskExecutor);
        List<Topic> topicList = Lists.newLinkedList();
        //消息主题监听
        topicList.add(new PatternTopic(SpiderBusinessConstant.RedisTopicEnum.TRADEMARK_TASK.topic()));
        topicList.add(new PatternTopic(SpiderBusinessConstant.RedisTopicEnum.PATENT_TASK.topic()));
        container.addMessageListener(topicMessageListener(), topicList);
        container.afterPropertiesSet();
        log.info("消息主题监听 container");
        return container;
    }


    @Bean
    public TopicMessageListener topicMessageListener() {
        TopicMessageListener topicMessageListener = new TopicMessageListener();
        return topicMessageListener;
    }
 */
    public void set(String key, Object value) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value);
    }

    public void setEx(String key, Object value,long expireTime,TimeUnit timeUnit){
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key,value,expireTime, timeUnit);
    }

    public Object get(String key) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        return opsForValue.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
