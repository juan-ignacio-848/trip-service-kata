package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

public class UserBuilder {

    private User[] friends;
    private Trip[] trips;

    private UserBuilder() {}

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder friendWith(User... friends) {
        this.friends = friends;
        return this;
    }


    public UserBuilder withTrips(Trip... trips) {
        this.trips = trips;
        return this;
    }

    public User build() {
        User user = new User();
        addTripsTo(user);
        addFriendsTo(user);
        return user;
    }

    private void addTripsTo(User user) {
        for (Trip trip : trips) {
            user.addTrip(trip);
        }
    }

    private void addFriendsTo(User user) {
        for(User friend : friends) {
            user.addFriend(friend);
        }
    }
}
