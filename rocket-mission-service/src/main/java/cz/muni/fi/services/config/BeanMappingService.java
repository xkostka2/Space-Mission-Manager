package cz.muni.fi.services.config;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Dozer bean mapping service.
 *
 * @author Martin Hořelka (469003)
 */
public interface BeanMappingService {

    /**
     * Maps collection of objects to given class
     * @param objects collection to be mapped
     * @param mapToClass target class
     * @param <T> target class
     * @return list of mapped objects
     */
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Maps object to given class
     * @param u object to be mapped
     * @param mapToClass target class
     * @param <T> target class
     * @return mapped object
     */
    <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * Returns instance of the mapper
     * @return mapper instance
     */
    Mapper getMapper();
}