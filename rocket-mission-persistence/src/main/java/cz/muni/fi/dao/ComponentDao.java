package cz.muni.fi.dao;

import cz.muni.fi.entity.Component;

import java.util.List;

public interface ComponentDao {

        /**
         *
         * @param component componnent to add
         */
        void addComponent(Component component);

        List<Component> findAllComponents();

        /**
         *
         * @param id id of component
         */
        Component findComponentById(Long id);

        /**
         *
         * @param component component to update
         */
        void updateComponent(Component component);

        /**
         *
         * @param component component to remove
         */
        void removeComponent(Component component);
}
