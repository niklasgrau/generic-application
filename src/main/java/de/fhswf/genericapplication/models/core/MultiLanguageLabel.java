package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiLanguageLabel {
    final String regex = "\\[(.{2}):([^\\]]*)\\]";

    @JsonProperty
    private Map<String, String> labels;

    public MultiLanguageLabel(String mlLabel) {
        this.labels = new HashMap<>();

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(mlLabel);

        while (matcher.find()) {
            this.labels.put(matcher.group(1), matcher.group(2));
        }
    }
}