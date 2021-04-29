package cz.muni.fi.dao;

import cz.muni.fi.entity.Component;

import java.util.List;

/**
 * Created by Tomas Bouma
 *
 * @author Tomas Bouma
 */
public interface ComponentDao {

        /**
         *
         * @param component componnent to add
         * @return Updated Component
         */
        Component addComponent(Component component);

        List<Component> findAllComponents();

        /**
         *
         * @param id id of component
         */
        Component findComponentById(Long id);

        /**
         *
         * @param component component to update
         * @return Updated Component
         */
        Component updateComponent(Component component);

        /**
         *
         * @param component component to remove
         */
        void removeComponent(Component component);
}
