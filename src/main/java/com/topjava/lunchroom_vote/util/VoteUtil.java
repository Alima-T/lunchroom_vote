package com.topjava.lunchroom_vote.util;

import com.topjava.lunchroom_vote.model.Vote;
import com.topjava.lunchroom_vote.to.VoteTo;

import java.util.Collection;
import java.util.List;

/**
 * @Alima-T 12/25/2022
 */
public class VoteUtil {

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream().map(VoteUtil::createTo).toList();
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVoteDate(), vote.getUser().getId(), vote.getRestaurant().getId());
    }
}
