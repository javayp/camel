package com.apps.camelApp.router;

import com.apps.camelApp.domain.Person;
import com.apps.camelApp.service.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppRouter extends RouteBuilder {

    private PersonalService personalService;

    @Autowired
    public AppRouter(PersonalService personalService) {
        this.personalService = personalService;
    }

    @Override
    public void configure() throws Exception {

        DataFormat bind = new BindyCsvDataFormat(Person.class);
        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(Person.class);
        jacksonDataFormat.useList();
        jacksonDataFormat.setUnmarshalType(Person.class);


  /*      from("file:src/data/input/?fileName=inputFile.csv")
                .routeId("AppRouter-route")
                .split(body().tokenize("\n",2,true))
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    log.info("Read file data="+body);

                })
                .to("file:src/data/output?fileName=inf.txt&fileExist=append")
                .end()//ending the splitter not the complete process
                .log("Process Completed!");
*/

        from("file:src/data/input?fileName=inputFile.csv&noop=true")
                .log("Data received from CSV file")
                .unmarshal(bind)
                .marshal().json(JsonLibrary.Jackson, true)
                .convertBodyTo(String.class)
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    System.out.println(body);
                    personalService.processStringData(body);
                })
                .end();

    }
}
