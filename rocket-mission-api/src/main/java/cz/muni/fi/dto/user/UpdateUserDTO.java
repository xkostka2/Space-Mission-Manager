package cz.muni.fi.dto.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.Role;

import java.util.Objects;

/**
 * User update DTO.
 *
 * @author Martin Hořelka (469003)
 */
public class UpdateUserDTO {

    private long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LevelOfExperience levelOfExperience;
    private MissionDTO mission;
    private boolean missionAccepted;
    private String missionExplanation;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    public boolean getMissionAccepted() {
        return missionAccepted;
    }

    public void setMissionAccepted(boolean missionAccepted) {
        this.missionAccepted = missionAccepted;
    }

    public String getMissionExplanation() {
        return missionExplanation;
    }

    public void setMissionExplanation(String missionExplanation) {
        this.missionExplanation = missionExplanation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateUserDTO)) return false;
        UpdateUserDTO other = (UpdateUserDTO) o;
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "UpdateUserDTO{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", levelOfExperience=" + levelOfExperience +
                ", mission=" + mission +
                '}';
    }
}
