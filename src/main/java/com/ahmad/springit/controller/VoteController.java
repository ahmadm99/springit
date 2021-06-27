package com.ahmad.springit.controller;

import com.ahmad.springit.Service.LinkService;
import com.ahmad.springit.Service.VoteService;
import com.ahmad.springit.domain.Link;
import com.ahmad.springit.domain.Vote;
import com.ahmad.springit.repository.LinkRepository;
import com.ahmad.springit.repository.VoteRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    private VoteService voteService;
    private LinkService linkService;

    public VoteController(VoteService voteService, LinkService linkService) {
        this.voteService = voteService;
        this.linkService = linkService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/vote/link/{linkId}/direction/{direction}/votecount/{voteCount}")
    public int vote(@PathVariable Long linkId, @PathVariable short direction, @PathVariable int voteCount) {
        Optional<Link> optionalLink = linkService.findById(linkId);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Vote vote = new Vote(direction, link);
            voteService.save(vote);

            int updatedVoteCount = direction + voteCount;
            link.setVoteCount(updatedVoteCount);
            linkService.save(link);
            return updatedVoteCount;
        }
        return voteCount;
    }
}
