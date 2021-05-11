package cz.muni.fi.facades;

import cz.muni.fi.dto.MissionDTO;
import cz.muni.fi.dto.create.CreateMissionDTO;
import cz.muni.fi.dto.update.UpdateMissionDTO;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.facade.MissionFacade;
import cz.muni.fi.services.BeanMappingService;
import cz.muni.fi.services.MissionService;
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
    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private MissionService missionService;

    @Override
    public MissionDTO addMission(CreateMissionDTO mission) {
        Mission mappedMission = beanMappingService.mapTo(mission, Mission.class);
        return beanMappingService.mapTo(missionService.addMission(mappedMission), MissionDTO.class);

    }

    @Override
    public MissionDTO updateMission(UpdateMissionDTO mission) {
        Mission mappedMission = beanMappingService.mapTo(mission, Mission.class);
        return beanMappingService.mapTo(missionService.updateMission(mappedMission), MissionDTO.class);
    }

    @Override
    public void deleteMission(MissionDTO mission) {
        Mission mappedMission = beanMappingService.mapTo(mission, Mission.class);
        missionService.deleteMission(mappedMission);
    }

    @Override
    @Transactional(readOnly=true)
    public List<MissionDTO> findAllMissions() {
        return beanMappingService.mapTo(missionService.findAllMissions(), MissionDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<MissionDTO> findAllMissions(MissionProgress progress) {
        return beanMappingService.mapTo(missionService.findAllMissions(progress), MissionDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public MissionDTO findMissionById(Long id) {
        Mission mission = missionService.findMissionById(id);
        if (mission == null) {
            return null;
        }
        return beanMappingService.mapTo(mission, MissionDTO.class);
    }

    @Override
    public void archive(MissionDTO mission, ZonedDateTime endDate, String archiveComment) {
        missionService.archive(beanMappingService.mapTo(mission, Mission.class), endDate, archiveComment);
    }
}
