package senla.course.dao;

import senla.course.entitys.UserEntity;

public interface IUserDao {

    UserEntity findUserByLogin(String login);
}
