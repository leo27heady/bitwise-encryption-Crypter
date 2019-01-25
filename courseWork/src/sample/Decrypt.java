package sample;

import com.jfoenix.controls.JFXTextArea;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Decrypt {

    private int _bit = 0;
    private byte[] bytes;
    private String newFileName = "";

    public void decrypt(File file, File fileKey, int bit, JFXTextArea DecryptTextArea) throws IOException {

        this._bit = bit;

        BufferedInputStream input = new BufferedInputStream( new FileInputStream(file));
        BufferedInputStream keyInput = new BufferedInputStream( new FileInputStream(fileKey));
        bytes = new byte[input.available()];
        byte[] keyBytes = new byte[keyInput.available()];

        input.read(bytes);
        keyInput.read(keyBytes);

        decryptMass(bytes, keyBytes, _bit, _bit);

        newFileName = createFileName(file.getAbsolutePath(), "_d");

        DecryptTextArea.setText(new String(bytes));

    }

    private void decryptMass(byte[] bytes, byte[] keyBytes, int _byte, int _bit){
        BigInteger massBytes;
        String array, massBites = "", zero = "";

        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = xorBites(keyBytes[i], _bit);
            massBites += (char)keyBytes[i];
        }

        massBytes = new BigInteger(massBites, 16);
        massBites = massBytes.toString(2);

        if(bitLenth(bytes.length, _byte) - massBites.length() > 0) {
            for (int i = 0; i < bitLenth(bytes.length, _byte) - massBites.length(); i++) zero += "0";
            massBites = zero + massBites;
        }

        for (int i = 0, b = massBites.length() - 1; i < bytes.length; i++) if(i%_byte==0){
            massBytes = BigInteger.valueOf(bytes[i]);

            array = massBytes.toString(2);
            array = insertCharAt(array, massBites.charAt(b), array.length() - _bit);

            massBytes = new BigInteger(array, 2);
            bytes[i] = massBytes.byteValueExact();
            b--;
        }
    }

    private byte xorBites(byte byte_i, int _bit) {
        if(_bit == 0) _bit = 1;
        if (!isPaired(byte_i)) byte_i ^= (byte) (1 << _bit);
        return byte_i;
    }

    private boolean isPaired(byte b) {
        if(b%2==0) return true;
        else return false;
    }

    private String createFileName(String fileName, String text){
        String newFileName = "";
        int ind = fileName.lastIndexOf('.');
        if (ind > 0) {
            newFileName = fileName.substring(0, ind) + text + fileName.substring(ind);
        }
        return newFileName;
    }

    private String insertCharAt(String s, char symb, int pos){
        return s.substring(0, pos) + symb + s.substring(pos);
    }

    private int bitLenth(int len, int _byte){
        int i = len/_byte;
        return len%_byte == 0? i : ++i;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getNewFileName() {
        return newFileName;
    }
}