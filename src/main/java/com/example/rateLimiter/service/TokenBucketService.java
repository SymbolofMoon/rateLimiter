//package com.example.rateLimiter.service;
//
//public class TokenBucketService {
//}
package com.example.rateLimiter.service;

import com.example.rateLimiter.entity.TokenBucket;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.rateLimiter.repository.TokenBucketRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenBucketService {

    @Autowired
    private TokenBucketRepository tokenBucketRepository;

    public void saveTokenBucket(String clienId, Long bucketSize, Long refillRate){
        tokenBucketRepository.createBucket(clienId, bucketSize, refillRate);
    }

    public boolean bucketExist(String clientId){
        return tokenBucketRepository.buckExist(clientId);
    }

    public boolean allowedRequest(String clientId){

        TokenBucket bucket = tokenBucketRepository.getBucket(clientId);
        if(bucket==null) return false;

        return bucket.allowRequest();

    }

}