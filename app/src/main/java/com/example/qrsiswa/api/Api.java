package com.example.qrsiswa.api;

import android.util.Log;

import com.example.qrsiswa.Base.RetroInstance;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;

public class Api extends RetroInstance {
    public static Api create(){
        return new Api();
    }

    private Endpoint getService(){
        return this.getInstance(Endpoint.class);
    }

    public Observable<Map<String, Object>> filterData(String body){

        return Observable.create(new ObservableOnSubscribe<Map<String, Object>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, Object>> emitter) throws Exception {

                getService().filterData(body).enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                        try {
                            Log.d("pinter", response.errorBody() != null ? response.errorBody().string() : "no error body");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(response.isSuccessful()){
                            emitter.onNext(response.body());
                        }else{
                            emitter.onError(new Exception(response.message()));
                        }

                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                        emitter.onError(t);
                    }
                });

            }
        });
    }
}
