package com.example.noted;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        String noteTitleText = getIntent().getStringExtra("noteTitle");

        final EditText noteTitle = findViewById(R.id.noteTitle);
        noteTitle.setText(noteTitleText);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile(noteTitle.getText().toString() + ".txt");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveFile(String fileName) {
        EditText noteContent = findViewById(R.id.noteBody);
        if (!fileExists(fileName)) {
            try {
                OutputStreamWriter out = new OutputStreamWriter(openFileOutput(fileName, 0));
                out.write(noteContent.getText().toString());
                out.close();
                Toast.makeText(this, "Saved to " + fileName, Toast.LENGTH_SHORT).show();

            } catch (Throwable t) {
                Toast.makeText(this,
                        "Exception" + t.toString(), Toast.LENGTH_LONG).show();

            }

        }

        else {
            Toast.makeText(this, "File Name Unavailable", Toast.LENGTH_LONG).show();

        }
    }

    public void loadFile(String fileName) {
        if (fileExists(fileName)){
            try {
                File savedNote = new File(fileName);

                Scanner scanIt = new Scanner(savedNote);

                EditText noteBody = (EditText)findViewById(R.id.noteBody);

                String tempOut = "";

                while (scanIt.hasNextLine()){
                    tempOut = tempOut + scanIt.nextLine();

                }

                noteBody.setText(tempOut);

            }
            catch(Throwable t) {
                Toast.makeText(this,
                        "Exception" + t.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }

    public boolean fileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    public void onSubmit(View v){
        this.finish();
    }


}
