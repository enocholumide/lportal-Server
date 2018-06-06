package com.enocholumide.controllers;

import com.enocholumide.domain.news.News;
import com.enocholumide.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping(value = "/api/news")
    private ResponseEntity<List<News>> getAllNews(){
        return ResponseEntity.ok().body(this.newsRepository.findAll());
    }
}
