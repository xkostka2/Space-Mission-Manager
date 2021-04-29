package cz.muni.fi.entity;

import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * User entity
 *
 * @author Martin Kostka
 */
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column(unique = true)
    @Pattern(regexp = ".+@.+\\....?")
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private LevelOfExperience levelOfExperience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    private boolean missionAccepted;
    private String missionRejectedExplanation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public LevelOfExperience getLevelOfExperience() {
        return levelOfExperience;
    }

    public void setLevelOfExperience(LevelOfExperience levelOfExperience) {
        this.levelOfExperience = levelOfExperience;
    }

    public boolean getMissionAccepted() {
        return missionAccepted;
    }

    public void setMissionAccepted(boolean missionAccepted) {
        this.missionAccepted = missionAccepted;
    }

    public String getMissionRejectedExplanation() {
        return missionRejectedExplanation;
    }

    public void setMissionRejectedExplanation(String missionRejectedExplanation) {
        this.missionRejectedExplanation = missionRejectedExplanation;
    }

    public boolean missionStatusPending() {
        return getMission() != null && !getMissionAccepted();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", levelOfExperience=" + levelOfExperience +
                ", mission=" + mission +
                '}';
    }
}
