package cz.muni.fi.dao;

import cz.muni.fi.entity.Mission;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.helpers.Guard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Data Access Object implementation for Mission entity.
 *
 * @author Martin Kazimir
 */
@Repository
@Transactional
public class MissionDaoImpl implements MissionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addMission(Mission mission) {
        Guard.requireNotNull(mission, "Mission is null");
        Guard.requireNotNull(mission.getName(), "Mission name is null");
        Guard.requireNull(mission.getId(), "Mission id is not null");

        entityManager.persist(mission);
    }

    @Override
    public List<Mission> findAllMissions() {
        return entityManager.createQuery("select mission from Mission mission", Mission.class).getResultList();
    }

    @Override
    public List<Mission> findAllMissions(MissionProgress progress) {
        return entityManager.createQuery("select mission from Mission mission " +
                        "where mission.missionProgress = :progress", Mission.class).
                setParameter("progress", progress).
                getResultList();
    }

    @Override
    public Mission findMissionById(Long id) {
        Guard.requireNotNull(id, "Mission id is null");

        return entityManager.find(Mission.class, id);
    }

    @Override
    public void updateMission(Mission mission) {
        Guard.requireNotNull(mission, "Mission is null");
        Guard.requireNotNull(mission.getName(), "Mission name is null");
        Guard.requireNotNull(mission.getId(), "Mission id is null");

        entityManager.merge(mission);
    }

    @Override
    public void removeMission(Mission mission) {
        Guard.requireNotNull(mission, "Mission is null");
        Guard.requireNotNull(mission.getId(), "Mission id is null");

        Mission del = findMissionById(mission.getId());

        Guard.requireNotNull(del, "Mission does not exist");

        entityManager.remove(del);
    }
}
