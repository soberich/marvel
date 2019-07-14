package com.example.marvel.openapi;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.integration.api.ObjectMapperProcessor;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.FileSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

import javax.ws.rs.core.StreamingOutput;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Value.construct;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING;
import static com.fasterxml.jackson.databind.MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS;
import static com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;
import static java.util.Arrays.asList;


public class OpenApiConfig implements ModelConverter, ObjectMapperProcessor {

    private static final List<String>
            STREAMING_CLASSES  = asList(
                "org.reactivestreams.Publisher",
                "io.reactivex.Flowable",
                "io.reactivex.Observable"
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

    private final Json mapper = new Json();

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
                if (Instant.class.isAssignableFrom(cls))
                    return new ComposedSchema().anyOf(asList(new DateTimeSchema(), new IntegerSchema().format("int64"), new NumberSchema())).example(1544391144);
                if (Year.class.isAssignableFrom(cls))
                    return new ComposedSchema().anyOf(asList(new StringSchema(), new IntegerSchema())).pattern("[0-9]{4}").example(2018);
                if (Period.class.isAssignableFrom(cls))
                    return new StringSchema().format("ISO 8601").pattern("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?").example("-P3Y+6M25W-15D");
                if (ZoneId.class.isAssignableFrom(cls) || TimeZone.class.isAssignableFrom(cls))
                    return new StringSchema().format("time-zone").example("UTC");
                if (Locale.class.isAssignableFrom(cls))
                    return new StringSchema().format("IETF BCP 47").pattern("(?<lang>[a-z]{2,8})(?:_(?<country>(?:[a-z]{2})|(?:[0-9]{3})))?").example(Locale.ENGLISH);
                if (ByteBuffer.class.isAssignableFrom(cls))
                    return new FileSchema();
                if (StreamingOutput.class.isAssignableFrom(cls))
                    return new FileSchema();
            }
        }
        return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
    }

    @Override
    public void processJsonObjectMapper(ObjectMapper mapper) {
        Json.configure(mapper);
    }

    @Override
    public void processYamlObjectMapper(ObjectMapper mapper) {
        Json.configure(mapper);
    }

    static class Json extends ObjectMapper {
        private static final long serialVersionUID = 1L;
        static TimeZone DEFAULT_TIMEZONE   = TimeZone.getTimeZone("UTC");

        Json() {
            configure(this);
        }

        static void configure(ObjectMapper mapper) {
            mapper.setTimeZone(DEFAULT_TIMEZONE)
                    .registerModules(
                            new JavaTimeModule(),
                            new ParameterNamesModule(PROPERTIES),
                            new AfterburnerModule()
                    ).setSerializationInclusion(NON_ABSENT)
//            .disable(WRITE_DATES_AS_TIMESTAMPS)
                    /*.setDefaultSetterInfo(empty().withValueNulls(SKIP, SKIP))*/
                    .setDefaultVisibility(construct(ALL, PUBLIC_ONLY))
                    .enable(ALLOW_EXPLICIT_PROPERTY_RENAMING, ACCEPT_CASE_INSENSITIVE_ENUMS, ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                    .enable(FAIL_ON_NUMBERS_FOR_ENUMS, READ_ENUMS_USING_TO_STRING)
                    .enable(WRITE_ENUMS_USING_TO_STRING)
                    .disable(ALLOW_FINAL_FIELDS_AS_MUTATORS, DEFAULT_VIEW_INCLUSION)
                    .disable(FAIL_ON_UNKNOWN_PROPERTIES/*, WRAP_EXCEPTIONS*/);
        }
    }
}
