package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TripServiceTest {

    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_MEXICO = new Trip();
    private static final Trip TO_AUSTRALIA = new Trip();

    private User loggedInUser;
    private TripService tripService;

    @Before
    public void setUp() {
        tripService = new TestableTripService();
        loggedInUser = REGISTERED_USER;
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {
        loggedInUser = GUEST;

        tripService.getTripsByUser(UNUSED_USER);
    }

    @Test
    public void should_not_return_any_trips_when_users_are_not_friends() {
        User susan = aUser()
                        .friendsWith(ANOTHER_USER)
                        .withTrips(TO_MEXICO)
                        .build();

        final List<Trip> friendTrips = tripService.getTripsByUser(susan);

        assertThat(friendTrips.size(), is(0));
    }

    @Test
    public void should_return_friend_trips_when_users_are_friends() {

        User susan = aUser()
                        .friendsWith(ANOTHER_USER, loggedInUser)
                        .withTrips(TO_MEXICO, TO_AUSTRALIA)
                        .build();

        final List<Trip> friendTrips = tripService.getTripsByUser(susan);

        assertThat(friendTrips.size(), is(2));
    }

    private class TestableTripService extends TripService {
        @Override
        protected User getLoggedInUser() {
            return loggedInUser;
        }

        @Override
        public List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }

}
