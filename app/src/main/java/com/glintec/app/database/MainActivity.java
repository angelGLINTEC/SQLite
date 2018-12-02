package com.glintec.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnCreate, btnDescr, btnDel, btnMod, btnConsult;
    private EditText edTPrice, edTCode, edTDescr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConsult =findViewById(R.id.btnConsult);
        View.OnClickListener clconsultItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultItem();
            }
        };
        btnConsult.setOnClickListener(clconsultItem);

        btnCreate = findViewById(R.id.btnCreate);
        View.OnClickListener clcreateItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItem();
            }
        };
        btnCreate.setOnClickListener(clcreateItem);

        btnDel = findViewById(R.id.btnDelate);
        View.OnClickListener cldeletItem = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteItem();
            }
        };
        btnDel.setOnClickListener(cldeletItem);

        btnMod = findViewById(R.id.btnModify);
        View.OnClickListener clmodifyItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyItem();
            }
        };
        btnMod.setOnClickListener(clmodifyItem);

        btnDescr = findViewById(R.id.btnDescription);
        View.OnClickListener clcodeItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeItem();
            }
        };
        btnDescr.setOnClickListener(clcodeItem);

        edTCode = findViewById(R.id.edTxtCode);
        edTDescr = findViewById(R.id.edTxtDescr);
        edTPrice = findViewById(R.id.edTxtPrice);


    }

    private void createItem(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administacion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = edTCode.getText().toString();
        String descripcion = edTDescr.getText().toString() ;
        String precio = edTPrice.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo",codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio",precio);

        db.insert("articulos",null,registro);

        db.close();

        edTPrice.setText("");
        edTDescr.setText("");
        edTCode.setText("");
        Toast.makeText(this,"Se ha dado de alta el articulo", Toast.LENGTH_LONG).show();
    }

    private void codeItem()
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administacion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String descripcion = edTDescr.getText().toString();

//        clase para acceder a base de datos (posicion de memoria)
        Cursor fila = db.rawQuery("select codigo, precio from articulos where descripcion = '" + descripcion +"'",null);
        if(fila.moveToFirst()){
            edTCode.setText(fila.getString(0));
            edTPrice.setText(fila.getString(1));
        }else{
            Toast.makeText(this,"Esta descripcion no corresponde a ningun articulo",
                    Toast.LENGTH_LONG).show();
        }
        db.close();
    }
    private void consultItem(){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administacion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo = edTCode.getText().toString();

//        clase para acceder a base de datos (posicion de memoria)
        Cursor fila = db.rawQuery("select descripcion, precio from articulos where codigo = " + codigo,null);
        if(fila.moveToFirst()){
            edTDescr.setText(fila.getString(0));
            edTPrice.setText(fila.getString(1));
        }else{
            Toast.makeText(this,"Este identificador no corresponde a ningun articulo",
                    Toast.LENGTH_LONG).show();
        }
        db.close();
    }
    private void deleteItem(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administacion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo = edTCode.getText().toString();

        int cantidad = db.delete("articulos","codigo="+codigo,null);
        db.close();
        edTCode.setText("");
        edTDescr.setText("");
        edTPrice.setText("");

        if(cantidad ==1){
            Toast.makeText(this,"Se ha borrado el articulo correctamente",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Este articulo no existe con el codigo asignado",Toast.LENGTH_LONG).show();
        }

    }
    private void modifyItem(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administacion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigo = edTCode.getText().toString();
        String descripcion = edTDescr.getText().toString();
        String precio = edTPrice.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo",codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio",precio);

        int cantidad = db.update("articulos",registro,"codigo = "+codigo,null);
        db.close();
        if(cantidad==1){
            Toast.makeText(this, "El articulo se ha modificado correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"No existe este articulo para modificarse",Toast.LENGTH_LONG).show();
        }

    }
}
