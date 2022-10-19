package com.example.mysplashangel;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.core.util.PatternsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.mysplashangel.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Regi extends AppCompatActivity {
    private Button con;

    private EditText usua,contra,cor,telf,fec,edad;
    private RadioButton gM,gH;
    private CheckBox cheP, cheS;
    private Switch camP;
    private static final String TANG = "MainActivity";
    public static final String archivo = "archivo.json";
    String json = null;
    public static String usu,paswo,eco,tele,dat,ed;
    public static boolean ze= false;
    public static boolean cod= false;
    public static boolean en;
    public static String[] chey = new String[2];
    public static List<MyInfo> list =new ArrayList<MyInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);


        con = findViewById(R.id.contilogid);
        Button inicio = findViewById(R.id.ini);
        usua = findViewById(R.id.ulogid);
        contra = findViewById(R.id.contlogid);
        cor = findViewById(R.id.meilid);
        telf = findViewById(R.id.numid);
        fec = findViewById(R.id.fchid);
        edad = findViewById(R.id.edaid);
        cheP = findViewById(R.id.mercaid);
        cheS = findViewById(R.id.chosaid );
        gM = findViewById(R.id.femid);
        gH = findViewById(R.id.masid);
        camP = findViewById(R.id.camid);
        ToggleButton togglebutton1 = findViewById(R.id.eufoid);
        Read();
        json2List(json);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Regi.this, longi.class);
                startActivity(intent);
            }
        });

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInfo info= new MyInfo();

                usu= String.valueOf(usua.getText());
                paswo = String.valueOf(contra.getText());
                eco= String.valueOf(cor.getText());
                tele = String.valueOf(telf.getText());
                dat = String.valueOf(fec.getText());
                ed = String.valueOf(edad.getText());

                if(cheP.isChecked()==true){
                    chey[0]="Primero";
                }else{
                    chey[0]="niMaiz";
                }
                if(cheS.isChecked()==true){
                    chey[1]="Segundo";
                }else{
                    chey[1]="niMaiz";
                }
                if(gM.isChecked()==true){
                    en=true;
                }
                if(gH.isChecked()==true){
                    en=true;
                }
                if(camP.isChecked()){
                    ze= true;
                }
                if(togglebutton1.isChecked()){
                    cod= true;
                }

                if(usu.equals("")||paswo.equals("")||eco.equals("")){
                    Log.d(TANG,"No hay nada :(");
                    Log.d(TANG,usu);
                    Log.d(TANG,paswo);
                    Log.d(TANG,eco);
                    Toast.makeText(getApplicationContext(), "Rellena los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(Unidad.valEmail(eco)){
                        if(list.isEmpty()){
                            Log.d(TANG,"Lleno");
                            Unidad.fillInfo(info);
                            List2Json(info,list);
                        }else{
                            if(Unidad.usuar(list,usu)){
                                Log.d(TANG,"Nombre de usuario existente");
                                Toast.makeText(getApplicationContext(), "Confirmar edad", Toast.LENGTH_LONG).show();
                            }else{
                                Unidad.fillInfo(info);
                                List2Json(info,list);
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Confirmar correo", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TANG, "Error en el json");
        }
        else
        {
            Log.d(TANG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Todo correcto :P", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TANG, "Buenas buenas :D");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
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
        return true;
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
    public void json2List( String json)
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }
}