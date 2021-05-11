package cz.muni.fi.services;

import cz.muni.fi.dao.MissionDao;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.impl.MissionServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Mission service layer tests.
 *
 * @author Martin Hořelka (469003)
 */
public class MissionServiceImplTest {
    @Mock
    private MissionDao missionDao;

    @InjectMocks
    private final MissionService missionService = new MissionServiceImpl();

    private Mission mission1;
    private Mission mission2;
    private Mission mission3;
    private Mission mission4;

    private Long counter = 10L;
    private final Map<Long, Mission> missions = new HashMap<>();

    @BeforeClass
    public void beforeClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(missionDao.addMission(any(Mission.class))).then(invoke -> {
            Mission mockedMission = invoke.getArgumentAt(0, Mission.class);
            if (mockedMission.getId() != null) {
                throw new IllegalArgumentException("Mission already exist");
            }
            if (mockedMission.getName() == null) {
                throw new IllegalArgumentException("Mission name can't be null");
            }
            if (checkMissionsNameDuplicity(mockedMission.getName(), -1L)) {
                throw new IllegalArgumentException("Mission name already exist");
            }
            long index = counter;
            mockedMission.setId(index);
            missions.put(index, mockedMission);
            counter++;
            return mockedMission;
        });

        when(missionDao.updateMission(any(Mission.class))).then(invoke -> {
            Mission mockedMission = invoke.getArgumentAt(0, Mission.class);
            if (mockedMission.getId() == null) {
                throw new IllegalArgumentException("Mission was not created yet.");
            }
            if (mockedMission.getName() == null) {
                throw new IllegalArgumentException("Mission name can't be null");
            }
            if (checkMissionsNameDuplicity(mockedMission.getName(), mockedMission.getId())) {
                throw new IllegalArgumentException("Mission name already exist");
            }
            missions.replace(mockedMission.getId(), mockedMission);
            return mockedMission;
        });

        doAnswer(invoke -> {
            Mission mockedMission = invoke.getArgumentAt(0, Mission.class);
            if (mockedMission.getId() == null) {
                throw new IllegalArgumentException("Mission is not in Database.");
            }
            missions.remove(mockedMission.getId(), mockedMission);
            return mockedMission;
        }).when(missionDao).removeMission(any(Mission.class));

        when(missionDao.findAllMissions())
                .then(invoke -> Collections.unmodifiableList(new ArrayList<>(missions.values())));

        when(missionDao.findMissionById(any())).then(invoke -> {
            Long id = invoke.getArgumentAt(0, Long.class);
            if (id == null) {
                throw new IllegalArgumentException("id is null");
            }
            return missions.get(id);
        });

        when(missionDao.findAllMissions(any(MissionProgress.class))).then(invoke -> {
            MissionProgress missionProgress = invoke.getArgumentAt(0, MissionProgress.class);
            List<Mission> missionList = new ArrayList<>();

            for (Mission m : missions.values()) {
                if (m.getMissionProgress() == missionProgress) {
                    missionList.add(m);
                }
            }
            return Collections.unmodifiableList(missionList);
        });
    }

    @BeforeMethod
    public void beforeTest() {
        missions.clear();
        mission1 = createMission("mission1");
        mission2 = createMission("mission2");
        mission3 = createMission("mission3");
        mission4 = createMission("mission4");

        mission1.setId(1L);
        mission2.setId(2L);
        mission3.setId(3L);
        mission4.setId(4L);

        mission1.setMissionProgress(MissionProgress.PLANNED);
        mission2.setMissionProgress(MissionProgress.IN_PROGRESS);
        mission3.setMissionProgress(MissionProgress.IN_PROGRESS);
        mission4.setMissionProgress(MissionProgress.FINISHED);

        missions.put(1L, mission1);
        missions.put(2L, mission2);
        missions.put(3L, mission3);
        missions.put(4L, mission4);
    }

    @Test
    public void addNewMission() throws ServiceDataAccessException {
        int sizeBefore = missions.size();
        Mission mission = createMission("mission");
        missionService.addMission(mission);
        assertThat(missions.values()).hasSize(sizeBefore + 1).contains(mission);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addNullMission() {
        missionService.addMission(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addMissionNullName() {
        missionService.addMission(new Mission());
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addExistingMission() {
        Mission mission = createMission("mission");
        Mission anotherMission = createMission("mission");
        missionService.addMission(mission);
        missionService.addMission(anotherMission);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addMissionWithIdNotNull() {
        Mission mission = createMission("mission");
        mission.setId(1L);
        missionService.addMission(mission);
    }

    @Test
    public void updateMission() throws ServiceDataAccessException {
        mission1.setName("updated mission");
        missionService.updateMission(mission1);

        Mission updated = missions.get(mission1.getId());

        assertThat(updated.getName()).isEqualTo("updated mission");
        assertThat(updated).isEqualToComparingFieldByField(mission1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateNullMission() {
        missionService.updateMission(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateMissionNullName() {
        mission1.setName(null);
        missionService.updateMission(mission1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateMissionWithNullId() {
        Mission mission = createMission("mission");
        mission.setMissionProgress(MissionProgress.IN_PROGRESS);
        missionService.updateMission(mission);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateMissionWithDuplicateName() {
        mission1.setName(mission2.getName());
        missionService.updateMission(mission1);
    }

    @Test
    public void deleteMission() throws ServiceDataAccessException {
        int sizeBefore = missions.values().size();
        missionService.deleteMission(mission1);

        assertThat(missions.values()).hasSize(sizeBefore - 1)
                .doesNotContain(mission1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void deleteNullMission() {
        missionService.deleteMission(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void deleteMissionWithNullId() {
        Mission mission = createMission("mission");
        missionService.deleteMission(mission);
    }

    @Test
    public void deleteMissionNotInDB() throws DataAccessException {
        int sizeBefore = missions.values().size();
        Mission mission = createMission("mission");
        mission.setId(counter * 2L);
        missionService.deleteMission(mission);

        assertThat(missions.values()).hasSize(sizeBefore)
                .doesNotContain(mission);
    }

    @Test
    public void findAllMissions() throws DataAccessException {
        assertThat(missionService.findAllMissions())
                .containsExactlyInAnyOrder(mission1, mission2, mission3, mission4);
    }

    @Test
    public void findAllActiveMissions() {
        assertThat(missionService.findAllMissions(MissionProgress.PLANNED))
                .containsExactlyInAnyOrder(mission1);
        assertThat(missionService.findAllMissions(MissionProgress.IN_PROGRESS))
                .containsExactlyInAnyOrder(mission2, mission3);
        assertThat(missionService.findAllMissions(MissionProgress.FINISHED))
                .containsExactlyInAnyOrder(mission4);
    }

    @Test
    public void findMissionById() throws DataAccessException {
        assertThat(missionService.findMissionById(mission1.getId()))
                .isEqualToComparingFieldByField(mission1);
    }

    @Test
    public void findMissionByIdNotInDB() throws DataAccessException {
        assertThat(missionService.findMissionById(1234L)).isNull();
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findMissionByNullId() {
        missionService.findMissionById(null);
    }

    @Test
    public void archiveMission() {
        missionService.archive(mission1, ZonedDateTime.now().minusDays(10), "It's over!");

        assertThat(mission1.getMissionProgress()).isEqualTo(MissionProgress.FINISHED);
        assertThat(mission1.getResult()).isNotNull().isNotEmpty().contains(mission1.getName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void archiveMissionWithNullMissionArgument() {
        missionService.archive(null, ZonedDateTime.now().minusDays(10), "It's over!");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void archiveMissionWithNullDateArgument() {
        missionService.archive(mission1, null, "It's over!");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void archiveMissionWithDateInThePast() {
        missionService.archive(mission1, ZonedDateTime.now().plusDays(10), "It's over!");
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void archiveMissionWithIdNotInDB() {
        Mission mission = createMission("name");
        missionService.archive(mission, ZonedDateTime.now().minusDays(20), "It's over!");
    }

    private boolean checkMissionsNameDuplicity(String name, long id) {
        for (Mission m : missions.values()) {
            if (m.getName().equals(name) && id != m.getId()) {
                return true;
            }
        }
        return false;
    }

    static Mission createMission(String name) {
        Mission mission = new Mission();
        mission.setName(name);
        return mission;
    }
}
