package com.example.telemedsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

//import ru.medlinesoft.telemed.TelemedApplicationManager;
//import ru.medlinesoft.telemed.presentation.SplashActivity;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import ru.medlinesoft.telemed.client.RequestListener;
import ru.medlinesoft.telemed.client.TelemedClientManager;
import ru.medlinesoft.telemed.client.VideoChatActivity;
import ru.medlinesoft.telemed.client.rest.model.AgeGroup;
import ru.medlinesoft.telemed.client.rest.model.PageView;
import ru.medlinesoft.telemed.client.rest.model.PagingParam;
import ru.medlinesoft.telemed.client.rest.model.Sex;
import ru.medlinesoft.telemed.client.rest.model.appointment.AppointmentView;
import ru.medlinesoft.telemed.client.rest.model.auth.AuthResult;
import ru.medlinesoft.telemed.client.rest.model.doc.DoctorShortPublicView;
import ru.medlinesoft.telemed.client.rest.model.doc.SpecialtyPublicView;
import ru.medlinesoft.telemed.client.rest.model.person.PatientRegistrationResultView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for app and doctor
        //start screens: SplashActivity, DoctorsActivity
        //test port 8443, prod port 8447
        //TelemedApplicationManager.init("telemed.medlinesoft.ru", "8447", getApplicationContext());

        //for api
        TelemedClientManager.init("telemed.medlinesoft.ru", "8443", getApplicationContext());
    }

    public void startOnlineConsultation(View view) {
        //startActivity(new Intent(this, SplashActivity.class));
        //startActivity(new Intent(this, DoctorsActivity.class));

        RequestListener appointmentListener = new RequestListener<AppointmentView>() {
            @Override
            public void onResponse(AppointmentView appointmentView) {
                Log.d("TAG", "id: " + appointmentView.id);

                Intent intent = new Intent(MainActivity.this, VideoChatActivity.class);
                intent.putExtra("APPOINTMENT_ID", appointmentView.id);
                startActivity(intent);
            }

            @Override
            public void onFailure(String s, Throwable throwable) {
                Log.d("TAG", "onFailure");
            }
        };

        RequestListener authListener = new RequestListener<AuthResult>() {
            @Override
            public void onResponse(AuthResult authResult) {
                Log.d("TAG", "onResponse");
                Log.d("TAG", "success: " + authResult.success);

                //create consultation
                TelemedClientManager.appointmentQueue(AgeGroup.ADULT, appointmentListener);
            }

            @Override
            public void onFailure(String s, Throwable throwable) {
                Log.d("TAG", "onFailure");
            }
        };

        TelemedClientManager.login("zzz@zz.zz", "1234", getApplicationContext(), authListener);
    }

    public void doctorsList(View view) {
        String tag = "REGISTER";
        /*String tag = "DOCTORSLIST";
        RequestListener doctorListListener = new RequestListener<PageView<DoctorShortPublicView>>() {
            @Override
            public void onResponse(PageView<DoctorShortPublicView> doctorShortPublicViewPageView) {
                Log.d(tag, "onResponse");
                List<Pair<String, String>> doctors = new ArrayList<>();

                for (DoctorShortPublicView d : doctorShortPublicViewPageView.content) {
                    doctors.add(new Pair<String, String>(d.id, d.person.firstName + " " + d.person.lastName));
                }

                Log.d(tag, "content: " + doctors);
                Log.d(tag, "total: " + String.valueOf(doctorShortPublicViewPageView.total));
                Log.d(tag, "pageSize: " + doctorShortPublicViewPageView.paging.pageSize);
                Log.d(tag, "pageNumber: " + doctorShortPublicViewPageView.paging.pageNumber);
            }

            @Override
            public void onFailure(String s, Throwable throwable) {
                Log.d(tag, "onFailure");
            }
        };

        RequestListener specialitiesListener = new RequestListener<List<SpecialtyPublicView>>() {
            @Override
            public void onResponse(List<SpecialtyPublicView> specialtyPublicViews) {
                Log.d(tag, "onResponse");
                List<Pair<Long, String>> specialities = new ArrayList<Pair<Long, String>>();
                for (SpecialtyPublicView s :  specialtyPublicViews) {
                    specialities.add(new Pair<>(s.id, s.name));
                }
                Log.d(tag, "specialities: " + specialities);
            }

            @Override
            public void onFailure(String s, Throwable throwable) {
                Log.d(tag, "onFailure");
            }
        };

        RequestListener authListener = new RequestListener<AuthResult>() {
            @Override
            public void onResponse(AuthResult authResult) {
                Log.d(tag, "onResponse");
                Log.d(tag, "success: " + authResult.success);

                //get doctors list
                PagingParam pp = new PagingParam(0, 10);
                //413 Невролог
                TelemedClientManager.getDoctorList(null, null, pp, doctorListListener);

                //get all specialities
                //TelemedClientManager.specialties(specialitiesListener);
            }

            @Override
            public void onFailure(String s, Throwable throwable) {

            }
        };

        TelemedClientManager.login("rtokarenko@docdoc.ru", "123", getApplicationContext(), authListener);*/

        /*RequestListener appointmentListener = new RequestListener<AppointmentView>() {
            @Override
            public void onResponse(AppointmentView appointmentView) {
                Log.d("TAG", "id: " + appointmentView.id);

                Intent intent = new Intent(MainActivity.this, VideoChatActivity.class);
                intent.putExtra("APPOINTMENT_ID", appointmentView.id);
                startActivity(intent);
            }

            @Override
            public void onFailure(String s, Throwable throwable) {
                Log.d("TAG", "onFailure");
            }
        };

        RequestListener<PatientRegistrationResultView> listener = new RequestListener<PatientRegistrationResultView>() {
            @Override
            public void onResponse(PatientRegistrationResultView patientRegistrationResultView) {
                Log.d("TAG", "onResponse");
                Log.d("TAG", "patientId: " + patientRegistrationResultView.patientId);
                Log.d("TAG", "state: " + patientRegistrationResultView.state);

                //create consultation
                TelemedClientManager.appointmentQueue(AgeGroup.ADULT, appointmentListener);
            }

            @Override
            public void onFailure(String s, Throwable throwable) {
                Log.d("TAG", "onFailure");
            }
        };

        //71111111111
        //1111
        TelemedClientManager.register(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                getApplicationContext(),
                listener
        );*/
    }

}
