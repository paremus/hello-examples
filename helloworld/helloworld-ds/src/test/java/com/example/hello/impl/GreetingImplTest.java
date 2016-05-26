package com.example.hello.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.osgi.framework.Constants.FRAMEWORK_UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;


@RunWith(MockitoJUnitRunner.class)
public class GreetingImplTest {
    
    @Mock
    BundleContext ctx;
    
    @Test
    public void testSaysHello() {
        when(ctx.getProperty(FRAMEWORK_UUID)).thenReturn("abcdefgh");
        GreetingImpl greetingImpl = new GreetingImpl();

        greetingImpl.activate(ctx);
        
        String result = greetingImpl.sayHello("NewYear");
        assertEquals("Framework abcdefgh says hello, NewYear!", result);
    }
}
