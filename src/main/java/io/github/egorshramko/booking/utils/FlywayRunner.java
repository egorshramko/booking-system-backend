package io.github.egorshramko.booking.utils;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.MigrateResult;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FlywayRunner implements CommandLineRunner {

    private final Flyway flyway;

    public FlywayRunner(Flyway flyway) {
        this.flyway = flyway;
    }

    @Override
    @NullMarked
    public void run(String... args) throws Exception {

        log.info("Flyway migration start");

        MigrateResult migrationsApplied = flyway.migrate();
        log.info("Applied {} migrations", migrationsApplied.migrationsExecuted);

        log.info("Applied migrations:");
        for (MigrationInfo info : flyway.info().applied()) {
            log.info("\t{} - {}", info.getVersion(), info.getDescription());
        }

    }
}
