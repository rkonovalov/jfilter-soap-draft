package com.example.producingwebservice.endpoint;

import com.example.producingwebservice.repository.UserRepository;
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

    @FieldFilterSetting(className = User.class, fields = {"id", "password"})
    @FieldFilterSetting(className = Address.class, fields = {"id"})
    @FieldFilterSetting(className = Street.class, fields = {"id"})
    @FieldFilterSetting(fields = {"secretValue"})
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequestWithFiltering")
    @ResponsePayload
    public GetUserResponse getUserRequestWithFiltering(@RequestPayload GetUserRequestWithFiltering request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.findByEmail(request.getEmail()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequestWithoutFiltering")
    @ResponsePayload
    public GetUserResponse getUserRequestWithoutFiltering(@RequestPayload GetUserRequestWithoutFiltering request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.findByEmail(request.getEmail()));
        return response;
    }
}
