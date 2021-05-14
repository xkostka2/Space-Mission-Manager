package cz.muni.fi.facades;

import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.mission.UpdateMissionDTO;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.facade.MissionFacade;
import cz.muni.fi.services.MissionService;
import cz.muni.fi.services.mapper.ComponentMapper;
import cz.muni.fi.services.mapper.MissionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Implementation of the {@link MissionFacade}.
 *
 * @author Martin Kazimir
 */
@Service
@Transactional
public class MissionFacadeImpl implements MissionFacade {

    private final MissionMapper missionMapper;
    private final MissionService missionService;

    @Autowired
    public MissionFacadeImpl(MissionService missionService, MissionMapper missionMapper) {
        this.missionMapper = missionMapper;
        this.missionService = missionService;
    }

    @Override
    public MissionDTO addMission(CreateMissionDTO mission) {
        Mission mappedMission = missionMapper.createMissionDTOToMission(mission);
        return missionMapper.missionToMissionDTO(missionService.addMission(mappedMission));
    }

    @Override
    public MissionDTO updateMission(UpdateMissionDTO mission) {
        Mission mappedMission = missionMapper.updateMissionDTOToMission(mission);
        return missionMapper.missionToMissionDTO(missionService.updateMission(mappedMission));
    }

    @Override
    public void deleteMission(MissionDTO mission) {
        Mission mappedMission = missionMapper.missionDTOToMission(mission);
        missionService.deleteMission(mappedMission);
    }

    @Override
    @Transactional(readOnly=true)
    public List<MissionDTO> findAllMissions() {
        return missionMapper.missionsToMissionDTOs(missionService.findAllMissions());
    }

    @Override
    @Transactional(readOnly=true)
    public List<MissionDTO> findAllMissions(MissionProgress progress) {
        return missionMapper.missionsToMissionDTOs(missionService.findAllMissions(progress));
    }

    @Override
    @Transactional(readOnly=true)
    public MissionDTO findMissionById(Long id) {
        Mission mission = missionService.findMissionById(id);
        if (mission == null) {
            return null;
        }
        return missionMapper.missionToMissionDTO(mission);
    }

    @Override
    public void archive(MissionDTO mission, ZonedDateTime endDate, String archiveComment) {
        missionService.archive(missionMapper.missionDTOToMission(mission), endDate, archiveComment);
    }
}
