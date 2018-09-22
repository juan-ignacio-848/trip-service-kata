package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TripServiceTest {

    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_MEXICO = new Trip();

    private User loggedInUser;
    private TripService tripService;

    @Before
    public void setUp() {
        tripService = new TestableTripService();
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {
        loggedInUser = GUEST;

        tripService.getTripsByUser(UNUSED_USER);
    }

    @Test
    public void should_not_return_any_trips_when_users_are_not_friends() {
        loggedInUser = REGISTERED_USER;

        User susan = new User();
        susan.addFriend(ANOTHER_USER);
        susan.addTrip(TO_MEXICO);

        final List<Trip> friendTrips = tripService.getTripsByUser(susan);

        assertThat(friendTrips.size(), is(0));
    }

    private class TestableTripService extends TripService {
        @Override
        protected User getLoggedInUser() {
            return loggedInUser;
        }
    }
}
