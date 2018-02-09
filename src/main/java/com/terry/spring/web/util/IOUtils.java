package com.terry.spring.web.util;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by TerryShisan on 2018/2/9.
 */
public class IOUtils {

    public static byte[] readFully1(InputStream inputStream) {
        try {
            return ByteStreams.toByteArray(inputStream);
        } catch (IOException e) {
            return new byte[0];
        }
    }
    //TODO: test
    public static byte[] readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[8384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }


    public static void closeQuietly(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            //ignore
        }
    }

    public static void main(String[] args) {
        byte[] buffer = new byte[0];
        try {
            String jsonString = new String(buffer, "UTF-8");
            System.out.println(jsonString.length());
            System.out.println(jsonString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
