package io.github.egorshramko.booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.github.egorshramko.booking.repository")
public class JpaConfig {
}
