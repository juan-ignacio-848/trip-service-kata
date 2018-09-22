package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

public class TripServiceTest {

    private User loggedInUser;
    private User UNUSED_USER = null;
    private User GUEST = null;

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {
        TripService tripService = new TestableTripService();

        loggedInUser = GUEST;

        tripService.getTripsByUser(UNUSED_USER);
    }

    private class TestableTripService extends TripService {

        @Override
        protected User getLoggedInUser() {
            return loggedInUser;
        }
    }
}
