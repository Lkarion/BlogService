package com.example.blogservice.entity.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PostStatus {
    PUBLISHED(0, "Published"),
    UNPUBLISHED(1, "Unpublished"),
    BLOCKED(2, "Blocked");

    private final Integer statusId;
    private final String statusName;

    @JsonValue
    public String getStatusName() {
        return statusName;
    }

    public static PostStatus findByStatusId(Integer statusId) {
        if (statusId == null) {
            return null;
        }

        return Arrays.stream(PostStatus.values())
                .filter(x -> x.getStatusId().equals(statusId))
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static PostStatus findByStatusName(String statusName) {
        if (statusName == null) {
            return UNPUBLISHED;
        }

        return Arrays.stream(PostStatus.values())
                .filter(x -> x.getStatusName().equals(statusName))
                .findFirst()
                .orElse(UNPUBLISHED);
    }
}
