package com.ahmad.springit.controller;

import com.ahmad.springit.domain.Link;
import com.ahmad.springit.repository.LinkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/links")
public class LinkController {
    private LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links", linkRepository.findAll());
        return "link/list";
    }

//    @GetMapping("/link/${id}")
//    public String read(@PathVariable Long id, Model model) {
//        Optional<Link> link = linkRepository.findById(id);
//        if (link.isPresent()) {
//            model.addAttribute("link", link.get());
////            model.addAttribute("success", model.containsAttribute("success"));
//            return "link/view";
//        }
//        else{
//            return "redirect:/";
//        }
//    }
//
//    @GetMapping("/")
//    public List<Link> list(){
//        return linkRepository.findAll();
//    }
//
//    //CRUD
//    @PostMapping("/create")
//    public Link create(@ModelAttribute Link link){
//        return linkRepository.save(link);
//    }
//    @GetMapping("/{id}")
//    public Optional<Link> read(@PathVariable Long id){
//        return linkRepository.findById(id);
//    }
//    @PutMapping("/{id}")
//    public Link update(@PathVariable Long id, @ModelAttribute Link link){
//        return linkRepository.save(link);
//    }
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id){
//        linkRepository.deleteById(id);
//    }
}
