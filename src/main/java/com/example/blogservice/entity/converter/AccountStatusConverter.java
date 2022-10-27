package com.example.blogservice.entity.converter;

import com.example.blogservice.entity.status.AccountStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountStatusConverter implements AttributeConverter<AccountStatus, String> {

    @Override
    public String convertToDatabaseColumn(AccountStatus accountStatus) {
        return accountStatus == null ? null : accountStatus.getStatusName();
    }

    @Override
    public AccountStatus convertToEntityAttribute(String str) {
        return str == null ? null : AccountStatus.findByStatusName(str);
    }
}
