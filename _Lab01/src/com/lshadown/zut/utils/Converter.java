package com.lshadown.zut.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class Converter {

    public static final byte[] objectToByteArray(String txt) throws UnsupportedEncodingException {
       return txt.getBytes("UTF-8");
    }
    public static  final String bytesToString(byte[] bytes){
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
