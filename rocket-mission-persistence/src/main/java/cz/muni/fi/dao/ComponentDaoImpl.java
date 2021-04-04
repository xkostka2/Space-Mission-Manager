package cz.muni.fi.dao;

import cz.muni.fi.entity.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tdtom167
 *
 * @author tdtom167
 */
@Repository
@Transactional
public class ComponentDaoImpl implements ComponentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Component addComponent(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Component is null");
        }
        if (component.getName() == null) {
            throw new IllegalArgumentException("name is null");
        }
        if (component.getId() != null) {
            throw new IllegalArgumentException("id is not null");
        }
        this.entityManager.persist(component);
        return component;
    }

    @Override
    public List<Component> findAllComponents() {
        return entityManager.createQuery("select cc from Component cc", Component.class).getResultList();
    }

    @Override
    public Component findComponentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        try {
            return entityManager.createQuery("select cc from Component cc fetch all properties where id = :id", Component.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Component updateComponent(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Component is null");
        }
        if (component.getName() == null) {
            throw new IllegalArgumentException("name is null");
        }
        if (component.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        this.entityManager.merge(component);
        this.entityManager.flush();
        return component;
    }

    @Override
    public Component removeComponent(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Component is null");
        }
        if (component.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        Component del = findComponentById(component.getId());
        if (del == null) {
            throw new IllegalArgumentException("Component does not exist");
        }

        entityManager.remove(del);
        return component;
    }
}