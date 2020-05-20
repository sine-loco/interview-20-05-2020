package ru.snm.interview;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;


@Service
public class InterviewParser {

    public @NotBlank String parse(@NotBlank String json) {
        // FIXME implement
        //Gson.
        return null;
    }
}
