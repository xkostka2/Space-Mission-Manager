package cz.muni.fi.dao;

import cz.muni.fi.entity.Mission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin Kazimir
 */
@Repository
@Transactional
public class MissionDaoImpl implements MissionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addMission(Mission mission) {
        entityManager.persist(mission);
    }

    @Override
    public List<Mission> findAllMissions() {
        return entityManager.createQuery("select mission from Mission mission", Mission.class).getResultList();
    }

    @Override
    public Mission findMissionById(Long id) {
        return entityManager.find(Mission.class, id);
    }

    @Override
    public void updateMission(Mission mission) {
        entityManager.merge(mission);
    }

    @Override
    public void deleteMission(Mission mission) {
        entityManager.remove(mission);
    }
}
