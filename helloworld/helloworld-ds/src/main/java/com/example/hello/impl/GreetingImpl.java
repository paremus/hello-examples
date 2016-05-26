package com.example.hello.impl;

import static org.osgi.framework.Constants.FRAMEWORK_UUID;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.example.hello.Greeting;

@Component(property="service.exported.interfaces=*")
public class GreetingImpl implements Greeting {
    
    private String uuid;
    
    @Activate
    void activate(BundleContext context) {
        uuid = context.getProperty(FRAMEWORK_UUID).substring(0, 8);
    }
    
    @Override
    public String sayHello(String name) {
        return "Framework " + uuid + " says hello, " + name + "!";
    }
}
