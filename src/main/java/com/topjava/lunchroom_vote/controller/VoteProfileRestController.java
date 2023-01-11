package com.topjava.lunchroom_vote.controller;

import com.topjava.lunchroom_vote.service.VoteService;
import com.topjava.lunchroom_vote.to.VoteTo;
import com.topjava.lunchroom_vote.util.VoteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

import static com.topjava.lunchroom_vote.util.SecurityUtil.authUserId;

/**
 * @Alima-T 12/26/2022
 */
@RestController
@RequestMapping(value = VoteProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteProfileRestController {

    static final String REST_URL = "/api/profile/votes";

    private final VoteService service;

    public VoteProfileRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping()
    public VoteTo get() {
        log.info("get {}", authUserId());
        return VoteUtil.createTo(service.getByUserAndDate(authUserId(), LocalDate.now()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createWithLocation(@RequestParam int restaurantId) {
        log.info("create vote from user {} for restaurant {}", authUserId(), restaurantId);
        VoteTo created = VoteUtil.createTo(service.create(authUserId(), restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestParam int restaurantId) {
        log.info("update vote {} for restaurant {}", id, restaurantId);
        service.update(id, restaurantId);
    }
}
