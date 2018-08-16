package top.akte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching //加上这个注解是的支持缓存注解
@SpringBootApplication(scanBasePackages={"top.akte"})
public class WxApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WxApplication.class);
    }

    //如果需要此方式启动 需要  注销掉pom文件中的  spring-boot-starter-tomcat  provided
    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class, args);
    }

}
