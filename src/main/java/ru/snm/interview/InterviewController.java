package ru.snm.interview;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;


@RestController
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewParser parser;

    @PostMapping(
            value = "/interview",
            consumes = APPLICATION_JSON_VALUE,
            produces = TEXT_PLAIN_VALUE )
    public ResponseEntity<?> parse( @RequestBody String json ) {
        ResponseEntity<String> responseEntity;
        try {
            String parsed = parser.parse( json );
            responseEntity = ResponseEntity.ok().body( parsed );
        } catch ( RuntimeException e ) {
            responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }
}
