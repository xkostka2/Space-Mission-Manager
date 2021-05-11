package cz.muni.fi.dto.mission;

import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.enums.MissionProgress;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for creating of the mission
 *
 * @author Martin Kazimir
 */
public class CreateMissionDTO {

    @NotNull
    @Size(min = 2, max = 60)
    private String name;

    @NotNull
    @Size(min = 2, max = 255)
    private String destination;

    @NotNull
    private Set<UserDTO> users = new HashSet<>();

    @NotNull
    @Size(min = 1)
    private Set<RocketDTO> rockets = new HashSet<>();

    @NotNull
    private Set<ComponentDTO> components = new HashSet<>();

    @NotNull
    @Future
    private ZonedDateTime eta;

    private MissionProgress missionProgress;
    private ZonedDateTime finishedDate;
    private ZonedDateTime startedDate;
    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public Set<RocketDTO> getRockets() {
        return rockets;
    }

    public void setRockets(Set<RocketDTO> rockets) {
        this.rockets = rockets;
    }

    public Set<ComponentDTO> getComponents() {
        return components;
    }

    public void setComponents(Set<ComponentDTO> components) {
        this.components = components;
    }

    public ZonedDateTime getEta() {
        return eta;
    }

    public void setEta(ZonedDateTime eta) {
        this.eta = eta;
    }

    public MissionProgress getMissionProgress() {
        return missionProgress;
    }

    public void setMissionProgress(MissionProgress missionProgress) {
        this.missionProgress = missionProgress;
    }

    public ZonedDateTime getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(ZonedDateTime finishedDate) {
        this.finishedDate = finishedDate;
    }

    public ZonedDateTime getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(ZonedDateTime startedDate) {
        this.startedDate = startedDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateMissionDTO)) return false;
        CreateMissionDTO that = (CreateMissionDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
