package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {

    @Test
    public void should_return_false_when_another_user_is_not_a_friend() {
        User user = new User();
        User notAFriend = new User();
        assertThat(user.isFriendWith(notAFriend), is(false));
    }

    @Test
    public void should_return_true_when_another_user_is_a_friend() {
        User aFriend = new User();

        User user = new User();
        user.addFriend(aFriend);

        assertThat(user.isFriendWith(aFriend), is(true));
    }
}
