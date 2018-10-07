package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    private final User REGISTERED_USER = new User();
    private final User SOME_USER = new User();
    private final User GUEST = null;

    private Trip TO_ARGENTINA = new Trip();
    private Trip TO_MEXICO = new Trip();

    @Mock
    private TripDAO tripDAO;

    @InjectMocks
    private TripService realTripService = new TripService();

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_a_guest() {
        realTripService.retrieveTripsDoneBy(SOME_USER, GUEST);
    }

    @Test
    public void should_return_no_trips_when_users_are_not_friends() {
        User MIKE = new User();
        MIKE.addFriend(SOME_USER);
        MIKE.addTrip(TO_ARGENTINA);

        assertThat(realTripService.retrieveTripsDoneBy(MIKE, REGISTERED_USER), is(Collections.EMPTY_LIST));
    }

    @Test
    public void should_return_friend_trips_when_users_are_friends() {
        User MIKE = new User();
        MIKE.addFriend(SOME_USER);
        MIKE.addFriend(REGISTERED_USER);
        MIKE.addTrip(TO_ARGENTINA);
        MIKE.addTrip(TO_MEXICO);

        given(tripDAO.retrieveFriendTripsDoneBy(MIKE)).willReturn(MIKE.trips());

        assertThat(realTripService.retrieveTripsDoneBy(MIKE, REGISTERED_USER).size(), is(2));
    }

}
