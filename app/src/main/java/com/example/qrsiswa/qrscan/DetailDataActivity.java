package com.example.qrsiswa.qrscan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qrsiswa.R;
import com.example.qrsiswa.api.Api;

import java.util.Map;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DetailDataActivity extends AppCompatActivity {

    TextView tvNamaSiswa, tvNis, tvNamaSekolah;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        tvNamaSiswa = findViewById(R.id.tvNamaSiswa);
        tvNis = findViewById(R.id.tvNis);
        tvNamaSekolah = findViewById(R.id.tvNamaSekolah);

        String namaSiswa = getIntent().getStringExtra("nis");
        getData(namaSiswa);
    }

    public void getData(String nis){
        ProgressDialog dialog = new ProgressDialog(DetailDataActivity.this);
        dialog.setMessage("Mohon Tunggu...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Api.create().filterData(nis).subscribe(new Observer<Map<String, Object>>() {
            @Override
            public void onSubscribe(Disposable d) {

                dialog.dismiss();

            }

            @Override
            public void onNext(Map<String, Object> map) {
                dialog.dismiss();

                if (Objects.equals(map.get("statusCode"), 404.0)){
                    Log.d("pinter", "response data = " + map);

                    AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(DetailDataActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.popup_layout, null);

                    ImageView gambar = view.findViewById(R.id.imageView);
                    TextView isi = view.findViewById(R.id.isiDetail);
                    Button dfOk = view.findViewById(R.id.btnDismiss);
                    dialogbuilder.setView(view);

                    gambar.setImageResource(R.drawable.ic_not_found);
                    isi.setText(Objects.requireNonNull(map.get("message")).toString());

                    final AlertDialog alertDialog = dialogbuilder.create();
//                      alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogStyle;
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();

                    dfOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }else {

                    Map<String, Objects> mapDua = ((Map<String, Objects>) map.get("body"));

                    tvNamaSiswa.setText(String.valueOf(mapDua.get("nama_siswa")));
                    tvNis.setText(String.valueOf(mapDua.get("nis")));
                    tvNamaSekolah.setText(String.valueOf(mapDua.get("nama_sekolah")));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}