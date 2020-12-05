package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.content.ContentValues;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    int button_count=1,pos;
    int i =1;
    int  tcount, tcount2;
    DBHelper helper = new DBHelper(this);
    SQLiteDatabase database;
    Boolean del_press, save_press, first_time=true, reopen=false;
    EditText mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        del_press=false;
        save_press=true;
        tcount=1;
        tcount2=tcount-1;
        if(reopen){
            String copy = "INSERT INTO TAB0"+"(NOTE) SELECT NOTE FROM TABS";
            database.execSQL(copy);
        }
    }







    public void saveData(View view) {


        ArrayList<String>arrayList = new ArrayList<>();
        ListView show =findViewById(R.id.NoteShow);
       /*
        if (button_count>1){
         database.delete("TAB", "_id = ?", new String[]{""+i});
         i++;
       }
        */
        //show.setVisibility(View.GONE);
        if (button_count==1 && first_time==true){
            database = helper.getWritableDatabase();
            first_time =false;
        }

        button_count++;

if(del_press==false) {
    mess = (EditText) findViewById(R.id.Note);
    String message = mess.getText().toString();
    ContentValues values = new ContentValues();
    values.put("NOTE", message);
    database.insert("TAB" + tcount2, null, values);
}










        Cursor cursor = database.rawQuery("SELECT NOTE FROM TAB"+tcount2, new String[]{});

        if (cursor != null) {
            cursor.moveToFirst();
        }
        do {
            String a = cursor.getString(0);

            arrayList.add(a);
        } while (cursor.moveToNext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        show.setAdapter(adapter);

        /*
     if(del_press==true){
       // database.execSQL(" UPDATE TAB SET COUNT = NULL");
        //ContentValues cv= new ContentValues();
        for(int j=pos;j<=arrayList.size()-1;j++){

            database.execSQL(" UPDATE TAB SET COUNT = "+j +" WHERE COUNT = "+j+1);
        }

        //save_press = false;
        saveData(view);

} */
      /*   database.execSQL(" UPDATE TAB SET COUNT = NULL");
        ContentValues cv= new ContentValues();
        for(int j=pos;j<=arrayList.size();j++){

            database.execSQL(" UPDATE TAB SET COUNT = "+j +" WHERE");
        }

        //database.insert("TAB",null,cv);
*/

        del_press=false;
        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter1, View view, int position, long id) {
                //  ContentValues cv = new ContentValues();
                //  for(int i =1; i<= arrayList.size();i++){
                //     cv.put("COUNT",i);
                //  }database.insert("TAB", null, cv);
                if(arrayList.size()>1){
                int px = position+1;
                database.execSQL("DELETE FROM TAB"+tcount2+" WHERE _id = " +px);
                database.execSQL("DROP TABLE IF EXISTS TAB"+tcount);
                String sql = "CREATE TABLE TAB"+ tcount+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, NOTE TEXT)";
                database.execSQL(sql);

                String copy = "INSERT INTO TAB"+tcount+"(NOTE) SELECT NOTE FROM TAB"+tcount2;
                database.execSQL(copy);
               // tcount++;
                database.execSQL("DROP TABLE IF EXISTS TAB"+tcount2);
                    tcount++;
                    tcount2++;
                    del_press=true;
                    saveData(view);
                }
              /*  ArrayList<String>arrayList = new ArrayList<>();
             //   ListView show =findViewById(R.id.NoteShow);
               //

                Cursor cursor = database.rawQuery("SELECT NOTE FROM TAB"+tcount, new String[]{});

                if (cursor != null) {
                    cursor.moveToFirst();
                }
                do {
                    String a = cursor.getString(0);

                    arrayList.add(a);
                } while (cursor.moveToNext());*/

              //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.,arrayList);
             //   show.setAdapter(adapter);

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
  /*     String del = "DROP TABLE IF EXISTS TABS";
       String make ="CREATE TABLE TABS (_id INTEGER PRIMARY KEY AUTOINCREMENT, NOTE TEXT)";
       String copy = "INSERT INTO TABS"+"(NOTE) SELECT NOTE FROM TAB"+tcount2;
        database.execSQL(del);
        database.execSQL(make);
        reopen = true;
        database.execSQL(copy);*/
    }
}
