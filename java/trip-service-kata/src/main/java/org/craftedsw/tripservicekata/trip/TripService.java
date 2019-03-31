package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

public class TripService {

    private static final ArrayList<Trip> NO_TRIPS = new ArrayList<Trip>();

    private final TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    List<Trip> tripsFrom(final User user, final User loggedUser) throws UserNotLoggedInException {
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendWith(loggedUser) ? tripDAO.tripsBy(user)
                                             : NO_TRIPS;
    }

}
