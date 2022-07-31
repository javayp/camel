package com.apps.camelApp.service;

import com.apps.camelApp.domain.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonalService {

    private final ObjectMapper objectMapper;

    @Autowired
    public PersonalService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void processStringData(Object object) throws JsonProcessingException {

        List<Person> people = objectMapper.readValue(object.toString(), new TypeReference<List<Person>>() {});
        System.out.println(people);
    }
}
