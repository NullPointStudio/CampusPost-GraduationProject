package com.nullpoint.admin;

import org.junit.Test;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MainTest {

    @Test
    public void test1() {
        System.out.println(Duration.ofSeconds(30));
    }
}
