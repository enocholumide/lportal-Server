package com.enocholumide.services.news;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.domain.news.News;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NewsService {

    List<News> listAll();
    News addComent(News news, Comment comment);

}
