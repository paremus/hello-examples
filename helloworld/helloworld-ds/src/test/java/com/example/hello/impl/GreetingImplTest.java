package com.example.hello.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class GreetingImplTest {
    @Test
    public void testSaysHello() {
        String result = new GreetingImpl().sayHello("NewYear");
        assertEquals("Hello, NewYear!", result);
    }
}
