package cz.muni.fi.services;

import cz.muni.fi.entity.Mission;

public class MissionServiceImplTest {

    static Mission createMission(String name) {
        Mission mission = new Mission();
        mission.setName(name);
        return mission;
    }
}