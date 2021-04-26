package cz.muni.fi.services;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link BeanMappingService}.
 *
 * @author Martin Hořelka (469003)
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        return objects.stream()
                .map(p -> dozer.map(p, mapToClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return dozer.map(u, mapToClass);
    }

    @Override
    public Mapper getMapper() {
        return dozer;
    }
}
