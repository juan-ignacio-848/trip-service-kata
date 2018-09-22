package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_MEXICO = new Trip();
    private static final Trip TO_AUSTRALIA = new Trip();

    @Mock
    private TripDAO tripDAO;

    @InjectMocks
    @Spy
    private TripService tripService = new TripService();

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {
        tripService.getFriendTrips(UNUSED_USER, GUEST);
    }

    @Test
    public void should_not_return_any_trips_when_users_are_not_friends() {
        User susan = aUser()
                        .friendsWith(ANOTHER_USER)
                        .withTrips(TO_MEXICO)
                        .build();

        final List<Trip> friendTrips = tripService.getFriendTrips(susan, REGISTERED_USER);

        assertThat(friendTrips.size(), is(0));
    }

    @Test
    public void should_return_friend_trips_when_users_are_friends() {
        User susan = aUser()
                        .friendsWith(ANOTHER_USER, REGISTERED_USER)
                        .withTrips(TO_MEXICO, TO_AUSTRALIA)
                        .build();

        given(tripDAO.tripsBy(susan)).willReturn(susan.trips());

        final List<Trip> friendTrips = tripService.getFriendTrips(susan, REGISTERED_USER);

        assertThat(friendTrips.size(), is(2));
    }

}
