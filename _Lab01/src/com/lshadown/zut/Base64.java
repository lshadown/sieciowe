package com.lshadown.zut;

public class Base64 {

    private byte[] encodeData;
    private String charSet =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public Base64(){
        encodeData = new byte[64];
        for (int i = 0; i<64; i++) {
            byte c = (byte) charSet.charAt(i);
            encodeData[i] = c;
        }
    }


    public String encode(byte[] bytes){

        int length = bytes.length;
        int start, x, dstIndex , state, old , len;
        start = x = dstIndex =state = old =len =0;
        byte[] dst = new byte[(length+2)/3 * 4 + length/72];

        int max = length + start;
        for (int srcIndex = start; srcIndex<max; srcIndex++) {
            x = bytes[srcIndex];
            if(++state == 1){
                dst[dstIndex++] = encodeData[(x>>2) & 0x3f];
            }else if(++state == 2){
                dst[dstIndex++] = encodeData[((old<<4)&0x30)
                        | ((x>>4)&0xf)];

            }else if(++state == 3){
                dst[dstIndex++] = encodeData[((old<<2)&0x3C)
                        | ((x>>6)&0x3)];
                dst[dstIndex++] = encodeData[x&0x3F];
                state = 0;
            }
            old = x;
            if (++len >= 72) {
                dst[dstIndex++] = (byte) '\n';
                len = 0;
            }
        }

        return new String(clean(dst, state, dstIndex, old));
    }

    private byte[] clean(byte[] src, int st, int index, int prev){
        switch (st) {
            case 1:
                src[index++] = encodeData[(prev<<4) & 0x30];
                src[index++] = (byte) '=';
                src[index++] = (byte) '=';
                break;
            case 2:
                src[index++] = encodeData[(prev<<2) & 0x3c];
                src[index++] = (byte) '=';
                break;
        }
        return src;
    }
}
