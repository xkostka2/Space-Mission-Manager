package cz.muni.fi.services.mapper;

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
    ComponentDTO componentToComponentDTO(Component component, @Context CycleAvoidingMappingContext context);
    Component componentDTOToComponent(ComponentDTO componentDTO,  @Context CycleAvoidingMappingContext context);

    CreateComponentDTO componentToCreateComponentDTO(Component component, @Context CycleAvoidingMappingContext context);
    Component createComponentDTOToComponent(CreateComponentDTO createComponentDTO, @Context CycleAvoidingMappingContext context);
    Component mapToEntity(CreateComponentDTO createComponentDTO, @Context CycleAvoidingMappingContext context);
    UpdateComponentDTO componentToUpdateComponentDTO(Component component, @Context CycleAvoidingMappingContext context);
    Component updateComponentDTOToComponent(UpdateComponentDTO updateComponentDTO, @Context CycleAvoidingMappingContext context);

    List<ComponentDTO> componentsToComponentDTOs(List<Component> components, @Context CycleAvoidingMappingContext context);
}
