package ba.valera.springBoot.dao;

import ba.valera.springBoot.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void removeUserById(long id) {
        try {
            if (getUserById(id) != null) {
                entityManager.remove(getUserById(id));
            }
        } catch (EntityNotFoundException e) {
            getUserById(id);

        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();

    }

    @Override
    public User getUserById(long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return  getUserById(id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User updateUser) {
        try {
            if (getUserById(updateUser.getId()) != null) {
                entityManager.merge(updateUser);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
        }

