package cz.muni.fi.dto;

import cz.muni.fi.enums.ComponentType;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Object used while updating Components
 *
 * @author Martin Kostka
 */
public class UpdateComponentDTO {

    @NotNull
    private String name;
    private Long id;
    private boolean readyToUse;
    private ComponentType type;
    private ZonedDateTime readyDate;
    private MissionDTO mission;
    private RocketDTO rocket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isReadyToUse() {
        return readyToUse;
    }

    public void setReadyToUse(boolean readyToUse) {
        this.readyToUse = readyToUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public ZonedDateTime getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(ZonedDateTime readyDate) {
        this.readyDate = readyDate;
    }

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    public RocketDTO getRocket() {
        return rocket;
    }

    public void setRocket(RocketDTO rocket) {
        this.rocket = rocket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateComponentDTO that = (UpdateComponentDTO) o;
        return getName().equals(that.getName()) && getReadyDate().equals(that.getReadyDate()) && Objects.equals(getMission(), that.getMission()) && Objects.equals(getRocket(), that.getRocket());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getReadyDate(), getMission(), getRocket());
    }
}
