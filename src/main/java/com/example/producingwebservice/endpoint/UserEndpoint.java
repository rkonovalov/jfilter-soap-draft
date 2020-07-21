package com.example.producingwebservice.endpoint;

import com.example.producingwebservice.repository.UserRepository;
import com.jfilter.components.FilterProvider;
import com.jfilter.filter.FieldFilterSetting;
import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private UserRepository userRepository;

    @Autowired
    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private FilterProvider filterProvider;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    @FieldFilterSetting(className = User.class, fields = {"id", "password"})
    @FieldFilterSetting(className = Address.class, fields = {"id"})
    @FieldFilterSetting(className = Street.class, fields = {"id"})
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.findByEmail(request.getEmail()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest2")
    @ResponsePayload
    public GetUserResponse getUserWithoutFiltration(@RequestPayload GetUserRequest2 request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.findByEmail(request.getEmail()));
        return response;
    }
}
