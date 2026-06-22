package com.example.session.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(HttpServletRequest req, Class<T> clazz)
            throws IOException {

        return mapper.readValue(
                req.getInputStream(),
                clazz);
    }

    public static void writeJson(HttpServletResponse res, Object data)
            throws IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        mapper.writeValue(
                res.getWriter(),
                data);
    }

    public static String toJson(Object object)
            throws IOException {

        return mapper.writeValueAsString(object);
    }
}