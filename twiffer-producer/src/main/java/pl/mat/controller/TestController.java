package pl.mat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mat.model.db.Tweet;
import pl.mat.repository.TweetRepository;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api")
public class TestController {

    private TweetRepository tweetRepository;

    public TestController(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @GetMapping("/tweets")
    public List<Tweet> listTweets(){
        System.out.println( tweetRepository.findAll() );

        return tweetRepository.findAll();
    }

}
