package top.akte.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;

public class JacksonMapper extends ObjectMapper {

    private static JacksonMapper mapper;

    private static JacksonMapper mapperWithUnderScores;

    public static JacksonMapper getInstance() {
        if (mapper == null) {
            mapper = new JacksonMapper().config();
        }
       // mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        return mapper;
    }

    public static JacksonMapper getInstanceWithUnderScores() {
        if (mapperWithUnderScores == null) {
            mapperWithUnderScores = new JacksonMapper().config();
        }
        mapperWithUnderScores.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapperWithUnderScores;
    }

    /**
     * 允许单引号
     * 允许不带引号的字段名称
     */
    public JacksonMapper config() {
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return this;
    }

    public static <T> T parseObjectWithUnderScores(String content, TypeReference<T> typeReference) throws IOException {
        return getInstanceWithUnderScores().readValue(content, typeReference);
    }

    public static <T> T parseObject(String content, TypeReference<T> typeReference) throws IOException {
        return getInstance().readValue(content, typeReference);
    }

}
