package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TripServiceTest {

    private final User UNUSED_USER = null;
    private final User GUEST = null;
    private final User LOGGED_IN_USER = new User();
    private final User CAROL = new User();

    private final Trip TO_BOLIVIA = new Trip();
    private final Trip TO_CANADA = new Trip();

    private TripService tripService;

    @Before
    public void setUp() {
        tripService = new TestableTripService();
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {
        tripService.getTripsBy(UNUSED_USER, GUEST);
    }

    @Test
    public void should_return_no_trips_when_users_are_not_a_friend() {
        User anotherUser = aUser()
                .friendWith(CAROL)
                .withTrips(TO_BOLIVIA)
                .build();

        final List<Trip> anotherUserTrips = tripService.getTripsBy(anotherUser, LOGGED_IN_USER);
        assertThat(anotherUserTrips.size(), is(0));
    }

    @Test
    public void should_return_friend_trips_when_users_are_friends() {

        User anotherUser = aUser()
                                .friendWith(CAROL, LOGGED_IN_USER)
                                .withTrips(TO_BOLIVIA, TO_CANADA)
                                .build();

        final List<Trip> anotherUserTrips = tripService.getTripsBy(anotherUser, LOGGED_IN_USER);
        assertThat(anotherUserTrips.size(), is(2));
    }

    private class TestableTripService extends TripService {

        @Override
        protected List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }

}
