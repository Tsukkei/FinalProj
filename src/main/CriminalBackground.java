package com.example.finalproj;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CrimeBackground extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "myPrefs";

    SharedPreferences shareToReport;
    private EditText editTextDetails1, editTextDetails2, editTextDetails3, editTextDetailsA, editTextDetailsB, editTextDetailsC;
    private CheckBox checkBoxYes1, checkBoxYes2, checkBoxYes3, checkBoxYesA, checkBoxYesB, checkBoxYesC;
    private CheckBox checkBoxNo1, checkBoxNo2, checkBoxNo3, checkBoxNoA, checkBoxNoB, checkBoxNoC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_background);
        setTitle("Final Project");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pastel_purple)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Info", "Stuff", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        editTextDetails1 = findViewById(R.id.editTextDetails1);
        editTextDetails2 = findViewById(R.id.editTextDetails2);
        editTextDetails3 = findViewById(R.id.editTextDetails3);
        editTextDetailsA = findViewById(R.id.editTextDetailsA);
        editTextDetailsB = findViewById(R.id.editTextDetailsB);
        editTextDetailsC = findViewById(R.id.editTextDetailsC);

        checkBoxYes1 = findViewById(R.id.checkBoxYes1);
        checkBoxYes2 = findViewById(R.id.checkBoxYes2);
        checkBoxYes3 = findViewById(R.id.checkBoxYes3);
        checkBoxYesA = findViewById(R.id.checkBoxYesA);
        checkBoxYesB = findViewById(R.id.checkBoxYesB);
        checkBoxYesC = findViewById(R.id.checkBoxYesC);

        checkBoxNo1 = findViewById(R.id.checkBoxNo1);
        checkBoxNo2 = findViewById(R.id.checkBoxNo2);
        checkBoxNo3 = findViewById(R.id.checkBoxNo3);
        checkBoxNoA = findViewById(R.id.checkBoxNoA);
        checkBoxNoB = findViewById(R.id.checkBoxNoB);
        checkBoxNoC = findViewById(R.id.checkBoxNoC);

        setFieldsEnabled(false);

        setCheckBoxListener(checkBoxYes1, editTextDetails1, checkBoxNo1);
        setCheckBoxListener(checkBoxYes2, editTextDetails2, checkBoxNo2);
        setCheckBoxListener(checkBoxYes3, editTextDetails3, checkBoxNo3);
        setCheckBoxListener(checkBoxYesA, editTextDetailsA, checkBoxNoA);
        setCheckBoxListener(checkBoxYesB, editTextDetailsB, checkBoxNoB);
        setCheckBoxListener(checkBoxYesC, editTextDetailsC, checkBoxNoC);
    }

    private void setCheckBoxListener(CheckBox checkBoxYes, EditText editTextDetails, CheckBox checkBoxNo) {
        checkBoxYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editTextDetails.setEnabled(isChecked);
            checkBoxNo.setChecked(!isChecked);
            if (!isChecked) {
                editTextDetails.setText("");
            } else {
                String answer = getAnswerForCheckBox(checkBoxYes);
                editTextDetails.setText(answer);
            }
        });
    }

    public void onSubmit(View view) {
        if (!validateFields()) {

            Toast.makeText(this, "Please fill in all necessary information.", Toast.LENGTH_SHORT).show();
            return;
        }
        shareToReport = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String questionOne = editTextDetails1.getText().toString();
        String questionTwo = editTextDetails2.getText().toString();
        String questionThree = editTextDetails3.getText().toString();
        String questionA = editTextDetailsA.getText().toString();
        String questionB = editTextDetailsB.getText().toString();
        String questionC = editTextDetailsC.getText().toString();

        SharedPreferences.Editor inputToReport = shareToReport.edit();

        inputToReport.putString("QuestionOne", questionOne);
        inputToReport.putString("QuestionTwo", questionTwo);
        inputToReport.putString("QuestionThree", questionThree);
        inputToReport.putString("QuestionA", questionA);
        inputToReport.putString("QuestionB", questionB);
        inputToReport.putString("QuestionC", questionC);
        inputToReport.apply();

        Intent intent = new Intent(this, Report.class);
        Intent thisIntent = getIntent();
        byte[] Storage  = thisIntent.getByteArrayExtra("Storage");
        intent.putExtra("Storage",Storage);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "The Info");
        builder.setContentTitle("Report");
        builder.setContentText("Hey, your Information has arrived");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        managerCompat.notify(1, builder.build());

        Toast.makeText(this, "Details submitted.", Toast.LENGTH_SHORT).show();
    }

    private boolean validateFields() {
        String details1 = editTextDetails1.getText().toString().trim();
        String details2 = editTextDetails2.getText().toString().trim();
        String details3 = editTextDetails3.getText().toString().trim();
        String detailsA = editTextDetailsA.getText().toString().trim();
        String detailsB = editTextDetailsB.getText().toString().trim();
        String detailsC = editTextDetailsC.getText().toString().trim();

        if (checkBoxYes1.isChecked() && details1.isEmpty()) {
            return false;
        }

        if (checkBoxYes2.isChecked() && details2.isEmpty()) {
            return false;
        }

        if (checkBoxYes3.isChecked() && details3.isEmpty()) {
            return false;
        }

        if (checkBoxYesA.isChecked() && detailsA.isEmpty()) {
            return false;
        }

        if (checkBoxYesB.isChecked() && detailsB.isEmpty()) {
            return false;
        }

        if (checkBoxYesC.isChecked() && detailsC.isEmpty()) {
            return false;
        }

        return true;
    }

    private void setFieldsEnabled(boolean enabled) {
        editTextDetails1.setEnabled(enabled);
        editTextDetails2.setEnabled(enabled);
        editTextDetails3.setEnabled(enabled);
        editTextDetailsA.setEnabled(enabled);
        editTextDetailsB.setEnabled(enabled);
        editTextDetailsC.setEnabled(enabled);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        int viewId = view.getId();

        if (viewId == R.id.checkBoxYes1) {
            setCheckBoxState(checked, editTextDetails1, checkBoxNo1);
        } else if (viewId == R.id.checkBoxYes2) {
            setCheckBoxState(checked, editTextDetails2, checkBoxNo2);
        } else if (viewId == R.id.checkBoxYes3) {
            setCheckBoxState(checked, editTextDetails3, checkBoxNo3);
        } else if (viewId == R.id.checkBoxYesA) {
            setCheckBoxState(checked, editTextDetailsA, checkBoxNoA);
        } else if (viewId == R.id.checkBoxYesB) {
            setCheckBoxState(checked, editTextDetailsB, checkBoxNoB);
        } else if (viewId == R.id.checkBoxYesC) {
            setCheckBoxState(checked, editTextDetailsC, checkBoxNoC);
        }
    }
    private String getAnswerForCheckBox(CheckBox checkBox) {
        int viewId = checkBox.getId();
        String answer = "";

        if (viewId == R.id.checkBoxYes1) {
            answer = "Answer for CheckBox 1";
        } else if (viewId == R.id.checkBoxYes2) {
            answer = "Answer for CheckBox 2";
        } else if (viewId == R.id.checkBoxYes3) {
            answer = "Answer for CheckBox 3";
        } else if (viewId == R.id.checkBoxYesA) {
            answer = "Answer for CheckBox A";
        } else if (viewId == R.id.checkBoxYesB) {
            answer = "Answer for CheckBox B";
        } else if (viewId == R.id.checkBoxYesC) {
            answer = "Answer for CheckBox C";
        }

        return answer;
    }
    private void setCheckBoxState(boolean checked, EditText editTextDetails, CheckBox checkBoxNo) {
        editTextDetails.setEnabled(checked);
        checkBoxNo.setChecked(!checked);
        if (!checked) {
            editTextDetails.setText("");
        }
    }
}
