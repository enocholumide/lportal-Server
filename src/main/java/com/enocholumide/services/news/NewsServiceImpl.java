package com.enocholumide.services.news;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.domain.news.News;
import com.enocholumide.repositories.NewsRepository;
import com.enocholumide.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<News> listAll() {
        return this.newsRepository.findAll();
    }

    @Override
    public News addComent(News news, Comment comment) {
        news.getComments().add(comment);
        newsRepository.save(news);
        return news;
    }
}
