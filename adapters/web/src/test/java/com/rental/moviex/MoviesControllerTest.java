package com.rental.moviex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.moviex.application.port.in.RentMoviesUseCase;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase.ReturnMoviesCommand;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase.ReturnedMoviesResponse;
import com.rental.moviex.controller.MoviesController;
import com.rental.moviex.exception.RentalNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Set;

import static com.rental.moviex.application.port.in.RentMoviesUseCase.RentMoviesCommand;
import static com.rental.moviex.application.port.in.RentMoviesUseCase.RentedMoviesResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MoviesController.class)
public class MoviesControllerTest {
    private static final long CALCULATED_COST = 60L;
    private static final long USER_ID = 13L;
    private static final long MOVIE_ID = 22;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RentMoviesUseCase rentMoviesUseCase;
    @MockBean
    private ReturnMoviesUseCase returnMoviesUseCase;

    @Test
    void somethingWillBreak() throws Exception {
        when(rentMoviesUseCase.rentMovies(new RentMoviesCommand(USER_ID, new HashMap<>() {{
            put(1L, 2);
            put(2L, 3);
        }})))
                .thenReturn(new RentedMoviesResponse(CALCULATED_COST));

        String response = mockMvc.perform(post("/movies/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"userId\":13,\n" +
                        "   \"moviesForDays\":[\n" +
                        "      {\n" +
                        "         \"movieId\":1,\n" +
                        "         \"days\":2\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"movieId\":2,\n" +
                        "         \"days\":3\n" +
                        "      }\n" +
                        "   ]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RentedMoviesResponse rentedMoviesResponse = convertResponse(response, new TypeReference<>() {});
        assertEquals(60L, rentedMoviesResponse.getCalculatedCost());
    }

    @Test
    void shouldReturnMovies() throws Exception {
        when(returnMoviesUseCase.returnMovies(new ReturnMoviesCommand(22L, Set.of(1L, 12L, 23L, 34L))))
                .thenReturn(new ReturnedMoviesResponse(90L, 5));

        String response = mockMvc.perform(post("/movies/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"userId\":22,\n" +
                        "   \"moviesIds\":[\n" +
                        "      23,\n" +
                        "      1,\n" +
                        "      34,\n" +
                        "      12\n" +
                        "   ]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ReturnedMoviesResponse returnedMoviesResponse = convertResponse(response, new TypeReference<>() {});

        assertEquals(5, returnedMoviesResponse.getBonusPoints());
        assertEquals(90L, returnedMoviesResponse.getSurcharges());
    }

    @Test
    void shouldReturnNotFoundStatusForNotExistingEntities() throws Exception {
        RentalNotFoundException thrownException = new RentalNotFoundException(USER_ID, MOVIE_ID);
        when(rentMoviesUseCase.rentMovies(any(RentMoviesCommand.class)))
                .thenThrow(thrownException);

        String response = mockMvc.perform(post("/movies/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"userId\":13,\n" +
                        "   \"moviesForDays\":[\n" +
                        "      {\n" +
                        "         \"movieId\":1,\n" +
                        "         \"days\":2\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"movieId\":2,\n" +
                        "         \"days\":3\n" +
                        "      }\n" +
                        "   ]\n" +
                        "}"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        assertEquals(thrownException.getMessage(), response);
    }

    private static <T> T convertResponse(String response, TypeReference<T> castType) {
        try {
            return new ObjectMapper().readValue(response, castType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
