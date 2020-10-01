package com.example.qrsiswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qrsiswa.qrscan.ScanActivity;
import com.example.qrsiswa.tambahSiswa.TambahSiswaActivity;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    CardView cvBtnScan, cvBtnKelompok, cvBtnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvBtnKelompok = findViewById(R.id.cvKelompok);
        cvBtnScan = findViewById(R.id.cvScan);
        cvBtnTambah = findViewById(R.id.cvTambahSiswa);

        cvBtnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahSiswaActivity.class);
                startActivity(intent);
            }
        });

        cvBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "camera", Toast.LENGTH_LONG);
                    Intent intent = new Intent(getBaseContext(), ScanActivity.class);
                    intent.putExtra("before", "bayar");
                    startActivity(intent);
                } else {
                    EasyPermissions.requestPermissions(MainActivity.this, "Izinkan kami untuk mengakses kamera", 27, Manifest.permission.CAMERA);
                }
            }
        });
    }
}