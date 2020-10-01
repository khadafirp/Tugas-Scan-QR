package com.example.qrsiswa.tambahSiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.qrsiswa.R;

public class TambahSiswaActivity extends AppCompatActivity {

    EditText edtNamaSiswa, edtNis, edtNamaSekolah, edtKelas, edtNoHp, edtEmailSiswa;
    Button btnSimpan;
    ImageView ivKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_siswa);

        ivKembali = findViewById(R.id.ivKembali);
        edtNamaSiswa = findViewById(R.id.edtNamaSiswa);
        edtNis = findViewById(R.id.edtNis);
        edtNamaSekolah = findViewById(R.id.edtNamaSekolah);
        edtKelas = findViewById(R.id.edtKelas);
        edtNoHp = findViewById(R.id.edtNoHp);
        edtEmailSiswa = findViewById(R.id.edtEmailSiswa);
        btnSimpan = findViewById(R.id.btnSimpan);

        ivKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}