package com.example.hello.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.hello.impl.GreetingImpl.Config;

@RunWith(MockitoJUnitRunner.class)
public class GreetingImplTest {
    
    @Mock
    Config config;
    
    @Test
    public void testSaysHello() {
        when(config.language()).thenReturn("en");
        GreetingImpl greetingImpl = new GreetingImpl();

        greetingImpl.activate(config);
        
        String result = greetingImpl.sayHello("NewYear");
        assertEquals("Hello, NewYear!", result);
    }

    @Test
    public void testSaysHelloFrench() {
        when(config.language()).thenReturn("fr");
        GreetingImpl greetingImpl = new GreetingImpl();
        
        greetingImpl.activate(config);
        
        String result = greetingImpl.sayHello("NewYear");
        assertEquals("Bonjour NewYear.", result);
    }
}
