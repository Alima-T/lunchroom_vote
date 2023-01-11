package com.topjava.lunchroom_vote.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.topjava.lunchroom_vote.json.View;
import com.topjava.lunchroom_vote.model.User;
import com.topjava.lunchroom_vote.service.UserService;
import com.topjava.lunchroom_vote.to.UserTo;
import com.topjava.lunchroom_vote.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.topjava.lunchroom_vote.util.SecurityUtil.authUserId;
import static com.topjava.lunchroom_vote.util.ValidationUtil.assureIdConsistent;

/**
 * @Alima-T 12/26/2022
 */

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileRestController {

    static final String REST_URL = "/api/user/profile";

    private final UserService service;

    public ProfileRestController(@Qualifier("userService") UserService service) {
        this.service = service;
    }

    @GetMapping
    @JsonView(View.JsonREST.class)
    public UserTo get() {
        log.info("get {}", authUserId());
        return UserUtil.createTo(service.get(authUserId()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = service.create(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        log.info("update {} with id={}", userTo, authUserId());
        assureIdConsistent(userTo, authUserId());
        service.update(userTo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        log.info("delete {}", authUserId());
        service.delete(authUserId());
    }

}