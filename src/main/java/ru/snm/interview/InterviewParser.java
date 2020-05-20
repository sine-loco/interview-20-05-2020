package ru.snm.interview;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Type;
import java.util.Map;


@Service
public class InterviewParser {

    public @NotBlank String parse(@NotBlank String json) {
        // FIXME implement

        Gson gson = new Gson();
        Object map = gson.fromJson(json, Object.class);

        return null;
    }
}
                                            