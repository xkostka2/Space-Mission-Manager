package cz.muni.fi.facades;

import cz.muni.fi.dto.create.CreateComponentDTO;
import cz.muni.fi.dto.create.CreateMissionDTO;
import cz.muni.fi.dto.create.CreateRocketDTO;
import cz.muni.fi.dto.update.UpdateMissionDTO;

import java.time.ZonedDateTime;

public class TestHelper {
    public static CreateComponentDTO getCreateComponentDTO(String name){
        CreateComponentDTO createComponentDTO = new CreateComponentDTO();
        createComponentDTO.setName(name);
        createComponentDTO.setReadyDate(ZonedDateTime.now().plusDays(10));
        return createComponentDTO;
    }

    public static CreateRocketDTO getCreateRocketDTO(String name){
        CreateRocketDTO createRocketDTO = new CreateRocketDTO();
        createRocketDTO.setName(name);
        return createRocketDTO;
    }

    public static CreateMissionDTO getCreateMissionDTO(String name){
        CreateMissionDTO createMissionDTO = new CreateMissionDTO();
        createMissionDTO.setName(name);
        createMissionDTO.setDestination(name);
        createMissionDTO.setEta(ZonedDateTime.now().plusDays(10));
        return createMissionDTO;
    }

    public static UpdateMissionDTO getUpdateMissionDTO(String name){
        UpdateMissionDTO updateMissionDTO = new UpdateMissionDTO();
        updateMissionDTO.setName(name);
        updateMissionDTO.setDestination(name);
        updateMissionDTO.setEta(ZonedDateTime.now().plusDays(10));
        return updateMissionDTO;
    }
}
