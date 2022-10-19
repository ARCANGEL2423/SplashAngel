package com.example.mysplashangel;

import androidx.core.util.PatternsCompat;

import com.example.mysplashangel.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Unidad {
    public static final String TAG = "ARCAN";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();


    public static byte[] creSha1( String text )
    {
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String BytesOHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static boolean valEmail(String email){
        boolean fl;
        if(email.isEmpty()){
            fl=false;
        }else{
            if(PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
                fl=true;
            }else{
                fl=false;
            }
        }
        return fl;
    }
    public static boolean usuar(List<MyInfo> list, String usu){
        boolean fl = false;
        for(MyInfo informacion : list){
            if(informacion.getUser().equals(usu)){
                fl=true;
            }
        }
        return fl;
    }

    public static void fillInfo(MyInfo info){
        info.setUser(Regi.usu);
        String pass = Regi.paswo + Regi.usu;
        info.setContrasena(BytesOHex(creSha1(pass)));
        info.setNumero(Regi.tele);
        info.setFecha(Regi.dat);
        info.setEscuela(Regi.chey);
        info.setCorreo(Regi.eco);
        info.setGen(Regi.en);
        info.setNotifi(Regi.ze);
        info.setFeliz(Regi.cod);
        info.setEdad(Regi.ed);
    }
    public static void vaciaJson(String json){
        json = null;
    }


    public static void encuentra(String cadena){
        for(MyInfo info: Olvido.list){
            if(longi.per.equals(info.getUser())){
                cadena = "Usuario existente, la contraseña no coincide";
            }else{
                cadena = "El usuario no esta registrado";
            }
        }
    }
}


