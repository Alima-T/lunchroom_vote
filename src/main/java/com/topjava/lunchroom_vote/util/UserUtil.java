package com.topjava.lunchroom_vote.util;

import com.topjava.lunchroom_vote.model.Role;
import com.topjava.lunchroom_vote.model.User;
import com.topjava.lunchroom_vote.to.UserTo;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

/**
 * @Alima-T 12/25/2022
 */
@NoArgsConstructor
public class UserUtil {

    public static List<UserTo> getTos(Collection<User> users) {
        return users.stream().map(UserUtil::createTo).toList();
    }

    public static UserTo createTo(User user) {
        LocalDate dateConverted = user.getRegistered().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                user.isEnabled(), dateConverted, user.getRoles());
    }

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
