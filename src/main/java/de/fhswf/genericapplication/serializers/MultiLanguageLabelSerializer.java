package de.fhswf.genericapplication.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.fhswf.genericapplication.models.core.MultiLanguageLabel;

import java.io.IOException;

/**
 * Serializer to serialize {@link MultiLanguageLabel} to json.
 *
 * @author Niklas Grau
 */
public class MultiLanguageLabelSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String mlLabel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        MultiLanguageLabel multiLanguageLabel = new MultiLanguageLabel(mlLabel);
        jsonGenerator.writeObject(multiLanguageLabel);
    }
}