package com.example.ATM_RECONCILIATION.ClobHandler;

import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;

public class WritableClob implements Clob {
    private StringBuilder content;

    public WritableClob(String str) {
        this.content = new StringBuilder(str);
    }

    @Override
    public long length() throws SQLException {
        return content.length();
    }

    @Override
    public String getSubString(long pos, int length) throws SQLException {
        return content.substring((int) pos - 1, (int) pos - 1 + length);
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        return new StringReader(content.toString());
    }

    @Override
    public InputStream getAsciiStream() throws SQLException {
        return new ByteArrayInputStream(content.toString().getBytes());
    }

    @Override
    public long position(String searchStr, long start) throws SQLException {
        return content.indexOf(searchStr, (int) start - 1) + 1;
    }

    @Override
    public long position(Clob searchStr, long start) throws SQLException {
        return position(searchStr.getSubString(1, (int) searchStr.length()), start);
    }

    @Override
    public int setString(long pos, String str) throws SQLException {
        if (pos < 1 || pos > content.length() + 1) {
            throw new SQLException("Invalid position");
        }

        int index = (int) pos - 1;
        content.replace(index, index + str.length(), str);
        return str.length();
    }

    @Override
    public int setString(long pos, String str, int offset, int len) throws SQLException {
        if (pos < 1 || pos > content.length() + 1) {
            throw new SQLException("Invalid position");
        }

        int index = (int) pos - 1;
        content.replace(index, index + len, str);
        return len;
    }

    @Override
    public OutputStream setAsciiStream(long pos) throws SQLException {
        throw new UnsupportedOperationException("Not supported");
    }

//    @Override
//    public Writer setCharacterStream(long pos) throws SQLException {
//        throw new UnsupportedOperationException("Not supported");
//    }

    @Override
    public void truncate(long len) throws SQLException {
        if (len < 0) {
            throw new SQLException("Invalid length");
        }

        if (len < content.length()) {
            content.setLength((int) len);
        }
    }

    @Override
    public void free() throws SQLException {
        content = null;
    }

    @Override
    public Reader getCharacterStream(long pos, long length) throws SQLException {
        String str = getSubString(pos, (int) length);
        return new StringReader(str);
    }
    
    @Override
    public Writer setCharacterStream(long pos) throws SQLException {
        if (pos < 1 || pos > content.length() + 1) {
            throw new SQLException("Invalid position");
        }

        int index = (int) pos - 1;

        Writer writer = new Writer() {
            private int currentIndex = index;

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                if (currentIndex + len > content.length()) {
                    content.setLength(currentIndex + len);
                }

                for (int i = 0; i < len; i++) {
                    content.setCharAt(currentIndex++, cbuf[off + i]);
                }
            }

            @Override
            public void flush() throws IOException {
                // No flushing required
            }

            @Override
            public void close() throws IOException {
                // No closing required
            }
        };

        return writer;
    }

}
