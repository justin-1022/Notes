package com.example.noted;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout content = new ConstraintLayout(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNote();
            }
        });


        String[] files = MainActivity.this.getFilesDir().list();
        final String[] noteList = new String[files.length];
        TextView tempFileList = findViewById(R.id.tempFileList);
        String tempString = "";
        for (int i = 0; i < files.length; i++)
        {
            String fileName = files[i];
            String[] splitName = fileName.split(".", 1);
            String noteTitle = splitName[0];

            Button noteButton = new Button(this);
            noteButton.setText(noteTitle);
            noteButton.setId(i);
            noteButton.setLayoutParams(new ConstraintLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            noteList[i] = noteTitle;

            tempString = tempString + noteTitle + ", ";

            content.addView(noteButton);

        }
        tempFileList.setText(tempString);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNote(){
        String noteTitle = "Untitled";
        int counter = 0;

        while (fileExists(noteTitle + "(" + counter +  ").txt")) {
            counter += 1;
        }

        noteTitle = noteTitle + "(" + counter + ")";

        Intent toNoteScreen = new Intent(MainActivity.this, NoteActivity.class);
        toNoteScreen.putExtra("noteTitle", noteTitle);

        startActivity(toNoteScreen);

    }

    public void openNote(String noteTitle){
        String fileName = noteTitle + ".txt";

        Intent toNoteScreen = new Intent(MainActivity.this, NoteActivity.class);
        toNoteScreen.putExtra("fileName", fileName);


        startActivity(toNoteScreen);

    }

    public boolean fileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }
}
