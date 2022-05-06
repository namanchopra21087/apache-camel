package com.naman.enterprise.integration.apcahe.camel.integration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApacheCamelDemo extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //move_all_file_from_source_to_destination();
        //move_filtered_file_from_source_to_destination("File100");
        //move_filtered_file_with_specific_data_inside_file_from_source_to_destination("Java");
        //move_data_from_text_to_csv();
        multipleFileProcessing();
    }

    private void move_all_file_from_source_to_destination() {
        from("file:source").log("Start picking file from source").to("file:destination").log("Push file to destination").end();
    }

    private void move_filtered_file_from_source_to_destination(String fileNameFilter) {
        from("file:source").log("Start picking file from source").filter(header(Exchange.FILE_NAME).startsWith(fileNameFilter)).to("file:destination").log("Push file to destination").end();
    }

    private void move_filtered_file_with_specific_data_inside_file_from_source_to_destination(String data) {
        from("file:source").log("Start picking file from source").filter(body().startsWith(data)).to("file:destination").log("Push file to destination").end();
    }

    private void move_data_from_text_to_csv() {
        from("file:source").log("Start picking file from source")
                .process(p->{
                    String body=p.getIn().getBody(String.class);
                    StringBuilder sb=new StringBuilder();
                    Arrays.stream(body.split(" ")).forEach(s->{
                        sb.append(s+",");
                    });
                    p.getIn().setBody(sb);
                })
                .to("file:destination?fileName=records.csv").log("Push file to destination").end();
    }

    private void multipleFileProcessing() {
        from("file:source").log("Start picking file from source").unmarshal().csv().split(body().tokenize(";"))
                .choice()
                .when(body().contains("Babel")).to("file:destination?fileName=babel.csv").log("Babel pushed")
                .when(body().contains("Troy")).to("file:destination?fileName=warrior.csv").log("Troy pushed")
                .when(body().contains("StarWars")).to("file:destination?fileName=starWars.csv").log("StarWars pushed");
    }
}
