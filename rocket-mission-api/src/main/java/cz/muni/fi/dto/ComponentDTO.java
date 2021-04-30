package cz.muni.fi.dto;

import cz.muni.fi.enums.ComponentType;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Component used in missions
 *
 * @author Martin Kostka
 */
public class ComponentDTO {

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
        if (!(o instanceof ComponentDTO)) return false;
        ComponentDTO that = (ComponentDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
