package com.enocholumide.services.news;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.domain.news.News;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {

    ResponseEntity<List<News>> listAll();
    ResponseEntity addComment(Comment comment, long newsID, long commentID);

}
