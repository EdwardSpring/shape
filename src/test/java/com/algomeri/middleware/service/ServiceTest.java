package com.algomeri.middleware.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

public class ServiceTest {
	
	@Test
	public void testBucket() {
		Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
		Bucket bucket = Bucket4j.builder().addLimit(limit).build();

		for (int i = 1; i <= 11; i++) {
			boolean didRun = bucket.tryConsume(1);
			System.out.println(didRun);
		    assertTrue(didRun);
		}
		assertFalse(bucket.tryConsume(1));
	}

	

}
