package com.example.rateLimiter.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.rateLimiter.service.TokenBucketService;

@RestController
@RequestMapping("/api/ratelimiter")
public class RateLimiter {

    @Autowired
    private TokenBucketService tokenBucketService;

    @PostMapping("/create/bucket")
    public ResponseEntity<String> createBucket(@RequestParam String clientId, @RequestParam long bucketSize, @RequestParam long refillrate){
//        TokenBucket tokenbucket = new TokenBucket(bucketSize, refillrate);
//        tokenBucketService.saveTokenBucket(tokenbucket);

        if(tokenBucketService.bucketExist(clientId)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bucket Already exist");
        }
        tokenBucketService.saveTokenBucket(clientId, bucketSize, refillrate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Token bucket created with size " + bucketSize + " and refill rate " + refillrate);
    }

//    @GetMapping("/abc")
//    public ResponseEntity<String>getAPI(){
//        return ResponseEntity.ok("HELLO");
//    }

    @GetMapping("/hello")
    public ResponseEntity<String>getRequest(@RequestHeader("X-Client-Id") String clientId){
        if(!tokenBucketService.bucketExist(clientId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry not proceed");
        }

        boolean allowed = tokenBucketService.allowedRequest(clientId);

        if(allowed){
            return ResponseEntity.ok("You can go");
        }else{
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too Much Request");
        }
    }


}
