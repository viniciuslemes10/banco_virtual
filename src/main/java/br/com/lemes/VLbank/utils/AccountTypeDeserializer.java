package br.com.lemes.VLbank.utils;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.exceptions.account.InvalidEnumArgumentException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class AccountTypeDeserializer extends JsonDeserializer<AccountType> {

    @Override
    public AccountType deserialize(JsonParser jsonParser,
                                   DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        String value = jsonParser.getText();
        try {
            return AccountType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumArgumentException("Invalid account type: " + value);
        }
    }
}
