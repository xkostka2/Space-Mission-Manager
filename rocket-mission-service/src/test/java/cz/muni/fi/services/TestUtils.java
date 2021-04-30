package cz.muni.fi.services;

import cz.muni.fi.entity.Component;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.entity.User;

public class TestUtils {
    static User createUser(String name, Mission mission) {
        User user = UserServiceImplTest.createUser(name);
        user.setMission(mission);
        mission.addUser(user);

        return user;
    }

    static Rocket createRocket(String name, Mission mission) {
        Rocket rocket = createRocket(name);
        mission.addRocket(rocket);
        return rocket;
    }

    static Component createComponent(String name) {
        Component craftComponent = new Component();
        craftComponent.setName(name);
        return craftComponent;
    }

    static Component createComponent(String name, Rocket rocket) {
        Component component = createComponent(name);
        component.setRocket(rocket);
        return component;
    }

    static Rocket createRocket(String name) {
        Rocket rocket = new Rocket();
        rocket.setName(name);
        return rocket;
    }
}
