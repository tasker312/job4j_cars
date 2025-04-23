package ru.job4j.cars.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HQLUserRepository implements UserRepository {

    private final SessionFactory sessionFactory;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            session.getTransaction().rollback();
        }
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createMutationQuery("UPDATE User SET login = :login, password = :password WHERE id = :id")
                    .setParameter("login", user.getLogin())
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            session.getTransaction().rollback();
        }
        session.close();
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createMutationQuery("DELETE FROM User WHERE id = :id")
                    .setParameter("id", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            session.getTransaction().rollback();
        }
        session.close();
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var users = session.createSelectionQuery("FROM User", User.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var user = session.createSelectionQuery("FROM User WHERE id = :id", User.class)
                .setParameter("id", userId)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(user);
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var users = session.createSelectionQuery("FROM User WHERE login LIKE :key", User.class)
                .setParameter("key", "%" + key + "%")
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var user = session.createSelectionQuery("FROM User WHERE login = :login", User.class)
                .setParameter("login", login)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(user);
    }

}
