package top.akte.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "redis")
@Data
public class AppSettings {
    int database;
    int minIdle;
    int maxTotal;
    int maxIdle;
    long maxWaitMillis;
    boolean testOnBorrow;
    boolean testOnReturn;
    String sentinelMaster;
    String sentinelNodes;
    String host;
    int port;
}
