package cz.muni.fi.dao;

import cz.muni.fi.entity.User;
import cz.muni.fi.enums.Role;
import cz.muni.fi.helpers.Guard;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


/**
 * User DAO interface implementation.
 *
 * @author Martin Kostka
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager em;


    @Override
    public void addUser(User user) {
        validateUser(user);
        Guard.requireNull(user.getId(), "User id is not null");

        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        validateUser(user);
        Guard.requireNotNull(user.getId(), "User id should not be null");

        em.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        Guard.requireNotNull(user, User.class.getName());
        User delUser = findUserById(user.getId());
        Guard.requireNotNull(delUser, "Trying to delete nonexistent user");

        em.remove(delUser);
    }

    @Override
    public List<User> findAllUsers() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public List<User> findAllAstronauts() {
        return em.createQuery("select u from User u where u.role = :role", User.class)
                .setParameter("role", Role.ASTRONAUT).getResultList();
    }

    @Override
    public User findUserById(Long id) {
        Guard.requireNotNull(id, "User id is null");

        try {
            return em.createQuery("select u from User u fetch all properties where u.id = :id", User.class)
                    .setParameter("id", id).getSingleResult();
        }
        catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public List<User> findAllAvailableAstronauts() {
        return em.createQuery("select u from User u where u.mission is null and u.role=:role", User.class)
                .setParameter("role", Role.ASTRONAUT).getResultList();
    }

    @Override
    public User findUserByEmail(String email) {
        Guard.requireNotNull(email, "User email is null");

        if(!email.matches(".+@.+\\....?")){
            throw new IllegalArgumentException("Email has wrong format");
        }
        try {
            return em.createQuery("select u from User u fetch all properties where u.email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
        }
        catch (NoResultException ex){
            return null;
        }
    }

    /**
     * Validation of new user
     * @param user User to validate
     */
    private void validateUser(User user){
        Guard.requireNotNull(user, "User is null");
        Guard.requireNotNull(user.getName(), "User name should not be null");
        Guard.requireNotNull(user.getEmail(), "User email should not be null");
        Guard.requireNotNull(user.getPassword(), "User password should not be null");
        Guard.requireNotNull(user.getRole(), "Role should not be null");
        Guard.requireNotNull(user.getLevelOfExperience(), "Level of experience should not be null");

        if(!user.getEmail().matches(".+@.+\\....?")){
            throw new IllegalArgumentException("User email has wrong format");
        }
    }
}
