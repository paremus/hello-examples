package com.example.hello;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Greeting {
    public String sayHello(String name);
}
