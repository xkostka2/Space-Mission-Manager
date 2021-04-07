package cz.muni.fi.dao;

import cz.muni.fi.entity.Rocket;
import cz.muni.fi.helpers.Guard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Rocket DAO interface implementation.
 *
 * @author Martin Hořelka (469003)
 */
@Repository
@Transactional
public class RocketDaoImpl implements RocketDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRocket(Rocket rocket) {
        Guard.requireNotNull(rocket, "Rocket is null");
        Guard.requireNotNull(rocket.getName(), "Rocket name is null");
        Guard.requireNull(rocket.getId(), "Rocket id is not null");

        this.entityManager.persist(rocket);
    }

    @Override
    public List<Rocket> findAllRockets() {
        return entityManager.createQuery("select r from Rocket r", Rocket.class).getResultList();
    }

    @Override
    public Rocket findRocketById(Long id) {
        Guard.requireNotNull(id, "Rocket id is null");

        try {
            return entityManager.createQuery("select r from Rocket r fetch all properties where id = :id", Rocket.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public void updateRocket(Rocket rocket) {
        Guard.requireNotNull(rocket, "Rocket is null");
        Guard.requireNotNull(rocket.getName(), "Rocket name is null");
        Guard.requireNotNull(rocket.getId(), "Rocket id is null");

        this.entityManager.merge(rocket);
    }

    @Override
    public void removeRocket(Rocket rocket) {
        Guard.requireNotNull(rocket, "Rocket is null");
        Guard.requireNotNull(rocket.getId(), "Rocket id is null");

        Rocket del = findRocketById(rocket.getId());

        Guard.requireNotNull(del, "Rocket does not exist");

        entityManager.remove(del);
    }
}
