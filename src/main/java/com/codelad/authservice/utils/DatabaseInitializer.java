package com.codelad.authservice.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private EntityManager entityManager;

    public void createInnerSequence() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS users_inner_seq START WITH 1 INCREMENT BY 1";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
        } catch (Exception ignored) {}
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createInnerSequence();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void resetInnerSequence() {
        String sql = "ALTER SEQUENCE IF EXISTS users_inner_seq RESTART";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
        } catch (Exception ignored) {}
    }

}
