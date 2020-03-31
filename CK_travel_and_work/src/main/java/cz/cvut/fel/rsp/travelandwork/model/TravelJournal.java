package cz.cvut.fel.rsp.travelandwork.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "TRAVELJOURNAL")
public class TravelJournal extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private int xp_count;
    @Basic(optional = false)
    @Column(nullable = false)
    private HashMap<Category, Integer> trip_counter;

    @OneToOne(mappedBy = "travel_journal")
    private User user;

    @ManyToMany
    private List<Achievement> earnedAchievements;

    public int getXp_count() {
        return xp_count;
    }

    public HashMap<Category, Integer> getTrip_counter() {
        return trip_counter;
    }

    public User getUser() {
        return user;
    }

    public List<Achievement> getEarnedAchievements() {
        return earnedAchievements;
    }

    public void setXp_count(int xp_count) {
        this.xp_count = xp_count;
    }

    public void setTrip_counter(HashMap<Category, Integer> trip_counter) {
        this.trip_counter = trip_counter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEarnedAchievements(List<Achievement> earnedAchievements) {
        this.earnedAchievements = earnedAchievements;
    }

    /**
     * Adds trip to travel journal
     * If travel journal already contains the category, adds one more.
     * If doesn't, adds a new category counted with one trip in there.
     */
    public void addTrip(Trip trip){
        int actualValue = findAndGetCategoryValueIfExists(trip.getCategory());
        if(actualValue != -1) {
            actualValue++;
            this.trip_counter.put(trip.getCategory(), actualValue);
        }
        else{
            this.trip_counter.put(trip.getCategory(), 1);
        }
        addsXp(trip.getPossible_xp_reward());
    }

    private int findAndGetCategoryValueIfExists(Category category){
        for (Category key: this.trip_counter.keySet()) {
            if(key.equals(category)){
                return this.trip_counter.get(key);
            }
        }
        return -1;
    }

    private void addsXp(int xp){
        this.xp_count += xp;
    }
}
