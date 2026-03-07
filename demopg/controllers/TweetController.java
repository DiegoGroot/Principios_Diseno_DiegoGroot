package com.postgres.demopg.controllers;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.anotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.repository.TweetRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins ="*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")

public class TweetController{

@Autowired 
private TweetRepository tweetRepository;

@GetMapping("/all")
public Page <Tweet> getTweet(Pageable pageable){
return tweetRepository.findAll(pageable);
}
}
