package com.example.blogservice.dto.converter;

import com.example.blogservice.dto.postDTOs.PostResponseShortDTO;
import com.example.blogservice.entity.BlogPost;

import java.util.List;

public class ConverterDTOList {

//    private final ConverterDTO converterDTO = new ConverterDTO();

    public static List<PostResponseShortDTO> convertPostsListToDTOShort(List<BlogPost> posts) {
        return posts.stream()
                .map(ConverterDTO::convertPostToDTOShort)
                .toList();
    }
}
