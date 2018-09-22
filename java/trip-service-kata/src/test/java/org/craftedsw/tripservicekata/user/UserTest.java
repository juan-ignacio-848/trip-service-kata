package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {

    private static final User SUSAN = new User();
    private static final User MIKE = new User();

    @Test
    public void should_return_false_when_users_are_not_friends() {
        User user = aUser()
                        .friendsWith(SUSAN)
                        .build();
        assertThat(user.isFriendWith(MIKE), is(false));
    }

    @Test
    public void should_return_true_when_users_are_friends() {
        User user = aUser()
                .friendsWith(SUSAN, MIKE)
                .build();
        assertThat(user.isFriendWith(MIKE), is(true));
    }
}
