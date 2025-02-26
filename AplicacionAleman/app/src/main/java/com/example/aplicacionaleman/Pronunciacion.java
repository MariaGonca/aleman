package com.example.aplicacionaleman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class Pronunciacion extends AppCompatActivity {

    ImageButton play;
    ImageButton play2;
    ImageButton play3;
    ImageButton play4;
    MediaPlayer mp;
    MediaPlayer mp2;
    MediaPlayer mp3;
    MediaPlayer mp4;

    private MediaRecorder grabacion;
    private String archivoSalida = null;
    private ImageButton btn_recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciacion);

        play = (ImageButton) findViewById(R.id.sonido1);
        mp = MediaPlayer.create(this, R.raw.auf_wiedersehen);

        play2 = (ImageButton) findViewById(R.id.sonido2);
        mp2 = MediaPlayer.create(this, R.raw.vielen_dank);

        play3 = (ImageButton) findViewById(R.id.sonido3);
        mp3 = MediaPlayer.create(this, R.raw.entschudilgen_sie);

        play4 = (ImageButton) findViewById(R.id.sonido4);
        mp4 = MediaPlayer.create(this, R.raw.ich_mag_das);

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.start();
            }
        });



        btn_recorder = (ImageButton)findViewById(R.id.botongrabar);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Pronunciacion.this, new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO}, 1000);
        }


    }

    public void Recorder(View view){
        if(grabacion == null){
            archivoSalida = getExternalFilesDir(null).getAbsolutePath() + "/Grabación.mp3";
            //archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            grabacion.setOutputFile(archivoSalida);
            try{
                grabacion.prepare();
                grabacion.start();
            } catch (IOException e){
            }

            Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
        } else if(grabacion != null){
            grabacion.stop();
            grabacion.release();
            grabacion = null;

            Toast.makeText(getApplicationContext(), "Grabación finalizada", Toast.LENGTH_SHORT).show();
        }
    }

    public void reproducir(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer() ;
        try {
            mediaPlayer.setDataSource( archivoSalida );
            mediaPlayer.prepare() ;
        } catch (IOException e){
        }
        mediaPlayer.start() ;
        Toast.makeText(getApplicationContext() , "Reproduciendo audio" ,
                Toast.LENGTH_SHORT).show();
    }

}