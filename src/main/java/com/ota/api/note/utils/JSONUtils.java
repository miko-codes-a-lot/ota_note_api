package com.ota.api.note.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;

public final class JSONUtils {
    @SneakyThrows
    public static String stringify(Object json) {
        val mapper = new ObjectMapper();
        return mapper.writeValueAsString(json);
    }
}
