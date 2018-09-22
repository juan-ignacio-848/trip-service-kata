package org.craftedsw.tripservicekata.trip;

import java.util.List;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.User;

public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new CollaboratorCallException(
				"TripDAO should not be invoked on an unit test.");
	}

	// We will start moving our code to use this instance method
	// until the only reference to the static method is the one
	// inside "tripsBy(user)".
	// Then we will replace the static call with the code inside
	// the static method and then safely remove it.
	public List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}
}