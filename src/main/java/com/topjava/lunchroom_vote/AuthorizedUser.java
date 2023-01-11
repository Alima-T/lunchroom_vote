package com.topjava.lunchroom_vote;

import com.topjava.lunchroom_vote.model.User;
import com.topjava.lunchroom_vote.to.UserTo;
import com.topjava.lunchroom_vote.util.UserUtil;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * @Alima-T 11/30/2022
 */
@ToString
@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        setTo(UserUtil.createTo(user));
    }

    public int getId() {
        return userTo.id();
    }

    public void setTo(UserTo newTo) {
        newTo.setPassword(null);
        userTo = newTo;
    }

}