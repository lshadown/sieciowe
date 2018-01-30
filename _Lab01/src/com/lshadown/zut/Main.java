package com.lshadown.zut;

import com.lshadown.zut.utils.Converter;

import java.io.UnsupportedEncodingException;

public class Main {

    private static String text = "TOL";
    private static Base64 base64 = new Base64();

    public static void main(String[] args) throws UnsupportedEncodingException {

        byte [] data = Converter.objectToByteArray(text);
        //encode
        String result = base64.encode2(data);
        System.out.println(result);
        //decode
        String result2 = Converter.bytesToString(base64.decode2(result));
        System.out.println(result2);

    }

}
