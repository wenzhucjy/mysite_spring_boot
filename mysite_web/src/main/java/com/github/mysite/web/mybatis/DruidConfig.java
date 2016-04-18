package com.github.mysite.web.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * description: Druid数据源配置
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-16 11:55
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource", ignoreInvalidFields = true)
public class DruidConfig {

    /**
     * Logger for DruidConfig
     */
    private static final Logger LOG = LoggerFactory.getLogger(DruidConfig.class);

    private String              url;                                             // 连接数据库的url

    private String              username;                                        // 连接数据库的用户名

    private String              password;                                        // 连接数据库的密码

    private String              driverClassName;                                 // 根据url自动识别

    private int                 initialSize;                                     // 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时

    private int                 maxActive;                                       // 最大连接池数量

    private int                 minIdle;                                         // 最小连接池数量

    private int                 maxWait;                                         // 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁

    private long                timeBetweenEvictionRunsMillis;                   // 1)Destroy线程会检测连接的间隔时间，2)testWhileIdle的判断依据

    private long                minEvictableIdleTimeMillis;

    private String              validationQuery;                                 // 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用

    private boolean             testWhileIdle;                                   // 建议设置为true，申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效

    private boolean             testOnBorrow;                                    // 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能

    private boolean             testOnReturn;                                    // 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能

    private boolean             poolPreparedStatements;                          // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。5.5及以上版本有PSCache，建议开启

    private int                 maxPoolPreparedStatementPerConnectionSize;

    private String              filters;                                         // 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat
                                                                                  // 日志用的filter:log4j
                                                                                  // 防御sql注入的filter:wall

    public DruidConfig(){

    }

    public DruidConfig(String url, String username, String password, String driverClassName, int initialSize,
                       int maxActive, int minIdle, int maxWait, long timeBetweenEvictionRunsMillis,
                       long minEvictableIdleTimeMillis, String validationQuery, boolean testWhileIdle,
                       boolean testOnBorrow, boolean testOnReturn, boolean poolPreparedStatements,
                       int maxPoolPreparedStatementPerConnectionSize, String filters){
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.initialSize = initialSize;
        this.maxActive = maxActive;
        this.minIdle = minIdle;
        this.maxWait = maxWait;
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        this.validationQuery = validationQuery;
        this.testWhileIdle = testWhileIdle;
        this.testOnBorrow = testOnBorrow;
        this.testOnReturn = testOnReturn;
        this.poolPreparedStatements = poolPreparedStatements;
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
        this.filters = filters;
    }

    public DataSource mysqlDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setDriverClassName(this.driverClassName);
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxWait(this.maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestWhileIdle(this.testWhileIdle);
        druidDataSource.setTestOnBorrow(this.testOnBorrow);
        druidDataSource.setTestOnReturn(this.testOnReturn);
        druidDataSource.setPoolPreparedStatements(this.poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
        try {
            druidDataSource.setFilters(this.filters);
            druidDataSource.setFilters("config,wall,stat");
            druidDataSource.setUseGlobalDataSourceStat(true);
            Properties prop = new Properties();
            // java -cp druid-1.0.18.jar com.alibaba.druid.filter.config.ConfigTools you_password 生成加密后的密码
            // 配置数据源，提示Druid数据源需要对数据库密码进行解密，需配置 config.decrypt=true,config.decrypt.key=${publicKey}，并且设置filters="config..."
            prop.setProperty("config.decrypt", "true");
            prop.setProperty("config.decrypt.key","MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALxGD+7b5TFusE2q0/qy5YRcVjTWH9GVkM/1n3VM8rynbLSbhYRLgZW9imPgj2dXlQ+chpAc5qUB9QTSPaDZaRECAwEAAQ==");
            druidDataSource.setConnectProperties(prop);

            // Unknown system variable 'language' 找到问题，mysql-connector-java-5.1.36.jar 版本太高了
            druidDataSource.init();
        } catch (Exception e) {
            LOG.error("Druid initialize datasource fail , {}", e);
            throw new RuntimeException("load datasource error, dbProperties is :", e);
        }

        return druidDataSource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
}
