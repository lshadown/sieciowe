package com.lshadown.zut.utils;

import java.io.*;

public final class Converter {

    public static final byte[] objectToByteArray(String txt) throws UnsupportedEncodingException {
       return txt.getBytes("UTF-8");
    }

}
