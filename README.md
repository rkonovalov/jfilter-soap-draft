# JFilter + SOAP draft demo

## Run
Before running application please run next Maven command:

```shell script
mvn jaxb2:xjc
```

Endpoint url: http://localhost:8080/ws

## Request examples
Sample requests could be found in src/main/resources

### requestWithFiltering.xml
This request runs "" endpoint which filters next fields
```java
    @FieldFilterSetting(className = User.class, fields = {"id", "password"})
    @FieldFilterSetting(className = Address.class, fields = {"id"})
    @FieldFilterSetting(className = Street.class, fields = {"id"})
    @FieldFilterSetting(fields = {"secretValue"})
```
* Filter **id** and **password** fields from each **User** class
* Filter **id** field from each **Address** class
* Filter **id** field from each **Street** class
* Filter **secretValue** field from all classes

* Request with filtering
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:gs="http://spring.io/guides/gs-producing-web-service">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:getUserRequestWithFiltering>
            <gs:email>janedoe@gmail.com</gs:email>
        </gs:getUserRequestWithFiltering>
    </soapenv:Body>
</soapenv:Envelope>
```

* Response
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <ns2:getUserResponse xmlns:ns2="http://spring.io/guides/gs-producing-web-service">
            <ns2:user>
                <ns2:email>janedoe@gmail.com</ns2:email>
                <ns2:fullName>Jane Doe</ns2:fullName>
                <ns2:secretKey>secret_key</ns2:secretKey>
                <ns2:address>
                    <ns2:apartment>52A</ns2:apartment>
                    <ns2:zipCode>100200</ns2:zipCode>
                    <ns2:street>
                        <ns2:streetName>Burbone Street</ns2:streetName>
                        <ns2:streetNumber>15</ns2:streetNumber>
                    </ns2:street>
                </ns2:address>
            </ns2:user>
        </ns2:getUserResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

### requestWithoutFiltering.xml
Example without filtering

* Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:gs="http://spring.io/guides/gs-producing-web-service">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:getUserRequestWithoutFiltering>
            <gs:email>janedoe@gmail.com</gs:email>
        </gs:getUserRequestWithoutFiltering>
    </soapenv:Body>
</soapenv:Envelope>
```

* Response
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <ns2:getUserResponse xmlns:ns2="http://spring.io/guides/gs-producing-web-service">
            <ns2:user>
                <ns2:id>1</ns2:id>
                <ns2:email>janedoe@gmail.com</ns2:email>
                <ns2:fullName>Jane Doe</ns2:fullName>
                <ns2:password>password123</ns2:password>
                <ns2:secretKey>secret_key</ns2:secretKey>
                <ns2:address>
                    <ns2:id>2</ns2:id>
                    <ns2:apartment>52A</ns2:apartment>
                    <ns2:zipCode>100200</ns2:zipCode>
                    <ns2:street>
                        <ns2:id>3</ns2:id>
                        <ns2:streetName>Burbone Street</ns2:streetName>
                        <ns2:streetNumber>15</ns2:streetNumber>
                    </ns2:street>
                </ns2:address>
            </ns2:user>
        </ns2:getUserResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```