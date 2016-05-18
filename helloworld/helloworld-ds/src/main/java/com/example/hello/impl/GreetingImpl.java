package com.example.hello.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.hello.Greeting;

@Designate(ocd = GreetingImpl.Config.class)
@Component(configurationPid = "com.example.hello.ds")
public class GreetingImpl implements Greeting {

    private static final Logger logger = LoggerFactory.getLogger(GreetingImpl.class);

    @ObjectClassDefinition(id = "com.example.hello.ds", name = "Hello Message Service configuration")
    @interface Config {
        @AttributeDefinition(required = false)
        String language() default "en";
    }

    private static final String RESOURCE_BASE = "com.example.hello.impl.greetings";

    private ResourceBundle resources;

    @Activate
    public void activate(Config config) {
        logger.info("Setting Hello Greeting Service language to {}", config.language());
        setResourceBundle(config);
    }

    @Modified
    public void modified(Config config) {
        logger.info("Changing Hello Greeting Service language to {}", config.language());
        setResourceBundle(config);
    }

    private void setResourceBundle(Config config) {
        Locale locale = new Locale(config.language());
        resources = ResourceBundle.getBundle(RESOURCE_BASE, locale);
    }

    @Override
    public String sayHello(String name) {
        return MessageFormat.format(resources.getString("greeting"), name);
    }
}
