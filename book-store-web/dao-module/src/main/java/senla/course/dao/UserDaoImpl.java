package senla.course.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import senla.course.entitys.UserEntity;

@Repository
public class UserDaoImpl extends HibernateAbstractDao<UserEntity, Integer> implements IUserDao {

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, UserEntity.class);
    }

    @Override
    public UserEntity findUserByLogin(String login) {

        String hqlFindUserByLogin = "SELECT u FROM UserEntity u WHERE u.login=:login";
        return sessionFactory.getCurrentSession().createQuery(hqlFindUserByLogin, UserEntity.class).
                setParameter("login", login).getResultList().stream().findFirst().orElse(null);
    }

}
