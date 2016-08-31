package com.example.hello.impl;

import org.osgi.service.component.annotations.Component;

import com.example.hello.Greeting;

@Component
public class GreetingImpl implements Greeting {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
