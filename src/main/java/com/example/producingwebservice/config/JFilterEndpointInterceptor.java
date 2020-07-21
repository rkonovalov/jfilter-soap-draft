package com.example.producingwebservice.config;

import com.example.producingwebservice.SoapUtils;
import com.jfilter.components.DynamicFilterProvider;
import com.jfilter.components.FilterProvider;
import com.jfilter.filter.FilterFields;
import com.jfilter.request.RequestSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import static com.jfilter.filter.FilterFields.EMPTY_FIELDS;

@Component
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class JFilterEndpointInterceptor implements EndpointInterceptor {

    private FilterProvider filterProvider;
    private DynamicFilterProvider dynamicFilterProvider;
    private HttpServletRequest httpServletRequest;

    @Autowired
    public void setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    @Autowired
    public void setDynamicFilterProvider(DynamicFilterProvider dynamicFilterProvider) {
        this.dynamicFilterProvider = dynamicFilterProvider;
    }

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext, Object o) throws Exception {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        MethodEndpoint methodEndpoint = (MethodEndpoint) endpoint;
        Method method = methodEndpoint.getMethod();
        MethodParameter methodParameter = new MethodParameter(method, 0);
        RequestSession requestSession = new RequestSession(httpServletRequest);
        final Node object = SoapUtils.extractObject(messageContext);

        FilterFields filterFields = EMPTY_FIELDS.get();

        //Retrieve filterable fields from static filters
        filterProvider.getOptionalFilter(methodParameter)
                .ifPresent(filter -> filterFields.appendToMap(filter.getFields(object, requestSession)));

        //Retrieve filterable fields from dynamic filters
        filterFields.appendToMap(dynamicFilterProvider.getFields(methodParameter, requestSession));
        filter(object, filterFields);

        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object o) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object o, Exception e) throws Exception {

    }

    public Node filter(Node objectNode, FilterFields filterFields) {
        NodeList fields = objectNode.getChildNodes();
        String mainNodeName = objectNode.getLocalName();
        for (int n = 0; n < fields.getLength(); n++) {
            Node field = fields.item(n);
            String fieldName = field.getLocalName();

            if (field.getChildNodes().getLength() > 0)
                filter(field, filterFields);

            filterFields.getFieldsMap().forEach((k, v) -> {
                String className = SoapUtils.getClassNodeName(k);
                if (mainNodeName.equals(className)) {
                    v.forEach(fField -> {
                        if (fField.equals(fieldName)) {
                            objectNode.removeChild(field);
                        }
                    });
                }
            });
        }

        return objectNode;
    }
}
