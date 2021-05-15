package cz.muni.fi.facades;

import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.facade.RocketFacade;

import cz.muni.fi.services.impl.RocketServiceImpl;
import cz.muni.fi.services.mapper.RocketMapperImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Martin Kostka
 *
 * @author Martin Kostka
 */
public class RocketFacadeImplTest {

    @Mock
    private final RocketServiceImpl rocketService = new RocketServiceImpl();

    @Mock
    private RocketMapperImpl rocketMapper;

    private RocketFacade rocketFacade;

    private CreateRocketDTO createRocketDTO;

    private RocketDTO rocketDTO1;
    private RocketDTO rocketDTO2;

    private Rocket rocket;
    private Rocket rocket1;
    private Rocket rocket2;

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);

        this.rocketFacade = new RocketFacadeImpl(rocketService, rocketMapper);

        createRocketDTO = getCreateRocketDTO("create rocket");
        rocket = new Rocket();
        rocket.setName("create rocket");
        rocket.setId(3L);

        CreateRocketDTO createRocketDTO1 = getCreateRocketDTO("rocket 1");
        CreateRocketDTO createRocketDTO2 = getCreateRocketDTO("rocket 2");

        rocket1 = new Rocket();
        rocket1.setName("rocket 1");
        rocket1.setId(1L);

        rocket2 = new Rocket();
        rocket2.setName("rocket 2");
        rocket2.setId(2L);

        given(rocketMapper.createRocketDTOToRocket(createRocketDTO)).willReturn(rocket);
        given(rocketMapper.createRocketDTOToRocket(createRocketDTO1)).willReturn(rocket1);
        given(rocketMapper.createRocketDTOToRocket(createRocketDTO2)).willReturn(rocket2);

        given(rocketService.addRocket(rocket)).willReturn(rocket);
        given(rocketService.addRocket(rocket1)).willReturn(rocket1);
        given(rocketService.addRocket(rocket2)).willReturn(rocket2);

        given(rocketService.findRocketById(1L)).willReturn(rocket1);
        given(rocketService.findRocketById(2L)).willReturn(rocket2);
        given(rocketService.findRocketById(3L)).willReturn(rocket);

        RocketDTO rocketDTO = getRocketDTO("create rocket", 3L);
        rocketDTO1 = getRocketDTO("rocket 1", 1L);

        rocketDTO2 = getRocketDTO("rocket 2", 2L);

        given(rocketMapper.rocketToRocketDTO(rocket)).willReturn(rocketDTO);
        given(rocketMapper.rocketToRocketDTO(rocket1)).willReturn(rocketDTO1);
        given(rocketMapper.rocketToRocketDTO(rocket2)).willReturn(rocketDTO2);
    }

    private CreateRocketDTO getCreateRocketDTO(String name) {
        CreateRocketDTO rocketDTO = new CreateRocketDTO();
        rocketDTO.setName(name);
        return rocketDTO;
    }

    private UpdateRocketDTO getUpdateRocketDTO(String name, Long id) {
        UpdateRocketDTO rocketDTO = new UpdateRocketDTO();
        rocketDTO.setName(name);
        rocketDTO.setId(id);
        return rocketDTO;
    }

    private RocketDTO getRocketDTO(String name, Long id) {
        RocketDTO rocketDTO = new RocketDTO();
        rocketDTO.setName(name);
        rocketDTO.setId(id);
        return rocketDTO;
    }
    
    @Test
    public void testAddRocket() {
        List<Rocket> rocketList = Arrays.asList(rocket1, rocket2);
        List<RocketDTO> rocketDTOList = Arrays.asList(rocketDTO1, rocketDTO2);

        given(rocketService.findAllRockets()).willReturn(rocketList);
        given(rocketMapper.rocketsToRocketDTOs(rocketList)).willReturn(rocketDTOList);

        assertThat(rocketFacade.findAllRockets()).hasSize(2).contains(rocketDTO1, rocketDTO2);
        RocketDTO rocketDTO = rocketFacade.findRocketById(rocketFacade.addRocket(createRocketDTO).getId());
        assertThat(rocketDTO).isEqualToIgnoringGivenFields(createRocketDTO, "id");

        rocketList = Arrays.asList(rocket1, rocket2, rocket);
        rocketDTOList = Arrays.asList(rocketDTO1, rocketDTO2, rocketDTO);

        given(rocketService.findAllRockets()).willReturn(rocketList);

        given(rocketMapper.rocketsToRocketDTOs(rocketList)).willReturn(rocketDTOList);

        assertThat(rocketFacade.findAllRockets()).hasSize(3).contains(rocketDTO);
    }

    @Test
    public void testRemoveRocket() {
        List<Rocket> rocketList = Arrays.asList(rocket1, rocket2);
        List<RocketDTO> rocketDTOList = Arrays.asList(rocketDTO1, rocketDTO2);

        given(rocketService.findAllRockets()).willReturn(rocketList);
        given(rocketMapper.rocketsToRocketDTOs(rocketList)).willReturn(rocketDTOList);

        assertThat(rocketFacade.findAllRockets()).hasSize(2).contains(rocketDTO1, rocketDTO2);

        rocketFacade.removeRocket(rocketDTO2);
        rocketList = Collections.singletonList(rocket1);
        rocketDTOList = Collections.singletonList(rocketDTO1);

        given(rocketService.findAllRockets()).willReturn(rocketList);
        given(rocketMapper.rocketsToRocketDTOs(rocketList)).willReturn(rocketDTOList);

        assertThat(rocketFacade.findAllRockets()).hasSize(1).contains(rocketDTO1);
    }

    @Test
    public void testFindAllRockets() {
        List<Rocket> rocketList = Arrays.asList(rocket1, rocket2);
        List<RocketDTO> rocketDTOList = Arrays.asList(rocketDTO1, rocketDTO2);

        given(rocketService.findAllRockets()).willReturn(rocketList);
        given(rocketMapper.rocketsToRocketDTOs(rocketList)).willReturn(rocketDTOList);

        assertThat(rocketFacade.findAllRockets()).hasSize(2).contains(rocketDTO1, rocketDTO2);
    }

    @Test
    public void testFindRocketById() {
        assertThat(rocketFacade.findRocketById(rocketDTO1.getId())).isEqualTo(rocketDTO1);
        assertThat(rocketFacade.findRocketById(rocketDTO2.getId())).isEqualTo(rocketDTO2);
    }

    @Test
    public void testUpdateRocket() {
        UpdateRocketDTO updateRocketDTO = getUpdateRocketDTO(rocketDTO1.getName(), rocketDTO1.getId());
        updateRocketDTO.setName("update");
        rocket1.setName("update");
        given(rocketMapper.updateRocketDTOToRocket(updateRocketDTO)).willReturn(rocket1);
        given(rocketService.updateRocket(rocket1)).willReturn(rocket1);

        rocketFacade.updateRocket(updateRocketDTO);
        assertThat(rocketFacade.findRocketById(updateRocketDTO.getId()).getId()).isEqualTo(updateRocketDTO.getId());
    }
}
