package cz.muni.fi.dto;

import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.Role;

import java.util.Objects;

/**
 * User create DTO.
 *
 * @author Martin Hořelka (469003)
 */
public class UserCreateDTO {

    private String name;
    private String email;
    private String password;
    private Role role;
    private LevelOfExperience levelOfExperience;
    private MissionDTO mission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LevelOfExperience getLevelOfExperience() {
        return levelOfExperience;
    }

    public void setLevelOfExperience(LevelOfExperience levelOfExperience) {
        this.levelOfExperience = levelOfExperience;
    }

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCreateDTO)) return false;
        UserCreateDTO other = (UserCreateDTO) o;
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", levelOfExperience=" + levelOfExperience +
                ", mission=" + mission +
                '}';
    }
}
