package top.akte.base.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.StringTokenizer;


@Configuration
@MapperScan(basePackages = "top.akte.dao")
public class MyBatisConfig {

    /**
     * 申明系统默认数据源
     *
     * @return
     */
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.system")
    public DataSource primaryDataSource() {
        DruidDataSource dataSource =  new DruidDataSource();
        String connectionInitSqls = "SET NAMES utf8mb4";
        StringTokenizer tokenizer = new StringTokenizer(connectionInitSqls, ";");
        dataSource.setConnectionInitSqls(Collections.list(tokenizer));//重点设置该参数
        return dataSource;
    }

    /**
     * 配置默认sqlsessionFactory
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:META-INF/mapper/*.xml"));
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis-config.xml"));
        return sessionFactory.getObject();
    }

    /**
     * 配置sqlSessionTemplate
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 分布式数据源，对应多个数据源支持事物管理
     *
     * @return
     */
    @Bean
    public DataSourceTransactionManager chainedTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



}
