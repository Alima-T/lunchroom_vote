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
import java.util.List;

import static com.topjava.lunchroom_vote.util.ValidationUtil.assureIdConsistent;
import static com.topjava.lunchroom_vote.util.ValidationUtil.checkNew;

/**
 * @Alima-T 12/26/2022
 */
@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestController {

    static final String REST_URL = "/api/admin/users";

    private final UserService service;

    public AdminRestController(@Qualifier("userService") UserService service) {
        this.service = service;
    }

    @GetMapping
    @JsonView(View.JsonREST.class)
    public List<UserTo> getAll() {
        log.info("getAll");
        return UserUtil.getTos(service.getAll());
    }

    @GetMapping("/{id}")
    @JsonView(View.JsonREST.class)
    public UserTo get(@PathVariable int id) {
        log.info("get {}", id);
        return UserUtil.createTo(service.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create {}", user);
        checkNew(user);
        User created = service.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/by-email")
    @JsonView(View.JsonREST.class)
    public UserTo getByEmail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        return UserUtil.createTo(service.getByEmail(email));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }
}