package com.github.mustard;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.eclipse.persistence.config.PersistenceUnitProperties.NON_JTA_DATASOURCE;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory factory = connectDB();
        EntityManager entityManager = factory.createEntityManager();
        setupData(entityManager);

        // This query fails with a NPE
        entityManager.createQuery("select e from Employee e left join Job j on e.job = j");
    }

    private static EntityManagerFactory connectDB() {
        var props = ImmutableMap.<String, Object>builder()
                .put(NON_JTA_DATASOURCE, DataSource.ds)
                .build();
        return Persistence.createEntityManagerFactory("TestUnit", props);
    }

    private static void setupData(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Job job = new Job();
        job.setId(1L);
        job.setName("Programmer");
        entityManager.persist(job);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Brian");
        employee.setJob(job);
        entityManager.persist(employee);

        entityManager.getTransaction().commit();

        List<Employee> employees = entityManager.createQuery("select e from Employee as e", Employee.class)
                .getResultList();

        for (Employee e : employees) {
            System.out.println("Employee " + e.getName() + " has job " + e.getJob().getName());
        }
    }

    public static class DataSource {

        private static HikariConfig config = new HikariConfig();
        private static HikariDataSource ds;

        static {
            config.setJdbcUrl("jdbc:h2:mem:testDB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/schema.sql'");
            config.setUsername("user");
            config.setPassword("pass");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            ds = new HikariDataSource(config);
        }

        private DataSource() {
        }

        public static Connection getConnection() throws SQLException {
            return ds.getConnection();
        }
    }

}
