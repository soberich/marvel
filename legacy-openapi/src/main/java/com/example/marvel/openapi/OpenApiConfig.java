package com.example.marvel.openapi;

import com.example.marvel.convention.serial.Json;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.SimpleType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.integration.api.ObjectMapperProcessor;
import io.swagger.v3.oas.models.media.*;

import javax.ws.rs.core.StreamingOutput;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.time.*;
import java.time.temporal.Temporal;
import java.util.*;

import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static java.util.Arrays.asList;


public class OpenApiConfig implements ModelConverter, ObjectMapperProcessor {

    private static final List<String>
            STREAMING_CLASSES  = asList(
                "arrow.core.ListK",
                "arrow.data.ListK",
                "io.reactivex.Flowable",
                "io.reactivex.Observable",
                "org.reactivestreams.Publisher",
                "org.springframework.data.domain.Page",
                "org.springframework.data.domain.Slice"
            ),
            WRAPPER_CLASSES    = asList(
                "java.util.concurrent.CompletionStage",
                "java.util.concurrent.CompletableFuture",
                "io.reactivex.Single",
                "io.reactivex.Maybe"
            ),
            SKIPPABLE_PACKAGES = asList(
                "com.example.mavel.service.filter",
                "com.example.mavel.service.criteria",
                "io.reactivex.Completable",
                "java.lang.Void"
            );

    private static final JsonMapper mapper = Json.CONFIGURED_MAPPER;

    @Override
    public Schema<?> resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        if (SKIPPABLE_PACKAGES.stream().anyMatch(type.getType().getTypeName()::contains)) return null;
        if (type.getType() instanceof ParameterizedType && STREAMING_CLASSES.contains(((ParameterizedType) type.getType()).getRawType().getTypeName()))
            return context.resolve(new AnnotatedType(mapper.getTypeFactory().constructCollectionType(List.class, mapper.constructType(((ParameterizedType) type.getType()).getActualTypeArguments()[0]))).resolveAsRef(true));
        if (type.getType() instanceof SimpleType && STREAMING_CLASSES.contains(((SimpleType) type.getType()).getRawClass().getName()))
            return context.resolve(new AnnotatedType(mapper.getTypeFactory().constructCollectionType(List.class, mapper.constructType(((SimpleType) type.getType()).containedType(0)))).resolveAsRef(true));
        if (type.getType() instanceof ParameterizedType && WRAPPER_CLASSES.contains(((ParameterizedType) type.getType()).getRawType().getTypeName()))
            return context.resolve(new AnnotatedType(((ParameterizedType) type.getType()).getActualTypeArguments()[0]).resolveAsRef(true));
        if (type.getType() instanceof SimpleType && WRAPPER_CLASSES.contains(((SimpleType) type.getType()).getRawClass().getName()))
            return context.resolve(new AnnotatedType(((SimpleType) type.getType()).containedType(0)).resolveAsRef(true));
        if (type.isSchemaProperty()) {
            JavaType _type = mapper.constructType(type.getType());
            if (_type != null) {
                Class<?> cls = _type.getRawClass();
                // Try to put checks in the order from most frequently-used
                // to less frequently-used in the code-base
                if (Temporal.class.isAssignableFrom(cls) && (type.getPropertyName().equalsIgnoreCase("dateCreated") || type.getPropertyName().equalsIgnoreCase("dateModified")))
                    return null;
                if (Number.class.isAssignableFrom(cls) || cls.isPrimitive() && type.getPropertyName().equalsIgnoreCase("version"))
                    return null;
                if (Instant.class.isAssignableFrom(cls) || LocalDateTime.class.isAssignableFrom(cls))
                    return new ComposedSchema().anyOf(asList(new DateTimeSchema(), new IntegerSchema().format("int64")                     , new NumberSchema())).example(1544391144);
                if (Date.class.isAssignableFrom(cls) || LocalDate.class.isAssignableFrom(cls))
                    return new ComposedSchema().anyOf(asList(new DateSchema()    , new IntegerSchema().format("int64")                     , new NumberSchema())).example(1544391144);
                if (Year.class.isAssignableFrom(cls))
                    //language=RegExp
                    return new ComposedSchema().anyOf(asList(new StringSchema()  , new IntegerSchema())).pattern("[0-9]{4}").example(2018);
                if (Period.class.isAssignableFrom(cls))
                    //language=RegExp
                    return new StringSchema().format("ISO 8601").pattern("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?").example("-P3Y+6M25W-15D");
                if (ZoneId.class.isAssignableFrom(cls) || TimeZone.class.isAssignableFrom(cls))
                    return new StringSchema().format("time-zone").example("UTC");
                if (Locale.class.isAssignableFrom(cls))
                    //language=RegExp
                    return new StringSchema().format("IETF BCP 47").pattern("(?<lang>[a-z]{2,8})(?:_(?<country>(?:[a-z]{2})|(?:[0-9]{3})))?").example(Locale.ENGLISH);
                if (ByteBuffer.class.isAssignableFrom(cls))
                    return new FileSchema();
                if (StreamingOutput.class.isAssignableFrom(cls))
                    return new FileSchema();
                if (String.class.isAssignableFrom(cls) && type.getPropertyName().equalsIgnoreCase("email"))
                    return new EmailSchema();
                if (Currency.class.isAssignableFrom(cls))
                    return new StringSchema().example("USD");
            }
        }
        return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
    }

    @Override
    public void processJsonObjectMapper(ObjectMapper mapper) {
        mapper
            .setConfig(OpenApiConfig.mapper.getDeserializationConfig())
            .setConfig(OpenApiConfig.mapper.getSerializationConfig())
            .setPropertyNamingStrategy(OpenApiConfig.mapper.getPropertyNamingStrategy())
            .setVisibility(OpenApiConfig.mapper.getVisibilityChecker())
            .enable(SORT_PROPERTIES_ALPHABETICALLY);
    }

    @Override
    public void processYamlObjectMapper(ObjectMapper mapper) {
        mapper
            .setConfig(OpenApiConfig.mapper.getDeserializationConfig())
            .setConfig(OpenApiConfig.mapper.getSerializationConfig())
            .setPropertyNamingStrategy(OpenApiConfig.mapper.getPropertyNamingStrategy())
            .setVisibility(OpenApiConfig.mapper.getVisibilityChecker())
            .enable(SORT_PROPERTIES_ALPHABETICALLY);
    }
}
