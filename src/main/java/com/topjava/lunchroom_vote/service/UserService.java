package com.topjava.lunchroom_vote.service;

import com.topjava.lunchroom_vote.AuthorizedUser;
import com.topjava.lunchroom_vote.model.User;
import com.topjava.lunchroom_vote.repository.UserRepository;
import com.topjava.lunchroom_vote.to.UserTo;
import com.topjava.lunchroom_vote.util.UserUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.topjava.lunchroom_vote.util.UserUtil.prepareToSave;
import static com.topjava.lunchroom_vote.util.ValidationUtil.checkNotFound;
import static com.topjava.lunchroom_vote.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email can not be empty");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public User create(User user) {
        Assert.notNull(user, "User can not be empty");
        return prepareAndSave(user);
    }

    public void update(User user) {
        Assert.notNull(user, "User can not be empty");
        prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }


}
