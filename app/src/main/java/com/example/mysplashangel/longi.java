package com.example.mysplashangel;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mysplashangel.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class longi extends AppCompatActivity {
    public static List<MyInfo> listo;
    public static String TANG = "mensaje";
    public static String json = null;
    public static String per,pas;
    private Button buttonP, buttonS, buttonT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longi);
        buttonS = findViewById(R.id.regisid);
        buttonP = findViewById(R.id.accid);
        buttonT = findViewById(R.id.olvid);
        EditText usua = findViewById(R.id.uNameid);
        EditText cont = findViewById(R.id.editTextTextPassword);
        Read();
        json2List(json);
        if (json == null || json.length() == 0){
            buttonP.setEnabled(false);
            buttonT.setEnabled(false);
        }
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                per = String.valueOf(usua.getText());
                pas = String.valueOf(cont.getText())+ per;
                pas = Unidad.BytesOHex(Unidad.creSha1(pas));
                acceso(per , pas);
            }
        });
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(longi.this, Regi.class);
                startActivity(intent);
            }
        });
        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                per = String.valueOf(usua.getText());
                pas = Unidad.BytesOHex(Unidad.creSha1(String.valueOf(cont.getText())));
                if(per.equals("")||pas.equals("")){
                    Toast.makeText(getApplicationContext(), "Rellena los apartados", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(longi.this,Olvido.class);
                    startActivity(intent);
                }
            }
        });
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
            Log.d(TANG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
        listo = gson.fromJson(json, listType);
        if (listo == null || listo.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "ERROR list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }
    private File getFile( )
    {
        return new File( getDataDir() , Regi.archivo );
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
    public void acceso(String usr , String pswd){
        int i=0;
        if(usr.equals("")||pswd.equals("")){
            Toast.makeText(getApplicationContext(), "Rellene los apartados", Toast.LENGTH_LONG).show();
        }else{
            for(MyInfo myInfo : listo){
                if(myInfo.getUser().equals(usr)&&myInfo.getContrasena().equals(pswd)){
                    Intent intent = new Intent(longi.this, Prota.class);
                    startActivity(intent);
                    i=1;
                }
            }
            if(i==0){
                Toast.makeText(getApplicationContext(), "El usuario y contraseña no coinciden", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void olvo_cont(String usr, String pswd){
        if(usr.equals("")||pswd.equals("")){
            Toast.makeText(getApplicationContext(), "Rellene los apartados", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(longi.this,Olvido.class);
            startActivity(intent);
        }
    }
}