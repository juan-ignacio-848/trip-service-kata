package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    private final User NOT_LOGGED_IN_USER = null;
    private final User LOGGED_IN_USER = new User();
    private final User CARL = new User();

    private final Trip TO_PERU = new Trip();
    private final Trip TO_SPAIN = new Trip();

    @Mock
    private TripDAO tripDAO;

    @InjectMocks
    private TripService realTripService = new TripService();

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {
        realTripService.retrieveTripsFrom(CARL, NOT_LOGGED_IN_USER);
    }

    @Test
    public void should_return_any_trips_when_users_are_not_friends() {
        User bob = aUser()
                .friendsWith(CARL)
                .withTrips(TO_PERU)
                .build();


        List<Trip> friendTrips = realTripService.retrieveTripsFrom(bob, LOGGED_IN_USER);
        assertThat(friendTrips.size(), is(0));
    }

    @Test
    public void should_return_friend_trips_when_users_are_friends() {
        User bob = aUser()
                .friendsWith(CARL, LOGGED_IN_USER)
                .withTrips(TO_PERU, TO_SPAIN)
                .build();

        given(tripDAO.tripsBy(bob)).willReturn(bob.trips());

        List<Trip> friendTrips = realTripService.retrieveTripsFrom(bob, LOGGED_IN_USER);
        assertThat(friendTrips.size(), is(2));
    }

}
