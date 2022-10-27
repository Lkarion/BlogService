package com.example.blogservice.entity.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AccountStatus {
    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active");

    private final Integer statusId;
    private final String statusName;

    @JsonValue
    public String getAccountStatusName() {
        return statusName;
    }

    public static AccountStatus findByStatusId(Integer statusId) {
        if (statusId == null) {
            return null;
        }

        return Arrays.stream(AccountStatus.values())
                .filter(x -> x.getStatusId().equals(statusId))
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static AccountStatus findByStatusName(String statusName) {
        if (statusName == null) {
            return ACTIVE;
        }

        return Arrays.stream(AccountStatus.values())
                .filter(x -> x.getStatusName().equals(statusName))
                .findFirst()
                .orElse(ACTIVE);
    }
}
