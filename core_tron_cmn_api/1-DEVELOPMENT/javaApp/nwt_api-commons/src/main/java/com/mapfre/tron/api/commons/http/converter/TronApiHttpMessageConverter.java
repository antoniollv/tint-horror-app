package com.mapfre.tron.api.commons.http.converter;
import java.lang.reflect.Type;
import java.util.Date;
import org.joda.time.DateTime;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.Base64Utils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import springfox.documentation.spring.web.json.Json;

/**
 * Customized MessageConverter. Uses Gson library.
 *
 * @author FBOHORQ
 */
public class TronApiHttpMessageConverter extends GsonHttpMessageConverter {
    public TronApiHttpMessageConverter() {
        super();
        this.setGson(_getGson());
    }
    private Gson _getGson() {
        return new GsonBuilder().addSerializationExclusionStrategy(new PrcExclusionStrategy())
                .registerTypeHierarchyAdapter(Date.class, new DateTypeAdapter())
                .registerTypeHierarchyAdapter(byte[].class, new Base64TypeAdapter())
                .registerTypeHierarchyAdapter(Json.class, new SpringfoxJsonToGsonAdapter())
                // Could not write JSON Attempted to serialize java.lang.Class Forgot to register a type adapter?
                // This was due to org.springframework.boot.actuate.beans.BeansEndpoint.BeanDescriptor having a
                // java.lang.Class<?> attribute
                // Added to resolve it
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes field) {
                        return field.getDeclaredType().getTypeName().equals("java.lang.Class<?>");
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                /*
                 * The root cause of the problem is that GSON serialises JSON using fields of any visibility,
                 * whereas Jackson relies on public getters by default. This results in the private registration field
                 * of RegistrationMappingDescription being serialised when it should not be. This registration is a
                 * org.apache.catalina.core.ApplicationServletRegistration. Within its object graph it contains a
                 * circular reference between a registry field and a reader field which causes Gson to blow up with a
                 * StackOverflowError.
                 * You can work around the problems caused by Gson's behaviour in a rather rudimentary manner by using
                 * a custom exclusion strategy:
                 */
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return "registration".equals(f.getName());
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }
    private static class PrcExclusionStrategy implements ExclusionStrategy {
        public boolean shouldSkipField(FieldAttributes arg0) {
            return false;
        }
        public boolean shouldSkipClass(Class<?> arg0) {
            return arg0.equals(OTrnPrcS.class);
        }
    }
    private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null) {
                return null;
            } else {
                return new JsonPrimitive(src.getTime());
            }
        }
        public Date deserialize(JsonElement json, Type type, JsonDeserializationContext cxt) {
            Date result = null;
            if (json != null) {
                try {// milliseconds
                    result = new Date(json.getAsLong());
                } catch (NumberFormatException nfe) {// ISO 8601
                    result = new DateTime(json.getAsString()).toDate();
                }
            }
            return result;
        }
    }
    private static class Base64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64Utils.encodeToString(src));
        }
        public byte[] deserialize(JsonElement json, Type type, JsonDeserializationContext cxt) {
            return Base64Utils.decodeFromString(json.getAsString());
        }
    }
    private static class SpringfoxJsonToGsonAdapter implements com.google.gson.JsonSerializer<Json> {
        public JsonElement serialize(Json json, Type type, JsonSerializationContext context) {
            final JsonParser parser = new JsonParser();
            return parser.parse(json.value());
        }
    }
}
