package eu.getsoftware.hotelico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@ActiveProfiles("test-jpa")
@EnableJpaRepositories(basePackages="eu.getsoftware.hotelico.model.repository")
@EntityScan(basePackages="eu.getsoftware.hotelico.model")
@DependsOn("liquibase") // This tells Spring to initialize Liquibase first
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // I want only real db!
public abstract class AbstractBaseJpaTest {

    // Define the PostgreSQL container as a static field
// It will be started once for the entire test class.
// Making it static ensures that it is shared across all test methods
// and is not restarted for each one.
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("eutest")
            .withUsername("eutest")
            .withPassword("test")
            .withInitScript("init-testcontainer.sql");

    // This method dynamically sets the properties for the datasource
// to match the running Testcontainers instance. Spring Boot will use
// these properties to connect to the database.
    @DynamicPropertySource
    static void setTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.liquibase.default-schema", () -> "eutest");
// If you're using a specific schema, you can add it here.
// registry.add("spring.jpa.hibernate.default_schema", () -> "your_schema_name");
// registry.add("spring.liquibase.default-schema", () -> "your_schema_name");
    }

    @TestConfiguration
    static class EmAlias {
        @PersistenceContext
        private EntityManager em;

        @Bean("postgres")
        EntityManager postgresAlias() { return em; }
    }
}