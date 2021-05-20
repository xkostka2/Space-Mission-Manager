package cz.muni.fi.services.impl;

import cz.muni.fi.dao.MissionDao;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
    private MissionDao missionDao;

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public Mission addMission(Mission mission) throws DataAccessException {
        try {
            return missionDao.addMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not create a mission ", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public Mission updateMission(Mission mission) throws DataAccessException {
        try {
            return missionDao.updateMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not update a mission ", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteMission(Mission mission) throws DataAccessException {
        try {
            missionDao.removeMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not delete a mission ", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Mission> findAllMissions() throws DataAccessException {
        try {
            return Collections.unmodifiableList(missionDao.findAllMissions());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all missions ", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Mission> findAllMissions(MissionProgress progress) throws DataAccessException {
        try {
            return Collections.unmodifiableList(missionDao.findAllMissions(progress));
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all missions with progress " + progress, e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public Mission findMissionById(Long id) throws DataAccessException {
        try {
            return missionDao.findMissionById(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find mission with id " + id, e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void archive(Mission mission, ZonedDateTime endDate, String archiveComment) {
        if (mission == null) {
            throw new IllegalArgumentException("Mission can not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("Mission end date can not be null");
        }
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
        });
        new ArrayList<>(mission.getUsers()).forEach(mission::removeUser);


        sb.append("\nRockets: ");
        mission.getRockets().forEach(r -> {
            sb.append("\n").append(r.getName());
        });
        new ArrayList<>(mission.getRockets()).forEach(mission::removeRocket);

        sb.append("\nComponents: ");
        mission.getComponents().forEach(c -> {
            sb.append("\n").append(c.getName());
        });
        new ArrayList<>(mission.getComponents()).forEach(mission::removeComponent);

        sb.append("\nArchive comment: ").append(archiveComment);

        mission.setResult(sb.toString());

        try {
            missionDao.updateMission(mission);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not archive mission " + mission, e);
        }
    }
}