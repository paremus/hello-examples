package com.example.hello.web;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import com.example.hello.Greeting;
import com.paremus.http.publish.HttpEndpointPublisher;

@Component(property = { HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/hello/sayHello",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME + "=Hello World",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN + "=/hello/static/*",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX + "=/static",
        HttpEndpointPublisher.APP_ENDPOINT + "=/hello/static/index.html"
})
public class HelloWorldServlet extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 1L;

    @Reference
    Greeting greeting;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        if (name != null) {
            response.getWriter().println(greeting.sayHello(name));
            response.getWriter().close();
        }
    }

}
