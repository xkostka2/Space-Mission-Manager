package cz.muni.fi.services.impl;

import cz.muni.fi.dao.MissionDao;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link MissionService}. This class is part of the service
 *  module of the application, that provides the implementation of the business logic.
 *
 * @author Martin Kazimir
 */
@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    MissionDao missionDao;

    @Override
    public void addMission(Mission mission) throws DataAccessException {
        try {
            missionDao.addMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not create a mission ", e);
        }
    }

    @Override
    public void updateMission(Mission mission) throws DataAccessException {
        try {
            missionDao.updateMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not update a mission ", e);
        }
    }

    @Override
    public void deleteMission(Mission mission) throws DataAccessException {
        try {
            missionDao.removeMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not delete a mission ", e);
        }
    }

    @Override
    public List<Mission> findAllMissions() throws DataAccessException {
        try {
            return Collections.unmodifiableList(missionDao.findAllMissions());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all missions ", e);
        }
    }

    @Override
    public List<Mission> findAllMissions(MissionProgress progress) throws DataAccessException {
        try {
            return Collections.unmodifiableList(missionDao.findAllMissions(progress));
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all missions with progress " + progress, e);
        }
    }

    @Override
    public Mission findMissionById(Long id) throws DataAccessException {
        try {
            return missionDao.findMissionById(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find mission with id " + id, e);
        }
    }

    @Override
    public void archive(Mission mission, ZonedDateTime endDate, String archiveComment) {
        if (endDate.isAfter(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Mission end date must be in the past.");
        }
        mission.setFinishedDate(endDate);
        mission.setMissionProgress(MissionProgress.FINISHED);

        StringBuilder sb = new StringBuilder();
        sb.append("Name of mission: ").append(mission.getName())
                .append("\nDestination: ").append(mission.getDestination())
                .append("\nStarted date: ").append(mission.getStartedDate())
                .append("\nEta: ").append(mission.getEta())
                .append("\nEnd date: ").append(mission.getFinishedDate()).append('\n');

        sb.append("\nMembers: ");
        mission.getUsers().forEach(u -> {
            sb.append("\n").append(u.getName()).append(": ").append(u.getRole());
            mission.removeUser(u);
        });

        sb.append("\nRockets: ");
        mission.getRockets().forEach(r -> {
            sb.append("\n").append(r.getName());
            mission.removeRocket(r);
        });

        sb.append("\nComponents: ");
        mission.getComponents().forEach(c -> {
            sb.append("\n").append(c.getName());
            mission.removeComponent(c);
        });

        sb.append("\nArchive comment: ").append(archiveComment);

        mission.setResult(sb.toString());

        try {
            missionDao.updateMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not archive mission " + mission, e);
        }
    }
}