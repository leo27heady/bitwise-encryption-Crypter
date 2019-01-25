package sample;

import com.jfoenix.controls.JFXTextArea;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;

public class Crypt {

    private int _bit = 0;
    private byte[] bytes;
    private byte[] keyBytes;
    private String newFileName = "";
    private String newKeyFile = "";

    public void cryptFile(File file, int bit, JFXTextArea CryptTextArea, JFXTextArea enterTextArea) throws IOException {

        _bit = bit;

        BufferedInputStream input = new BufferedInputStream( new FileInputStream(file));
        bytes = new byte[input.available()];

        input.read(bytes);
        enterTextArea.setText(new String(bytes));

        cryptMass(bytes, _bit, _bit);

        newFileName = createFileName(file.getAbsolutePath(), "_c");
        System.out.println(newFileName);


        System.out.println(new String(bytes));
        CryptTextArea.setText(new String(bytes));

    }

    public void cryptText(String text, int bit, JFXTextArea CryptTextArea) throws IOException {

        _bit = bit;

        bytes = text.getBytes();

        cryptMass(bytes, _bit, _bit);

        System.out.println(new String(bytes));
        CryptTextArea.setText(new String(bytes));

    }

    private void cryptMass(byte[] bytes, int _byte, int _bit) throws IOException{
        BigInteger massBytes;
        String array, massBites = "";

        for (int i = 0; i < bytes.length; i++) if(i%_byte==0){

            massBytes = BigInteger.valueOf(bytes[i]);

            array = massBytes.toString(2);
            massBites = array.charAt(array.length() - _bit - 1) + massBites;

            array = removeCharAt(array, array.length() - _bit - 1);
            massBytes = new BigInteger(array, 2);
            bytes[i] = massBytes.byteValueExact();
        }
        createKeyFile(massBites);
    }

    private void createKeyFile(String massBites) throws IOException{
        BigInteger massBytes;
        String array;

        massBytes = new BigInteger(massBites, 2);
        array = massBytes.toString(16);

        keyBytes = new byte[array.length()];

        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte)array.charAt(i);
            keyBytes[i] = xorBites(keyBytes[i], _bit);
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

    private String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public String createFileName(String fileName, String text){
        String newFileName = "";
        int ind = fileName.lastIndexOf('.');
        if (ind > 0) {
            newFileName = fileName.substring(0, ind) + text + fileName.substring(ind);
        }
        return newFileName;
    }

    private int sizeArray(String massBites){
        int i = massBites.length()/6;
        return massBites.length()%6 == 0? i : ++i;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] getKeyBytes() {
        return keyBytes;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public String getNewKeyFile() {
        return newKeyFile;
    }
}