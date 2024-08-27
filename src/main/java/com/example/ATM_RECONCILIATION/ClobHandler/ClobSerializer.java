package com.example.ATM_RECONCILIATION.ClobHandler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobSerializer extends JsonSerializer<Clob> {
    @Override
    public void serialize(Clob clob, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            Reader reader = clob.getCharacterStream();
            char[] buffer = new char[1024];
            StringBuilder sb = new StringBuilder();
            int numChars;
            while ((numChars = reader.read(buffer, 0, buffer.length)) != -1) {
                sb.append(buffer, 0, numChars);
            }
            jsonGenerator.writeString(sb.toString());
        } catch (SQLException e) {
            throw new IOException("Error serializing CLOB", e);
        }
    }
}
