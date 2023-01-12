package truthordare.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class GameConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "pg.data-source")
    public DataSource pgDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean
//    public DataSource pgDataSource(
//            @Value("${pg.data-source.username}") String username,
//            @Value("${pg.data-source.password}") String password,
//            @Value("${pg.data-source.driverClassName}") String driverClassName,
//            @Value("${pg.data-source.jdbcUrl}") String jdbcUrl
//    ) {
//        return DataSourceBuilder.create()
//                .username(username)
//                .password(password)
//                .driverClassName(driverClassName)
//                .url(jdbcUrl)
//                .build();
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}