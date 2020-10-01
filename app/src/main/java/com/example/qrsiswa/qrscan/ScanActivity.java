package com.example.qrsiswa.qrscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.qrsiswa.R;
import com.example.qrsiswa.api.Api;
import com.google.zxing.Result;

import java.util.Map;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    ImageView btnClose, ivQrCode;
    ToggleButton flash;
    ZXingScannerView mScannerView;
    private boolean flashLightStatus;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mScannerView = findViewById(R.id.zxing_barcode_scanner);
        flashLightStatus = false;

        btnClose = findViewById(R.id.btnClose);
        flash = findViewById(R.id.flash);
//        ivQrCode = findViewById(R.id.qr_code_saya);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle.getString("before").equals("transfer")){
//            ivQrCode.setVisibility(View.VISIBLE);
//        }
//
//        else if (bundle.getString("before").equals("bayar")) {
//            ivQrCode.setVisibility(View.GONE);
//        }

        final boolean hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        flash.setEnabled(isEnabled);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCameraFlash) {
                    if(flashLightStatus){
                        flashLightStatus = false;
                        mScannerView.setFlash(false);
                    }
                    else{
                        flashLightStatus = true;
                        mScannerView.setFlash(true);
                    }
                } else {
                    Toast.makeText(ScanActivity.this, "No Flash available on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClose.setOnClickListener(v -> finish());
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        Log.d("pinter", result.getText());

        scanQr(result.getText());
        mScannerView.resumeCameraPreview(this);
    }

    public void scanQr(String nis){
        ProgressDialog dialog = new ProgressDialog(ScanActivity.this);
        dialog.setMessage("Sedang memuat...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Api.create().filterData(nis).subscribe(new Observer<Map<String, Object>>() {
            @Override
            public void onSubscribe(Disposable d) {

                dialog.dismiss();

            }

            @Override
            public void onNext(Map<String, Object> map) {
                dialog.dismiss();
                if (Objects.equals(map.get("statucCode"), 404)){
                    Log.d("pinter", "response data = " + map);

                    AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(ScanActivity.this);
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
                }else{
                    Log.d("pinter", "response data = " + map);

                    Map<String, Objects> mapDua = ((Map<String, Objects>) map.get("body"));

                    Intent intent = new Intent(ScanActivity.this, DetailDataActivity.class);
                    intent.putExtra("nis", String.valueOf(mapDua.get("nis")));
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
                Log.d("pinter", "response error = " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }
}