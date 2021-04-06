package cz.muni.fi.dao;

import cz.muni.fi.entity.Mission;
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
        if (mission == null) {
            throw new IllegalArgumentException("Mission is null");
        }
        if (mission.getName() == null) {
            throw new IllegalArgumentException("Mission name is null");
        }
        if (mission.getId() != null) {
            throw new IllegalArgumentException("Mission id is not null");
        }
        entityManager.persist(mission);
    }

    @Override
    public List<Mission> findAllMissions() {
        return entityManager.createQuery("select mission from Mission mission", Mission.class).getResultList();
    }

    @Override
    public Mission findMissionById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Mission id is null");
        }
        return entityManager.find(Mission.class, id);
    }

    @Override
    public void updateMission(Mission mission) {
        if (mission == null) {
            throw new IllegalArgumentException("Mission is null");
        }
        if (mission.getName() == null) {
            throw new IllegalArgumentException("Mission name is null");
        }
        if (mission.getId() != null) {
            throw new IllegalArgumentException("Mission id is not null");
        }
        entityManager.merge(mission);
    }

    @Override
    public void removeMission(Mission mission) {
        if (mission == null) {
            throw new IllegalArgumentException("Mission is null");
        }
        if (mission.getId() == null) {
            throw new IllegalArgumentException("Mission id is null");
        }
        Mission del = findMissionById(mission.getId());
        if (del == null) {
            throw new IllegalArgumentException("Mission does not exist");
        }
        entityManager.remove(mission);
    }
}
