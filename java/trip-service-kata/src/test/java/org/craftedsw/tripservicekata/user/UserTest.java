package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void should_inform_when_users_are_not_friends() {
        User user = new User();
        User anotherUser = new User();
        assertThat(user.isFriendWith(anotherUser), is(false));
    }

    @Test
    public void should_inform_when_users_are_friends() {
        User user = new User();
        User anotherUser = new User();
        user.addFriend(anotherUser);
        assertThat(user.isFriendWith(anotherUser), is(true));
    }
}
