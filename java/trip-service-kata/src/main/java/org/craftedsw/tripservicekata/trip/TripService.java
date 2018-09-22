package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedUser = getLoggedInUser();
		if (loggedUser != null) {

            if (user.isFriendWith(loggedUser)) {
				tripList = tripsBy(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

    protected List<Trip> tripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
