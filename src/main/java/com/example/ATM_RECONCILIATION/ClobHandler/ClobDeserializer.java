package com.example.ATM_RECONCILIATION.ClobHandler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.sql.Clob;

@JsonComponent
public class ClobDeserializer extends JsonDeserializer<Clob> {
    @Override
    public Clob deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String clobContent = jsonParser.getText(); // Assuming the CLOB value is passed as a string

		// Create a WritableClob object
		Clob clob = new WritableClob(clobContent);

		return clob;
    }
}
