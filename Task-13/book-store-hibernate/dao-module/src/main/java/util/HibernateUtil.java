package util;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory;
    private static Session session;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            log.error("error connection data base");
            throw new ExceptionInInitializerError();
        }
    }

    public static Session getCurrentSession() {
        if (session != null && session.isOpen()) {
            return session;
        }

        session = sessionFactory.openSession();
        return session;
    }
}
