package br.com.lemes.VLbank.enums.account;

import br.com.lemes.VLbank.utils.AccountTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AccountTypeDeserializer.class)
public enum AccountType {
    CURRENT,
    SAVINGS,
    CURRENT_AND_SAVINGS
}
