package com.enocholumide.services.news;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.domain.news.News;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.repositories.NewsRepository;
import com.enocholumide.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public ResponseEntity<List<News>> listAll() {
        return ResponseEntity.ok().body(this.newsRepository.findAll());
    }

    @Override
    public ResponseEntity addComment(Comment comment, long newsID, long userID) {

        if(!comment.getText().matches(".*[a-z].*"))
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Comment is not acceptable because it contains no text");

        Optional<News> newsOptional = newsRepository.findById(newsID);
        Optional<ApplicationUser> userOptional = usersRepository.findById(userID);

        if (!newsOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("News not found");

        if (!userOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");


        News news = newsOptional.get();
        ApplicationUser user = userOptional.get();

        comment.setApplicationUser(user);
        news.getComments().add(comment);
        newsRepository.save(news);

        return ResponseEntity.ok().body(news);
    }
}
