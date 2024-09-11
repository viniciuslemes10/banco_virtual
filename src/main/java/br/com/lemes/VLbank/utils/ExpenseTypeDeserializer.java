package br.com.lemes.VLbank.utils;

import br.com.lemes.VLbank.enums.transactions.ExpenseType;
import br.com.lemes.VLbank.exceptions.account.InvalidEnumArgumentException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ExpenseTypeDeserializer extends JsonDeserializer<ExpenseType> {
    @Override
    public ExpenseType deserialize(JsonParser jsonParser,
                                   DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        String value = jsonParser.getText();
        try {
            return ExpenseType.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new InvalidEnumArgumentException("Invalid expense type: " + value);
        }
    }
}
