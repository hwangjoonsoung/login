package me.hwangjoonsoung.pefint.util.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommonUtil {

    private final ObjectMapper objectMapper;

    public JsonNode stringToJson(String jsonString) throws JsonProcessingException {
        JsonNode jsonObject = objectMapper.readTree(jsonString);
        return jsonObject;
    }
}
