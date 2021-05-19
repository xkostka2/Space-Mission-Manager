package cz.muni.fi.facades;


import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.mission.UpdateMissionDTO;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.facade.MissionFacade;
import cz.muni.fi.services.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.*;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Mission facade layer tests.
 *
 * @author Martin Hořelka (469003)
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MissionFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private MissionFacade missionFacade;
    private CreateMissionDTO createMissionDTO;
    private UpdateMissionDTO updateMissionDTO;
    private MissionDTO missionDTO1;
    private MissionDTO missionDTO2;
    @BeforeMethod
    public void setUp() {
        createMissionDTO = getCreateMissionDTO("create mission", MissionProgress.PLANNED);
        missionDTO1 = missionFacade.findMissionById(missionFacade
                .addMission(getCreateMissionDTO("apollo 1", MissionProgress.IN_PROGRESS)).getId());
        missionDTO2 = missionFacade.findMissionById(missionFacade
                .addMission(getCreateMissionDTO("apollo 2", MissionProgress.FINISHED)).getId());
    }
    static public CreateMissionDTO getCreateMissionDTO(String name, MissionProgress progress) {
        CreateMissionDTO createMissionDTO = new CreateMissionDTO();
        createMissionDTO.setName(name);
        createMissionDTO.setMissionProgress(progress);
        return createMissionDTO;
    }
    private UpdateMissionDTO getUpdateMissionDTO(String name, Long id) {
        UpdateMissionDTO updateMissionDTO = new UpdateMissionDTO();
        updateMissionDTO.setId(id);
        updateMissionDTO.setName(name);
        return updateMissionDTO;
    }
    @AfterMethod
    public void afterMethod() {
        for (MissionDTO mission : missionFacade.findAllMissions()) {
            missionFacade.deleteMission(mission);
        }
        assertThat(missionFacade.findAllMissions()).isEmpty();
        createMissionDTO = null;
        updateMissionDTO = null;
        missionDTO1 = null;
        missionDTO2 = null;
    }
    @Test
    public void testAddMission() {
        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(missionDTO1, missionDTO2);
        MissionDTO missionDTO = missionFacade.findMissionById(missionFacade.addMission(createMissionDTO).getId());
        assertThat(missionDTO).isEqualToIgnoringGivenFields(createMissionDTO, "id");
        assertThat(missionFacade.findAllMissions()).hasSize(3).contains(missionDTO);
    }
    @Test
    public void testDeleteMission() {
        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(missionDTO1, missionDTO2);
        missionFacade.deleteMission(missionDTO1);
        assertThat(missionFacade.findAllMissions()).hasSize(1).contains(missionDTO2);
    }
    @Test
    public void testFindAllMissions() {
        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(missionDTO1, missionDTO2);
    }
    @Test
    public void testFindMissionById() {
        assertThat(missionFacade.findMissionById(missionDTO1.getId())).isEqualTo(missionDTO1);
        assertThat(missionFacade.findMissionById(missionDTO2.getId())).isEqualTo(missionDTO2);
    }
    @Test
    public void testFindAllMissionsWithProgress() {
        assertThat(missionFacade.findAllMissions(MissionProgress.IN_PROGRESS)).hasSize(1).contains(missionDTO1);
        assertThat(missionFacade.findAllMissions(MissionProgress.FINISHED)).hasSize(1).contains(missionDTO2);
    }
    @Test
    public void testUpdateMission() {
        updateMissionDTO = getUpdateMissionDTO(missionDTO1.getName(), missionDTO1.getId());
        updateMissionDTO.setName("updated name");
        missionFacade.updateMission(updateMissionDTO);
        assertThat(missionFacade.findMissionById(updateMissionDTO.getId()))
                .isEqualToComparingFieldByField(updateMissionDTO);
    }
    @Test
    public void testArchive() {
        missionFacade.archive(missionDTO1, ZonedDateTime.now().minusDays(10), "It's over!");
        assertThat(missionFacade.findMissionById(missionDTO1.getId()))
                .hasFieldOrPropertyWithValue("missionProgress", MissionProgress.FINISHED);
        assertThat(missionFacade.findMissionById(missionDTO1.getId()).getResult()).isNotEmpty();
    }
}