package com.example.blogservice.entity.converter;

import com.example.blogservice.entity.status.PostStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PostStatusConverter implements AttributeConverter<PostStatus, String> {

    @Override
    public String convertToDatabaseColumn(PostStatus postStatus) {
        return postStatus == null ? null : postStatus.getStatusName();    }

    @Override
    public PostStatus convertToEntityAttribute(String str) {
        return str == null ? null : PostStatus.findByStatusName(str);
    }
}
