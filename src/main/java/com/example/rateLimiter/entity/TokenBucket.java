//package com.example.rateLimiter.entity;
//
//public class TokenBucket {
//}
package com.example.rateLimiter.entity;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucket {
    //Starting 9:55 PM
    private final long bucketSize;
    private AtomicInteger currentbucetsize;
    private final long refillrate;
    private Instant lastRefillTime;


    public TokenBucket(long bucketSize, long refillrate) {
        this.bucketSize = bucketSize;
        this.refillrate = refillrate;
        this.currentbucetsize = new AtomicInteger(0);
        this.lastRefillTime = Instant.now();
    }

    public long getBucketSize() {
        return bucketSize;
    }

    public AtomicInteger getCurrentbucetsize() {
        return currentbucetsize;
    }

    public long getRefillrate() {
        return refillrate;
    }

    public void setCurrentbucetsize() {
        currentbucetsize.incrementAndGet();
    }

    public synchronized  boolean allowRequest(){

        refill();
        System.out.println(currentbucetsize);
        if(currentbucetsize.get()==bucketSize){
            currentbucetsize.set(0);
            return false;
        }

        currentbucetsize.incrementAndGet();
        System.out.println(currentbucetsize);

        return true;
    }


    public void resetBucket(){

        Instant now = Instant.now();

        long secondsElapsed = java.time.Duration.between(lastRefillTime, now).getSeconds();

        if(secondsElapsed>=60){
            currentbucetsize.set(0);
        }
    }

    private void refill(){
        Instant now = Instant.now();

        long secondsElapsed = java.time.Duration.between(lastRefillTime, now).getSeconds();

        if(secondsElapsed>0){
            int tokentoAdd = (int)(secondsElapsed*refillrate);
            int nToken = Math.min((int)bucketSize, currentbucetsize.get()+tokentoAdd);
            currentbucetsize.set(nToken);
            lastRefillTime = now;
        }
    }


}
