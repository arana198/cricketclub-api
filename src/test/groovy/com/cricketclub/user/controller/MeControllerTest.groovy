package com.cricketclub.user.controller

import com.cricketclub.user.service.UserService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
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

}
