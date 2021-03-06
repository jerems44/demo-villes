package org.openclassrooms.cities.controllers

import groovy.json.JsonSlurper
import org.openclassrooms.cities.exceptions.CityNotFoundException
import org.openclassrooms.cities.model.City
import org.openclassrooms.cities.repositories.ICitiesRepository
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.ServletTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

/**
 * Created by jguidoux on 03/05/2017.
 * this file test the rest api to get info about a city
 *
 * this unit test only show if the rest api /cities/list work
 *  call the CitiesController and return a list of cities in json format
 */

class GetCityInfoTestUrlSpeck extends Specification {


    ICitiesRepository citiesRepository = Mock()
    CitiesController citiesController = new CitiesController(citiesRepository)

    MockMvc mockMvc

    def setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(citiesController)
        .build()
    }

    def "check if the uls '/cities/get is valis"() {

        given: "this city : 'Rennes' does not exist in the repository"
        citiesRepository.getCity("Rennes") >>
                new City("Rennes", 1, 1)


        when: "I ask for the rest api '/api/rest/cities/get?name=Rennes'"
        URI url = UriComponentsBuilder.fromUriString("/api/rest/cities/get").
                queryParam("name", "Rennes").build().toUri()
        def response = this.mockMvc.perform(get(url).
                accept(MediaType.APPLICATION_JSON)).andReturn().response


        then: "the request status should be 'ok'"
        response.status == OK.value()

    }

    @WithMockUser
    def "ask for a city  from rest api '/cities/get with json result"() {

        given: "this city : 'Rennes' does not exist in the repository"
        citiesRepository.getCity("Rennes") >>
                new City("Rennes", 1, 1)


        when: "I ask for the rest api '/api/rest/cities/get?name=Rennes'"
        URI url = UriComponentsBuilder.fromUriString("/api/rest/cities/get").
                queryParam("name", "Rennes").build().toUri()
        def response = this.mockMvc.perform(get(url).
                accept(MediaType.APPLICATION_JSON)).andReturn().response
        def result = new JsonSlurper().parseText(response.contentAsString)


        then: "the result should be a valid json content of the cites 'Paris' 'Rennes' 'Bordeaux' 'Reims'"
        response.status == OK.value()
        response.contentType == MediaType.APPLICATION_JSON_UTF8_VALUE
        result.name == "Rennes"
    }


    def "ask for a city which does not existfrom rest api '/cities/get with json result"() {

        given: "this city : 'Guérande' does not exist in the repository"
        citiesRepository.getCity("Guérande") >>
                { throw new CityNotFoundException("Guérande") }

        when: "I ask for the rest api '/api/rest/cities/get?name=Guérande'"
        URI url = UriComponentsBuilder.fromUriString("/api/rest/cities/get").
                queryParam("name", "Guérande").build().toUri()
        def response = this.mockMvc.perform(get(url).
                accept(MediaType.APPLICATION_JSON)).andReturn().response
//        def result = new JsonSlurper().parseText(response.errorMessage)


        then: "the response request should have status 'not found'"
        response.status == NOT_FOUND.value()
        response.errorMessage == "City Not Found"

    }


}

