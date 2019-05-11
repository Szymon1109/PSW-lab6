package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
    public SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(model.User.class);
        configuration.addAnnotatedClass(model.Event.class);
        configuration.addAnnotatedClass(model.Zapis.class);
        StandardServiceRegistryBuilder registryBuilder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
        return sessionFactory;
    }
}
