//package cz.muni.fi.facades;
//
//import cz.muni.fi.dto.mission.MissionDTO;
//import cz.muni.fi.dto.mission.CreateMissionDTO;
//import cz.muni.fi.dto.mission.UpdateMissionDTO;
//import cz.muni.fi.entity.Mission;
//import cz.muni.fi.enums.MissionProgress;
//import cz.muni.fi.facade.MissionFacade;
//
//import cz.muni.fi.services.impl.MissionServiceImpl;
//import cz.muni.fi.services.mapper.CycleAvoidingMappingContext;
//import cz.muni.fi.services.mapper.MissionMapperImpl;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.annotation.DirtiesContext;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.time.*;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//
///**
// * Mission facade layer tests.
// *
// * @author Martin Hořelka (469003)
// */
//@DirtiesContext
//public class MissionFacadeImplTest {
//
//    @Mock
//    private final MissionServiceImpl missionService = new MissionServiceImpl();
//
//    @Mock
//    private MissionMapperImpl missionMapper;
//
//    private CycleAvoidingMappingContext cycleAvoidingMappingContext;
//
//    private MissionFacade missionFacade;
//
//    private CreateMissionDTO createMissionDTO;
//
//    private MissionDTO missionDTO1;
//    private MissionDTO missionDTO2;
//
//    private Mission mission;
//    private Mission mission1;
//    private Mission mission2;
//
//    @BeforeMethod
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        cycleAvoidingMappingContext = new CycleAvoidingMappingContext();
//        this.missionFacade = new MissionFacadeImpl(missionService, missionMapper);
//
//        createMissionDTO = getCreateMissionDTO("create mission");
//        mission = new Mission();
//        mission.setName("create mission");
//        mission.setId(3L);
//
//        CreateMissionDTO createMissionDTO1 = getCreateMissionDTO("mission 1");
//        CreateMissionDTO createMissionDTO2 = getCreateMissionDTO("mission 2");
//
//        mission1 = new Mission();
//        mission1.setName("mission 1");
//        mission1.setId(1L);
//        mission1.setMissionProgress(MissionProgress.FINISHED);
//
//        mission2 = new Mission();
//        mission2.setName("mission 2");
//        mission2.setId(2L);
//        mission2.setMissionProgress(MissionProgress.IN_PROGRESS);
//
//        given(missionMapper.createMissionDTOToMission(createMissionDTO, cycleAvoidingMappingContext)).willReturn(mission);
//        given(missionMapper.createMissionDTOToMission(createMissionDTO1, cycleAvoidingMappingContext)).willReturn(mission1);
//        given(missionMapper.createMissionDTOToMission(createMissionDTO2, cycleAvoidingMappingContext)).willReturn(mission2);
//
//        given(missionService.addMission(mission)).willReturn(mission);
//        given(missionService.addMission(mission1)).willReturn(mission1);
//        given(missionService.addMission(mission2)).willReturn(mission2);
//
//        given(missionService.findMissionById(1L)).willReturn(mission1);
//        given(missionService.findMissionById(2L)).willReturn(mission2);
//        given(missionService.findMissionById(3L)).willReturn(mission);
//
//        MissionDTO missionDTO = getMissionDTO("create mission", 3L);
//        missionDTO1 = getMissionDTO("mission 1", 1L);
//        missionDTO1.setMissionProgress(MissionProgress.FINISHED);
//        missionDTO1.setResult("It's over!");
//        missionDTO2 = getMissionDTO("mission 2", 2L);
//        missionDTO2.setMissionProgress(MissionProgress.IN_PROGRESS);
//
//        given(missionMapper.missionToMissionDTO(mission, cycleAvoidingMappingContext)).willReturn(missionDTO);
//        given(missionMapper.missionToMissionDTO(mission1, cycleAvoidingMappingContext)).willReturn(missionDTO1);
//        given(missionMapper.missionToMissionDTO(mission2, cycleAvoidingMappingContext)).willReturn(missionDTO2);
//    }
//
//    @Test
//    public void testAddMission() {
//        List<Mission> missionList = Arrays.asList(mission1, mission2);
//        List<MissionDTO> missionDTOList = Arrays.asList(missionDTO1, missionDTO2);
//
//        given(missionService.findAllMissions()).willReturn(missionList);
//        given(missionMapper.missionsToMissionDTOs(missionList, cycleAvoidingMappingContext)).willReturn(missionDTOList);
//
//        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(missionDTO1, missionDTO2);
//        MissionDTO missionDTO = missionFacade.findMissionById(missionFacade.addMission(createMissionDTO).getId());
//        assertThat(missionDTO).isEqualToIgnoringGivenFields(createMissionDTO, "id");
//
//        missionList = Arrays.asList(mission1, mission2, mission);
//        missionDTOList = Arrays.asList(missionDTO1, missionDTO2, missionDTO);
//
//        given(missionService.findAllMissions()).willReturn(missionList);
//
//        given(missionMapper.missionsToMissionDTOs(missionList, cycleAvoidingMappingContext)).willReturn(missionDTOList);
//
//        assertThat(missionFacade.findAllMissions()).hasSize(3).contains(missionDTO);
//    }
//
//    @Test
//    public void testDeleteMission() {
//        List<Mission> missionList = Arrays.asList(mission1, mission2);
//        List<MissionDTO> missionDTOList = Arrays.asList(missionDTO1, missionDTO2);
//
//        given(missionService.findAllMissions()).willReturn(missionList);
//        given(missionMapper.missionsToMissionDTOs(missionList, cycleAvoidingMappingContext)).willReturn(missionDTOList);
//
//        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(missionDTO1, missionDTO2);
//
//        missionFacade.deleteMission(missionDTO2);
//        missionList = Collections.singletonList(mission1);
//        missionDTOList = Collections.singletonList(missionDTO1);
//
//        given(missionService.findAllMissions()).willReturn(missionList);
//        given(missionMapper.missionsToMissionDTOs(missionList, cycleAvoidingMappingContext)).willReturn(missionDTOList);
//
//        assertThat(missionFacade.findAllMissions()).hasSize(1).contains(missionDTO1);
//    }
//
//    @Test
//    public void testFindAllMissions() {
//        List<Mission> missionList = Arrays.asList(mission1, mission2);
//        List<MissionDTO> missionDTOList = Arrays.asList(missionDTO1, missionDTO2);
//
//        given(missionService.findAllMissions()).willReturn(missionList);
//        given(missionMapper.missionsToMissionDTOs(missionList, cycleAvoidingMappingContext)).willReturn(missionDTOList);
//
//        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(missionDTO1, missionDTO2);
//    }
//
//    @Test
//    public void testFindMissionById() {
//        assertThat(missionFacade.findMissionById(missionDTO1.getId())).isEqualTo(missionDTO1);
//        assertThat(missionFacade.findMissionById(missionDTO2.getId())).isEqualTo(missionDTO2);
//    }
//
//    @Test
//    public void testFindAllMissionsWithProgress() {
//        List<Mission> missionFinishedList = Collections.singletonList(mission1);
//        List<MissionDTO> missionFinishedDTOList = Collections.singletonList(missionDTO1);
//
//        List<Mission> missionInProgressList = Collections.singletonList(mission2);
//        List<MissionDTO> missionInProgressDTOList = Collections.singletonList(missionDTO2);
//
//        given(missionService.findAllMissions(MissionProgress.FINISHED)).willReturn(missionFinishedList);
//        given(missionMapper.missionsToMissionDTOs(missionFinishedList, cycleAvoidingMappingContext)).willReturn(missionFinishedDTOList);
//
//        given(missionService.findAllMissions(MissionProgress.IN_PROGRESS)).willReturn(missionInProgressList);
//        given(missionMapper.missionsToMissionDTOs(missionInProgressList, cycleAvoidingMappingContext)).willReturn(missionInProgressDTOList);
//
//        assertThat(missionFacade.findAllMissions(MissionProgress.FINISHED)).hasSize(1).contains(missionDTO1);
//        assertThat(missionFacade.findAllMissions(MissionProgress.IN_PROGRESS)).hasSize(1).contains(missionDTO2);
//    }
//
//    @Test
//    public void testUpdateMission() {
//        UpdateMissionDTO updateMissionDTO = getUpdateMissionDTO(missionDTO1.getName(), missionDTO1.getId());
//        updateMissionDTO.setName("update");
//        mission1.setName("update");
//        given(missionMapper.updateMissionDTOToMission(updateMissionDTO, cycleAvoidingMappingContext)).willReturn(mission1);
//        given(missionService.updateMission(mission1)).willReturn(mission1);
//
//        missionFacade.updateMission(updateMissionDTO);
//        assertThat(missionFacade.findMissionById(updateMissionDTO.getId()).getId()).isEqualTo(updateMissionDTO.getId());
//    }
//
//    @Test
//    public void testArchive() {
//        missionFacade.archive(missionDTO1, ZonedDateTime.now().minusDays(10), "It's over!");
//        assertThat(missionFacade.findMissionById(missionDTO1.getId()))
//                .hasFieldOrPropertyWithValue("missionProgress", MissionProgress.FINISHED);
//        assertThat(missionFacade.findMissionById(missionDTO1.getId()).getResult()).isNotEmpty();
//    }
//
//    private CreateMissionDTO getCreateMissionDTO(String name) {
//        CreateMissionDTO missionDTO = new CreateMissionDTO();
//        missionDTO.setName(name);
//        return missionDTO;
//    }
//
//    private UpdateMissionDTO getUpdateMissionDTO(String name, Long id) {
//        UpdateMissionDTO missionDTO = new UpdateMissionDTO();
//        missionDTO.setName(name);
//        missionDTO.setId(id);
//        return missionDTO;
//    }
//
//    private MissionDTO getMissionDTO(String name, Long id) {
//        MissionDTO missionDTO = new MissionDTO();
//        missionDTO.setName(name);
//        missionDTO.setId(id);
//        return missionDTO;
//    }
//}
