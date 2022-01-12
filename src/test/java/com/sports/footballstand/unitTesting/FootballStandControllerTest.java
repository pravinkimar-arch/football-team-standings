package com.sports.footballstand.unitTesting;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sports.footballstand.models.FootballStandingRequest;
import com.sports.footballstand.services.TeamStandingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FootballStandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamStandingService teamStandingService;


    @Test
    public void footballStandControllerWithRequest() throws Exception {

        FootballStandingRequest footballStandingRequest = new FootballStandingRequest();
        footballStandingRequest.setCountryName("England");
        footballStandingRequest.setLeagueName("Non League Premier");
        footballStandingRequest.setTeamName("Lewes");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(footballStandingRequest);


        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/football/team/standing")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }
}
