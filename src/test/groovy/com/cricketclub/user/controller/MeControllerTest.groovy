package com.cricketclub.user.controller

import com.cricketclub.user.service.UserService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Ignore
import spock.lang.Specification
import static org.hamcrest.Matchers.*
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class MeControllerTest extends Specification{

    private final UserService userService = Mock(UserService)
    private final UserControllerHateoasBuilder userControllerHateoasBuilder = Mock(UserControllerHateoasBuilder)

    private MeController underTest = new MeController(userService, userControllerHateoasBuilder)

    def mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()

    @Ignore
    def 'someMethod() forwards to service and returns result as a JSON'() {
        when:
            def response = mockMvc.perform(get('/me')
                    .contentType(APPLICATION_JSON_VALUE)
            )
        then:
            1 * userService.me(principal) >> 'some/other/value'

            response
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath('$.url', is('some/other/value')))
    }
}
