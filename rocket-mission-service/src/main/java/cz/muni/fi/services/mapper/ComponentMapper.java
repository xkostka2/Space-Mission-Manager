package cz.muni.fi.services
        .mapper;

import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.component.UpdateComponentDTO;
import cz.muni.fi.entity.Component;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Maps Component entity related DTOs to their Entity counterparts.
 *
 * @author Martin Hořelka (469003)
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface ComponentMapper {
    ComponentDTO componentToComponentDTO(Component component);
    Component componentDTOToComponent(ComponentDTO componentDTO);

    CreateComponentDTO componentToCreateComponentDTO(Component component);
    Component createComponentDTOToComponent(CreateComponentDTO createComponentDTO);
    Component mapToEntity(CreateComponentDTO createComponentDTO);
    UpdateComponentDTO componentToUpdateComponentDTO(Component component);
    Component updateComponentDTOToComponent(UpdateComponentDTO updateComponentDTO);

    List<ComponentDTO> componentsToComponentDTOs(List<Component> components);
}
