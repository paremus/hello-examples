package com.example.hello.web;

import static org.osgi.framework.Constants.FRAMEWORK_UUID;
import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.remoteserviceadmin.RemoteConstants.ENDPOINT_FRAMEWORK_UUID;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.NamespaceException;
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
        
    private final AtomicReference<Greeting> localGreeting = new AtomicReference<Greeting>();
    
    private String uuid;
    
    private ConcurrentMap<String, Greeting> remoteGreetings = new ConcurrentHashMap<>();

    @Reference(policy = DYNAMIC, cardinality = MULTIPLE)
    void setGreeting(Greeting greeting, Map<String, Object> props) {
        String remoteUUID = (String) props.get(ENDPOINT_FRAMEWORK_UUID);
        if (remoteUUID != null) {
            remoteGreetings.put(((String) remoteUUID).substring(0,8), greeting);
        } else {
            localGreeting.set(greeting);
        }
    }
    
    void unsetGreeting(Greeting greeting, Map<String, Object> props) {
        String remoteUUID = (String) props.get(ENDPOINT_FRAMEWORK_UUID);
        if (remoteUUID != null) {
            remoteGreetings.remove(remoteUUID.substring(0,8), greeting);
        } else {
            localGreeting.compareAndSet(greeting, null);
        }
    }
    
    @Activate
    void start(BundleContext context) throws NamespaceException, ServletException {
        uuid = context.getProperty(FRAMEWORK_UUID).substring(0, 8);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/javascript");
        resp.setCharacterEncoding("UTF-8");
        
        Greeting local = localGreeting.get();

        Map<String, Greeting> snapshot = new TreeMap<>(remoteGreetings);
        if(local != null) {
            snapshot.put(uuid, local);
        }
        
        StringBuilder response = new StringBuilder("{");
        
        writeAvailableIds(snapshot, response);
        
        String name = req.getParameter("name");
        
        response.append(",");
        if(name != null) {
            writeMessages(snapshot, name, response);
        } else {
            response.append("\"messages\": {}");
        }
        
        response.append("}");
        
        
        resp.getWriter().println(response);
    }

    private void writeAvailableIds(Map<String, Greeting> snapshot, StringBuilder response) {
        response.append("\"available\": [");
        
        for(String id : snapshot.keySet()) {
            response.append('"')
                .append(id)
                .append('"')
                .append(',');
        }
        if(!snapshot.isEmpty()) {
            response.deleteCharAt(response.length() -1);
        }
        response.append(']');
    }

    private void writeMessages(Map<String, Greeting> snapshot, String name, StringBuilder response) {
        response.append("\"messages\": {");
        
        for(Entry<String, Greeting> e : snapshot.entrySet()) {
            String message;
            try {
                 message = e.getValue().sayHello(name);
            } catch (RuntimeException re) {
                message = "An error occurred: " + re.getMessage();
            }
            
            response.append('"')
                .append(e.getKey())
                .append("\": ")
                .append('"')
                .append(message)
                .append('"')
                .append(',');
        }
        
        if(!snapshot.isEmpty()) {
            response.deleteCharAt(response.length() -1);
        }
        response.append('}');
    }
}
