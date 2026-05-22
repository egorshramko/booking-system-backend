package io.github.egorshramko.booking.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource) //источник данных
                .baselineOnMigrate(true) //создание baseline при первом запуске
                .baselineVersion("1") //версия baseline
                .validateOnMigrate(true) //валидация миграций
                .load();
    }

}
