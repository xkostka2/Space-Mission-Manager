package cz.muni.fi.dao;

import cz.muni.fi.InMemoryDatabaseSpring;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Tests for MissionDao implementation class.
 *
 * @author Tomas Bouma
 */
@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
public class MissionDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MissionDao missionDao;

    private Mission marsMission;
    private Mission jupyterMission;

    @BeforeClass
    public void beforeClass() {
        marsMission = new Mission();
        marsMission.setName("Mission to Mars");
        marsMission.setDestination("Mars");
        marsMission.setMissionProgress(MissionProgress.PLANNED);
        missionDao.addMission(marsMission);

        jupyterMission = new Mission();
        jupyterMission.setName("Jupyter mission");
        jupyterMission.setDestination("Jupyter");
        marsMission.setMissionProgress(MissionProgress.FINISHED);
        missionDao.addMission(jupyterMission);
    }

    @AfterClass
    public void afterClass() {
        for (Mission mission : missionDao.findAllMissions()) {
            missionDao.removeMission(mission);
        }
    }

    @Test
    public void testAddMission() {
        Mission pluto = new Mission();
        pluto.setName("pluto mission");
        pluto.setMissionProgress(MissionProgress.FINISHED);
        missionDao.addMission(pluto);

        assertThat(missionDao.findMissionById(pluto.getId())).isEqualTo(pluto);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullMission() {
        missionDao.addMission(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullIdMission() {
        Mission mission = new Mission();
        mission.setName("Jakarta");
        mission.setId(null);
        missionDao.addMission(mission);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMissionNullName() {
        Mission mission = new Mission();
        mission.setMissionProgress(MissionProgress.FINISHED);
        missionDao.addMission(mission);
    }

    @Test
    public void testFindAllComponents() {
        List<Mission> allMissions = missionDao.findAllMissions();

        assertThat(allMissions.size()).isGreaterThanOrEqualTo(2);
        assertThat(allMissions.contains(marsMission)).isTrue();
        assertThat(allMissions.contains(jupyterMission)).isTrue();
    }

    @Test
    public void testFindMissionNonExistentMissionId() {
        Mission foundMission = missionDao.findMissionById(20L);

        assertThat(foundMission).isNull();
    }

    @Test
    public void testFindMissionById() {

        Mission foundMission = missionDao.findMissionById(jupyterMission.getId());

        assertThat(foundMission).isEqualTo(jupyterMission);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMissionByIdNull() {
        missionDao.findMissionById(null);
    }
    @Test
    public void testUpdateMission() {
        marsMission.setName("some different name");
        missionDao.updateMission(marsMission);

        assertThat(missionDao.findMissionById(marsMission.getId())).isEqualTo(marsMission);
    }

    @Test
    public void testRemoveMission() {
        Long jupyterMissionId = jupyterMission.getId();
        missionDao.removeMission(jupyterMission);

        assertThat(missionDao.findMissionById(jupyterMissionId)).isNull();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateMissionNull() {
        missionDao.updateMission(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateMissionNameNull() {
        Mission mission = new Mission();
        missionDao.updateMission(mission);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateMissionIdNull() {
        Mission mission = new Mission();
        mission.setName("Jakarta");
        missionDao.updateMission(mission);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNonExistingMission() {
        Mission mission = new Mission();
        mission.setName("shield");
        mission.setId(1L);
        missionDao.removeMission(mission);
    }

}
