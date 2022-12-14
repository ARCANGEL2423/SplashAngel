package com.example.mysplashangel;

import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mysplashangel.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Olvido extends AppCompatActivity {
    public static List<MyInfo> list;
    public static String json = null;
    public static String TAG = "mensaje";
    public static String cadena= null;
    TextView sag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido);
        sag = findViewById(R.id.importa);
        Read();
        json2List(json);
        find();
    }
    private File getFile( )
    {
        return new File( getDataDir() , Regi.archivo);
    }
    private boolean isFileExits( )
    {
        File file = getFile( );
        if( file == null )
        {
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void json2List( String json )
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {

            Toast.makeText(getApplicationContext(), "ERROR json null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "ERROR list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public boolean Read(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json=new String(bytes);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void find(){
        for(MyInfo info: list){
            if(longi.per.equals(info.getUser())){
                sag.setText("Contrase??a equivocada");
            }else{
                sag.setText("Este usuario no existe");
            }
        }
    }
}