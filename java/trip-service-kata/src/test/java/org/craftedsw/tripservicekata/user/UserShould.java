package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.craftedsw.tripservicekata.UserBuilder.aUser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserShould {

    private static final User CARL = new User();
    private static final User MIKE = new User();

    @Test
    public void inform_when_users_are_not_friends() {
        User bob = aUser()
                        .withFriends(CARL)
                        .build();

        assertThat(bob.isFriendWith(MIKE), is(false));
    }

    @Test
    public void inform_when_users_are_friends() {
        User bob = aUser()
                .withFriends(CARL, MIKE)
                .build();

        assertThat(bob.isFriendWith(MIKE), is(true));
    }
}
