package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    @Autowired
    private TripDAO tripDAO;

    public List<Trip> retrieveTripsFrom(User possibleFriend, User loggedInUser) throws UserNotLoggedInException {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }

        return possibleFriend.isFriendsWith(loggedInUser)
                ? tripsBy(possibleFriend)
                : noTrips();
    }

    protected ArrayList<Trip> noTrips() {
        return new ArrayList<Trip>();
    }

    protected List<Trip> tripsBy(User user) {
        return tripDAO.tripsBy(user);
    }

}
