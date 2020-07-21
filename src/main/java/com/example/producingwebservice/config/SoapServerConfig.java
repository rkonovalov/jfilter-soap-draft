package com.example.producingwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;

import java.util.List;


@EnableWs
@Configuration
public class SoapServerConfig extends WsConfigurerAdapter {

    private JFilterEndpointInterceptor jFilterEndpointInterceptor;

    @Autowired
    public SoapServerConfig(JFilterEndpointInterceptor jFilterEndpointInterceptor) {
        this.jFilterEndpointInterceptor = jFilterEndpointInterceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(jFilterEndpointInterceptor);
    }
}