package cz.cvut.fel.rsp.travelandwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ACHIEVEMENT_SPECIAL")
public class AchievementSpecial extends Achievement {

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "achievement_special_owned_travel_journals",
        joinColumns = @JoinColumn(name = "achievement_special_id"),
        inverseJoinColumns = @JoinColumn(name = "traveljournal_id"))
    private List<TravelJournal> owned_travel_journals;

    @ManyToMany
    @JoinTable(
            name = "recieved_achievement_special_trip",
            joinColumns = @JoinColumn(name = "achievement_special_id"),
            inverseJoinColumns = @JoinColumn(name = "enrollment_id"))
    private List<Enrollment> recieved_via_enrollments;

    public AchievementSpecial() {
    }

    public AchievementSpecial(String name, String description, String icon) {
        super(name, description, icon);
    }

    public List<TravelJournal> getOwned_travel_journals() {
        return owned_travel_journals;
    }

    public void setOwned_travel_journals(List<TravelJournal> owned_travel_journals) {
        this.owned_travel_journals = owned_travel_journals;
    }

    public List<Enrollment> getRecieved_via_enrollments() {
        return recieved_via_enrollments;
    }

    public void setRecieved_via_enrollments(List<Enrollment> recieved_via_enrollments) {
        this.recieved_via_enrollments = recieved_via_enrollments;
    }
}
