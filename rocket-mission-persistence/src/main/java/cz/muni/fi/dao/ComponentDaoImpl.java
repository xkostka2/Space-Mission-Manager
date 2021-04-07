package cz.muni.fi.dao;

import cz.muni.fi.entity.Component;
import cz.muni.fi.helpers.Guard;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Tomas Bouma
 *
 * @author Tomas Bouma
 */
@Repository
@Transactional
public class ComponentDaoImpl implements ComponentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComponent(Component component) {
        Guard.requireNotNull(component, "Component is null");
        Guard.requireNotNull(component.getName(), "name is null");
        Guard.requireNull(component.getId(), "id is not null");

        this.entityManager.persist(component);
    }

    @Override
    public List<Component> findAllComponents() {
        return entityManager.createQuery("select cc from Component cc", Component.class).getResultList();
    }

    @Override
    public Component findComponentById(Long id) {
        Guard.requireNotNull(id, "id is null");

        try {
            return entityManager.createQuery("select cc from Component cc fetch all properties where id = :id", Component.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public void updateComponent(Component component) {
        Guard.requireNotNull(component, "Component is null");
        Guard.requireNotNull(component.getName(), "name is null");
        Guard.requireNotNull(component.getId(), "id is null");

        this.entityManager.merge(component);
    }

    @Override
    public void removeComponent(Component component) {
        Guard.requireNotNull(component, "Component is null");
        Guard.requireNotNull(component.getId(), "id is null");

        Component del = findComponentById(component.getId());

        Guard.requireNotNull(del, "Component does not exist");

        entityManager.remove(del);
    }
}