package com.topjava.lunchroom_vote.controller;

import com.topjava.lunchroom_vote.service.VoteService;
import com.topjava.lunchroom_vote.to.VoteTo;
import com.topjava.lunchroom_vote.util.VoteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * @Alima-T 12/26/2022
 */
@RestController
@RequestMapping(value = VoteAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteAdminRestController {

    static final String REST_URL = "/api/admin/votes";

    private final VoteService voteService;

    public VoteAdminRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id) {
        log.info("get {}", id);
        return VoteUtil.createTo(voteService.get(id));
    }

    @GetMapping
    public List<VoteTo> getAll() {
        log.info("getAll");
        return VoteUtil.getTos(voteService.getAll());
    }

    @GetMapping("/date")
    public List<VoteTo> getAllByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                     @RequestParam LocalDate voteDate) {
        log.info("getByUserAndDate");
        return VoteUtil.getTos(voteService.getAllByDate(voteDate));
    }

    @GetMapping("/user-and-date")
    public VoteTo getByUserAndDate(@RequestParam int userId,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @RequestParam LocalDate voteDate) {
        log.info("getByUserAndDate");
        return VoteUtil.createTo(voteService.getByUserAndDate(userId, voteDate));
    }

    @GetMapping("/{restaurantId}/date")
    public List<VoteTo> getByRestaurantAndDate(@PathVariable int restaurantId,
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                               @RequestParam LocalDate voteDate) {
        log.info("getByUserAndDate");
        return VoteUtil.getTos(voteService.getByRestaurantAndDate(restaurantId, voteDate));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createWithLocation(@RequestParam int userId, @RequestParam int restaurantId) {
        log.info("create vote from user {} for restaurant {}", userId, restaurantId);
        VoteTo created = VoteUtil.createTo(voteService.create(userId, restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestParam int restaurantId) {
        log.info("update vote {} for restaurant {}", id, restaurantId);
        voteService.update(id, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        voteService.delete(id);
    }
}
