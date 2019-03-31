package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.craftedsw.tripservicekata.trip.TripServiceShould.UserBuilder.aUser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TripServiceShould {

    private static final User NOT_LOGGED_IN_USER = null;
    private static final User LOGGED_IN_USER = new User();
    private static final User BOB = new User();
    private static final List NO_TRIPS = EMPTY_LIST;
    private static final Trip BRAZIL = new Trip();
    private static final Trip COLOMBIA = new Trip();

    private TripService tripService;

    @Before
    public void setUp() {
        tripService = new TestableTripService(LOGGED_IN_USER);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void not_try_to_find_trips_when_user_is_not_logged_in() {
        tripService = new TestableTripService(NOT_LOGGED_IN_USER);

        tripService.getTripsBy(BOB);
    }

    @Test
    public void not_try_to_find_trips_when_users_are_not_friends() {
        User carl = aUser()
                        .withFriends(BOB)
                        .withTripsTo(COLOMBIA)
                        .build();

        List<Trip> trips = tripService.getTripsBy(carl);

        assertThat(trips, is(NO_TRIPS));
    }


    @Test
    public void find_friend_trips() {
        User carl = aUser()
                        .withFriends(LOGGED_IN_USER, BOB)
                        .withTripsTo(COLOMBIA, BRAZIL)
                        .build();

        List<Trip> trips = tripService.getTripsBy(carl);

        assertThat(trips.size(), is(2));
    }

    static class UserBuilder {

        private User[] friends;
        private Trip[] trips;

        static UserBuilder aUser() {
            return new UserBuilder();
        }

        UserBuilder withFriends(User... friends) {
            this.friends = friends;
            return this;
        }

        UserBuilder withTripsTo(Trip... trips) {
            this.trips = trips;
            return this;
        }

        User build() {
            User user = new User();
            addFriendsTo(user);
            addTripsTo(user);
            return user;
        }

        private void addTripsTo(User user) {
            for(Trip trip : trips) {
                user.addTrip(trip);
            }
        }

        private void addFriendsTo(User user) {
            for(User friend : friends) {
                user.addFriend(friend);
            }
        }
    }

    private class TestableTripService extends TripService {

        final User loggedUser;

        TestableTripService(User loggedUser) {
            this.loggedUser = loggedUser;
        }

        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }

        @Override
        protected List<Trip> getTripsByUser(User user) {
            return user.trips();
        }
    }
}
