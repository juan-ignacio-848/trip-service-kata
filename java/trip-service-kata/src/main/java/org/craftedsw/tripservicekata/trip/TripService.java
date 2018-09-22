package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedInUser = getLoggedInUser();
	    if(loggedInUser == null) {
			throw new UserNotLoggedInException();
        }

		List<Trip> tripList = new ArrayList<Trip>();

        if (user.isFriendWith(loggedInUser)) {
            tripList = tripsBy(user);
        }
        return tripList;
	}

    protected List<Trip> tripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
