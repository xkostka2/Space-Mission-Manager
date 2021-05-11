package cz.muni.fi.dto.mission;

import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.enums.MissionProgress;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for updating of the mission
 *
 * @author Martin Kazimir
 */
public class UpdateMissionDTO {

    private long id;
    private String name;
    private String destination;
    private MissionProgress missionProgress;
    private Set<UserDTO> users = new HashSet<>();
    private Set<RocketDTO> rockets = new HashSet<>();
    private Set<ComponentDTO> components = new HashSet<>();
    private ZonedDateTime eta;
    private ZonedDateTime finishedDate;
    private ZonedDateTime startedDate;
    private String result;

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void addUser(UserDTO userDTO){
        users.add(userDTO);
    }

    public void removeUser(UserDTO userDTO){
        users.remove(userDTO);
    }

    public Set<RocketDTO> getRockets() {
        return rockets;
    }

    public void addRocket(RocketDTO rocketDTO){
        rockets.add(rocketDTO);
    }

    public void removeRocket(RocketDTO rocketDTO){
        rockets.remove(rocketDTO);
    }

    public Set<ComponentDTO> getComponents() {
        return components;
    }

    public void addComponent(ComponentDTO componentDTO){
        components.add(componentDTO);
    }

    public void removeComponent(ComponentDTO componentDTO){
        components.remove(componentDTO);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public MissionProgress getMissionProgress() {
        return missionProgress;
    }

    public void setMissionProgress(MissionProgress missionProgress) {
        this.missionProgress = missionProgress;
    }

    public ZonedDateTime getEta() {
        return eta;
    }

    public void setEta(ZonedDateTime eta) {
        this.eta = eta;
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
        if (!(o instanceof MissionDTO)) return false;
        MissionDTO that = (MissionDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "MissionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", missionProgress=" + missionProgress +
                ", users=" + users +
                ", rockets=" + rockets +
                ", components=" + components +
                ", eta=" + eta +
                ", finishedDate=" + finishedDate +
                ", startedDate=" + startedDate +
                ", result='" + result + '\'' +
                '}';
    }
}
