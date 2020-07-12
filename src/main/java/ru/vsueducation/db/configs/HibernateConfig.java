package ru.vsueducation.db.configs;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Set;

public class HibernateConfig {
    private static SessionFactory sessionFactory = null;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return  sessionFactory;
        }
        Configuration configuration = new Configuration().configure();
        Reflections reflections = new Reflections("ru.vsueducation.db.models");
        try {
            Set<Class<?>> importantClasses = reflections.getTypesAnnotatedWith(Entity.class, true);
            for (Class<?> clazz : importantClasses) {
                configuration.addAnnotatedClass(clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration
                .buildSessionFactory(builder.build());
        sessionFactory.openSession();
        return sessionFactory;
    }
}
