package ru.snm.interview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest( controllers = InterviewController.class )
class InterviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private InterviewController subject;

    @MockBean
    private InterviewParser parser;


    @Test
    void parse_null() throws Exception {
        // expect
        mvc.perform(
                post( "/interview" )
                        .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );
    }

    @Test
    void parse_emptyString() throws Exception {
        // expect
        mvc.perform(
                post( "/interview" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "" ) )
                .andExpect( status().isBadRequest() );

    }

    @Test
    void parse_parserThrows() throws Exception {
        // given
        when( parser.parse( any( String.class ) ) ).thenThrow( RuntimeException.class );

        // expect
        mvc.perform(
                post( "/interview" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{ \"valid\": \"json\"}" ) )
                .andExpect( status().isBadRequest() );
    }

    /**
     * checks controller contract only. Does not check returned string content.
     */
    @Test
    void parse_returnsString() throws Exception {
        // given
        final String RESULT = "result";
        when( parser.parse( any( String.class ) ) ).thenReturn( RESULT );

        // expect
        mvc.perform(
                post( "/interview" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{ \"valid\": \"json\"}" ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( RESULT ) )
                .andExpect( content().contentTypeCompatibleWith( TEXT_PLAIN ) )
        ;
    }
}