package com.enocholumide.controllers;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.repositories.NewsRepository;
import com.enocholumide.repositories.UsersRepository;
import com.enocholumide.services.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/organisations/{orgID}/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NewsService newsService;

    @GetMapping(value = "", name = "Get all news")
    private ResponseEntity getAllNews(){
        return this.newsService.listAll();
    }

    @PutMapping(value = "/{news_id}/comments/add/applicationUser/{user_id}", name = "Add comment to a news")
    private ResponseEntity addComment(@NotNull @RequestBody Comment comment, @PathVariable long news_id, @PathVariable long user_id){
        return newsService.addComment(comment, news_id, user_id);
    }
}
