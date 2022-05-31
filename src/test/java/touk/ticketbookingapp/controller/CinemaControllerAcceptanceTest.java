package touk.ticketbookingapp.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CinemaControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRoomWithMovieShowIdTest() throws Exception {
        mockMvc.perform(get("/movie-shows/1010/room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(4)));

    }

    /*
    @Test
    void getMovieShowsInPeriodTest() throws Exception {
        mockMvc.perform(get("/movie-shows/period/?from=16:00-5/5/2022&to=23:00-22/12/2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[tittle", is("Batman")));
    }

     */

    /*
    @Test
    void getMovieShowInPeriodSortTest() {
        List<Map<String, Object>> dataList =
                JsonPath.parse(jsonString)
    }

     */


}
