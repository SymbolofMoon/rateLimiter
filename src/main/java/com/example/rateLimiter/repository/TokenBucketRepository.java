//package com.example.rateLimiter.repository;
//
//public class TokenBucketRepository {
//}
package com.example.rateLimiter.repository;

import com.example.rateLimiter.entity.TokenBucket;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TokenBucketRepository  {

    private  final ConcurrentHashMap<String, TokenBucket> store = new ConcurrentHashMap<>();

    public TokenBucket getBucket(String clientId){
        return store.getOrDefault(clientId, null);
    }

    public void createBucket(String clientId, Long bucketSize, Long refillRate){
        store.putIfAbsent(clientId, new TokenBucket(bucketSize, refillRate));
    }

    public boolean buckExist(String clientId){
        return store.containsKey(clientId);
    }


}