//package com.example.blogservice.entity.converter;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//
//@Converter(autoApply = true)
//public class TagsStringConverter implements AttributeConverter<String[], String> {
//
//    @Override
//    public String convertToDatabaseColumn(String[] strings) {
//        return strings == null ? null : String.join(",", strings);
//    }
//
//    @Override
//    public String[] convertToEntityAttribute(String s) {
//        return s == null ? null : s.split(",");
//    }
//}
