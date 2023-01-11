package com.topjava.lunchroom_vote.controller;

import com.topjava.lunchroom_vote.service.RestaurantService;
import com.topjava.lunchroom_vote.to.RestaurantTo;
import com.topjava.lunchroom_vote.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Alima-T 12/25/2022
 */
@RestController
@RequestMapping(value = RestaurantUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantUserRestController {

    static final String REST_URL = "/api/restaurants";

    private final RestaurantService service;

    public RestaurantUserRestController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping("/menu")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("getAllWithMenu");
        return RestaurantUtil.getTosWithMenu(service.getAll());
    }

    @GetMapping("/{id}/menu")
    public RestaurantTo getMenuOfDay(@PathVariable int id) {
        log.info("getMenuOfDay for restaurant {}", id);
        return RestaurantUtil.createToWithMenu(service.getMenuOfDay(id));
    }
}
