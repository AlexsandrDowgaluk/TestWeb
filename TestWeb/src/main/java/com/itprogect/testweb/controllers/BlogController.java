package com.itprogect.testweb.controllers;
import com.itprogect.testweb.models.BlogPost;
import com.itprogect.testweb.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<BlogPost> blogPosts = blogPostRepository.findAll();
        model.addAttribute("blogPost", blogPosts);
        return "blog-main";

    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
        public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
            BlogPost post=new BlogPost(title,anons,full_text);
            blogPostRepository.save(post);
        return "redirect:/blog";
    }
    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value ="id") long id, Model model){
        if(!blogPostRepository.existsById(id)){
            return "redirect:/blog";
        }
    Optional<BlogPost> post = blogPostRepository.findById(id);
        ArrayList<BlogPost> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }
    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value ="id") long id, Model model){
        if(!blogPostRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<BlogPost> post = blogPostRepository.findById(id);
        ArrayList<BlogPost> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value ="id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        BlogPost post = blogPostRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        blogPostRepository.save(post);
        return "redirect:/blog";
    }
    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value ="id") long id, Model model) {
        BlogPost post = blogPostRepository.findById(id).orElseThrow();
        blogPostRepository.delete(post);
        return "redirect:/blog";
    }
}
