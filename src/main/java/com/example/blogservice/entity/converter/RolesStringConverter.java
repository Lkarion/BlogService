package com.example.blogservice.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;

@Converter(autoApply = true)
public class RolesStringConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        return strings == null ? null : String.join(",", strings);
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        return s == null ? null : new HashSet(List.of(s.split(",")));
    }
}
