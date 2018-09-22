package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public List<Trip> getTripsBy(User someUser, User loggedInUser) throws UserNotLoggedInException {
        validate(loggedInUser);

        return someUser.isFriendWith(loggedInUser)
            ? tripsBy(someUser)
            : noTrips();
    }

    private void validate(User loggedInUser) {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }
    }

    private List<Trip> noTrips() {
        return new ArrayList<Trip>();
    }

    protected List<Trip> tripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }

}
