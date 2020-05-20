package ru.snm.interview;

import com.google.common.io.Resources;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static com.google.common.io.Resources.getResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Does not lift SpringContext, therefore validation is out of scope.
 */
class InterviewParserTest {

    private final InterviewParser subject = new InterviewParser();


    @DisplayName( "invalid JSON" )
    @ParameterizedTest( name = "{0}" )
    @MethodSource( "invalidJson" )
    void parse_invalid( String json ) {
        // expect
        assertThrows( RuntimeException.class, () -> subject.parse( json ) );
    }

    @DisplayName( "valid JSON" )
    @ParameterizedTest
    @MethodSource( "validJson" )
    void parse_valid( String json, String expected ) {
        // when
        String actual = subject.parse( json );

        // then
        assertEquals( expected, actual );
    }


    static Stream<Arguments> invalidJson() {
        return Stream.of(
                Arguments.of( "{}" ),
                Arguments.of( "{\"no-value\":}" ),
                Arguments.of( "{:\"no-key\"}" ),
                Arguments.of( "{]" ),
                Arguments.of( "{" ),
                Arguments.of( "{}}" ),
                Arguments.of( "{\"key\": {\"value\"}" )
        );
    }

    static Stream<Arguments> validJson() {
        return Stream.of(
                Arguments.of( read( "json/1.json" ), read( "parsed/1.string") )
        );
    }


    static String read(String fileName) {
        try {
            return Resources.toString( getResource( fileName ), StandardCharsets.UTF_8 );
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
    }
}