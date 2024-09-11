package br.com.lemes.VLbank.utils;

import br.com.lemes.VLbank.enums.transaction.TransactionAccountType;
import br.com.lemes.VLbank.exceptions.account.InvalidEnumArgumentException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TransactionAccountTypeDeserializer extends JsonDeserializer<TransactionAccountType> {
    @Override
    public TransactionAccountType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getText();
        try {
            return TransactionAccountType.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new InvalidEnumArgumentException("Transaction expense type: " + value);
        }
    }
}
