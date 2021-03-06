package com.ahmad.springit.controller;

import com.ahmad.springit.Service.CommentService;
import com.ahmad.springit.Service.LinkService;
import com.ahmad.springit.domain.Comment;
import com.ahmad.springit.domain.Link;
import com.ahmad.springit.repository.CommentRepository;
import com.ahmad.springit.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class LinkController {
    private LinkService linkService; //we could have linkRepository here instead, but we do this for refactoring and to transfer some of the logic to the service layer so instead of calling the linkRepository directly we call the service layer which calls the repo
    private CommentService commentService;

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);


    public LinkController(LinkService linkService, CommentService commentService) {
        this.linkService = linkService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links", linkService.findAll());
        return "link/list";
    }

    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id,Model model) {
        Optional<Link> link = linkService.findById(id);
        if( link.isPresent() ) {
            Link currentLink = link.get();
            Comment comment = new Comment();
            comment.setLink(currentLink);
            model.addAttribute("comment",comment);
            model.addAttribute("link",currentLink);
            model.addAttribute("success", model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link",new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            logger.info("Validation errors were found");
            model.addAttribute("link",link);
            return "link/submit";
        } else {
            linkService.save(link);
            logger.info("New link was saved successfully");
            redirectAttributes.addAttribute("id",link.getId()).addFlashAttribute("success",true);
            return "redirect:/";
        }
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/link/comments")
    public String addComment(@Valid Comment comment, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.info("Error adding new comment");
        }
        else{
            commentService.save(comment);
            logger.info("Added comment successfully");
        }
        return "redirect:/link/" + comment.getLink().getId();
    }
}
