package com.lshadown.zut;

import com.lshadown.zut.utils.Converter;

import java.io.UnsupportedEncodingException;

public class Main {

    private static String json = "a";
    private static Base64 base64 = new Base64();

    public static void main(String[] args) throws UnsupportedEncodingException {

        byte [] data = Converter.objectToByteArray(json);

        System.out.println(base64.encode(data));

    }

}
