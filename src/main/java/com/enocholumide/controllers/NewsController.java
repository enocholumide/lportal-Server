package com.enocholumide.controllers;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.domain.news.News;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.repositories.NewsRepository;
import com.enocholumide.repositories.UsersRepository;
import com.enocholumide.services.news.NewsService;
import com.enocholumide.services.news.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NewsService newsService;

    @GetMapping(value = "/api/news", name = "Get all news")
    private ResponseEntity<List<News>> getAllNews(){
        return ResponseEntity.ok().body(this.newsService.listAll());
    }

    @CrossOrigin("http://localhost:3000")
    @PutMapping(value = "/api/news/{news_id}/comments/add/user/{user_id}", name = "Add comment to a news")
    private ResponseEntity<Object> addComment(@NotNull @RequestBody Comment comment,
                                              @PathVariable long news_id,
                                              @PathVariable long user_id){

        if(!comment.getText().matches(".*[a-z].*"))
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Comment is not acceptable because it contains no text");

        Optional<News> newsOptional = newsRepository.findById(news_id);
        Optional<ApplicationUser> userOptional = usersRepository.findById(user_id);

        if (!newsOptional.isPresent())
            return ResponseEntity.notFound().build();

        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();



        News news = newsOptional.get();
        ApplicationUser user = userOptional.get();

        comment.setApplicationUser(user);

        newsService.addComent(news, comment);

        return ResponseEntity.ok().body(news);

    }
}
