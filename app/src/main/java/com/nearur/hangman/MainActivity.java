package com.nearur.hangman;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    EditText editTextLetter;
    TextView chances,guess,guessedletters;
    String name="";
    ImageButton playagain;
    ArrayList<String> questions;
    String game,check="AEIOU";
    int chancesleft;
    char[] array;
    boolean win=false;
    StringBuffer buffer=new StringBuffer();
    StringBuffer guesses=new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initdialog();
        init();
    }

    void initdialog(){
       final Dialog d=new Dialog(MainActivity.this);
        d.setContentView(R.layout.dialog);
        d.setTitle("HangMan");
        d.show();
        Button b=(Button)d.findViewById(R.id.buttonstart);
        final EditText editText=(EditText)d.findViewById(R.id.editTextname);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length()>0){
                    name=editText.getText().toString();
                    d.dismiss();
                    init();
                }else{
                    Toast.makeText(MainActivity.this,"Please Enter a Valid Name",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    void init(){
        chancesleft=7;
        guess=(TextView)findViewById(R.id.textViewstring);
        playagain=(ImageButton)findViewById(R.id.playagain);
        playagain.setVisibility(View.GONE);
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guess.setText("");
                buffer.delete(0,buffer.length());
                guesses.delete(0,guesses.length());
                guessedletters.setText("");
                playagain.setVisibility(View.GONE);
                init();
            }
        });
        chances=(TextView)findViewById(R.id.textViewchances);
        guessedletters=(TextView)findViewById(R.id.guessedletters);
        editTextLetter=(EditText)findViewById(R.id.editTextstring);
        questions=new ArrayList<>();
        questions.add("ABRUPTLY");
        questions.add("BOOKWORM");
        questions.add("GALAXY");
        questions.add("PNEUMONIA");
        questions.add("STRONGHOLD");
        questions.add("WHIZZING");
        questions.add("PUZZLING");
        questions.add("WRISTWATCH");
        questions.add("ZODIAC");
        questions.add("THUMBSCREW");
        questions.add("YACHTSMAN");
        questions.add("MNEMONIC");
        questions.add("TRANSPLANT");
        questions.add("MICROWAVE");
        Collections.shuffle(questions);
        game=questions.get(0);
        chances.setText("Chances Left : "+chancesleft);
        array=new char[game.length()];
        for(int i=0;i<game.length();i++){
            if(check(game.charAt(i))){
                array[i]=game.charAt(i);
            }else{
                array[i]='_';
            }
        }
        for (char x: array){
            buffer.append(x+" ");
        }
        guess.setText(buffer.toString());

        editTextLetter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN && i==KeyEvent.KEYCODE_ENTER){
                 enter(editTextLetter.getText().toString().toUpperCase().charAt(0));
                }
                return false;
            }
        });
    }

     boolean check(char c){
        if(check.indexOf(c)>=0){
           return true;
        }return false;
    }


    void enter(char c) {
        if (guesses.toString().contains(c + "")) {
            Toast.makeText(MainActivity.this, "Already Guessed", Toast.LENGTH_SHORT).show();
        } else if (buffer.toString().contains(c + "")) {
            Toast.makeText(MainActivity.this, "Already There", Toast.LENGTH_SHORT).show();
        } else {if (game.contains(c + "")) {
            int idx = game.indexOf(c);
            while (idx >= 0) {
                array[idx] = c;
                idx = game.indexOf(c, idx + 1);
            }
            buffer.delete(0, buffer.length());
            win = true;
            for (char x : array) {
                if (x == '_') {
                    win = false;
                }
                buffer.append(x + " ");
            }
            guess.setText(buffer.toString());

            Toast.makeText(MainActivity.this, "Good Guess", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Bad Guess", Toast.LENGTH_SHORT).show();
            chancesleft--;
        }
            guesses.append(c+",");
            guessedletters.setText("Guessed Letters : "+guesses.toString().substring(0,guesses.length()-1));
            chances.setText("Chances Left : "+chancesleft);
            if(chancesleft==0||win==true){
                done();
            }
    }editTextLetter.setText("");
    }

    void done(){
        if(win){
            guess.setText("The Correct Word is : "+game+"\nCongratulations,You Win!!");
        }else{
            guess.setText("The Correct Word is : "+game+"\nYou Loose,Don't worry \nTry Again");
        }
        playagain.setVisibility(View.VISIBLE);
    }
}
