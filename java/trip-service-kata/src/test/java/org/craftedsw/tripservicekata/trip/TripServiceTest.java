package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TripServiceTest {

    private User loggedInUser;
    private final User UNUSED_USER = null;
    private final User GUEST = null;
    private final User LOGGED_IN_USER = new User();
    private final User CAROL = new User();
    private final Trip TO_BOLIVIA = new Trip();

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {
        TripService tripService = new TestableTripService();

        loggedInUser = GUEST;

        tripService.getTripsByUser(UNUSED_USER);
    }

    @Test
    public void should_return_no_trips_when_user_is_not_a_friend() {
        TripService tripService = new TestableTripService();

        loggedInUser = LOGGED_IN_USER;

        User anotherUser = new User();
        anotherUser.addFriend(CAROL);
        anotherUser.addTrip(TO_BOLIVIA);

        final List<Trip> anotherUserTrips = tripService.getTripsByUser(anotherUser);
        assertThat(anotherUserTrips.size(), is(0));
    }

    private class TestableTripService extends TripService {

        @Override
        protected User getLoggedInUser() {
            return loggedInUser;
        }
    }
}
