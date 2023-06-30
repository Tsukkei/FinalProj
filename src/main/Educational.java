package com.example.finalproj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Educational extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "myPrefs";
    private SharedPreferences shareToReport;

    private CheckBox elementaryCheckBox, secondaryCheckBox, vocationalCheckBox, collegeCheckBox, graduateCheckBox;
    private EditText elementarySchoolEditText, elementaryEducationEditText, secondarySchoolEditText, secondaryEducationEditText,
            vocationalSchoolEditText, vocationalEducationEditText, collegeSchoolEditText, collegeEducationEditText,
            graduateSchoolEditText, graduateEducationEditText;
    private Button submitButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational);
        setTitle("Final Project");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pastel_purple)));

        elementaryCheckBox = findViewById(R.id.elementaryCheckBox);
        secondaryCheckBox = findViewById(R.id.secondaryCheckBox);
        vocationalCheckBox = findViewById(R.id.vocationalCheckBox);
        collegeCheckBox = findViewById(R.id.collegeCheckBox);
        graduateCheckBox = findViewById(R.id.graduateCheckBox);
        elementarySchoolEditText = findViewById(R.id.elementarySchoolEditText);
        elementaryEducationEditText = findViewById(R.id.elementaryEducationEditText);
        secondarySchoolEditText = findViewById(R.id.secondarySchoolEditText);
        secondaryEducationEditText = findViewById(R.id.secondaryEducationEditText);
        vocationalSchoolEditText = findViewById(R.id.vocationalSchoolEditText);
        vocationalEducationEditText = findViewById(R.id.vocationalEducationEditText);
        collegeSchoolEditText = findViewById(R.id.collegeSchoolEditText);
        collegeEducationEditText = findViewById(R.id.collegeEducationEditText);
        graduateSchoolEditText = findViewById(R.id.graduateSchoolEditText);
        graduateEducationEditText = findViewById(R.id.graduateEducationEditText);
        submitButton = findViewById(R.id.submitButton);

        updateFieldsVisibility();

        elementaryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFieldsVisibility();
            }
        });

        secondaryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFieldsVisibility();
            }
        });

        vocationalCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFieldsVisibility();
            }
        });

        collegeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFieldsVisibility();
            }
        });

        graduateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFieldsVisibility();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    Toast.makeText(Educational.this, "Form Submitted Proceeding to next page", Toast.LENGTH_SHORT).show();

                    shareToReport = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

                    String elemSchool = elementarySchoolEditText.getText().toString();
                    String elemEd = elementaryEducationEditText.getText().toString();
                    String secSchool = secondarySchoolEditText.getText().toString();
                    String secEd = secondaryEducationEditText.getText().toString();
                    String vocSchool = vocationalSchoolEditText.getText().toString();
                    String vocEd = vocationalEducationEditText.getText().toString();
                    String colSchool = collegeSchoolEditText.getText().toString();
                    String colEd = collegeEducationEditText.getText().toString();
                    String gradstudSchool = graduateSchoolEditText.getText().toString();
                    String gradstudEd = graduateEducationEditText.getText().toString();

                    SharedPreferences.Editor inputToReport = shareToReport.edit();
                    inputToReport.putString("ElemSchool", elemSchool);
                    inputToReport.putString("ElemEd", elemEd);
                    inputToReport.putString("SecSchool", secSchool);
                    inputToReport.putString("SecEd", secEd);
                    inputToReport.putString("VocSchool", vocSchool);
                    inputToReport.putString("VocEd", vocEd);
                    inputToReport.putString("ColSchool", colSchool);
                    inputToReport.putString("ColEd", colEd);
                    inputToReport.putString("GradStudSchool", gradstudSchool);
                    inputToReport.putString("GradStudEd", gradstudEd);
                    inputToReport.apply();

                    Intent intent = new Intent(Educational.this, CrimeBackground.class);
                    Intent getImage = getIntent();
                    byte[] storage  = getImage.getByteArrayExtra("StoredImage");
                    intent.putExtra("Storage",storage);
                    startActivity(intent);
                } else {
                    Toast.makeText(Educational.this, "Please fill in all necessary information.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void updateFieldsVisibility() {
        boolean elementaryChecked = elementaryCheckBox.isChecked();
        boolean secondaryChecked = secondaryCheckBox.isChecked();
        boolean vocationalChecked = vocationalCheckBox.isChecked();
        boolean collegeChecked = collegeCheckBox.isChecked();
        boolean graduateChecked = graduateCheckBox.isChecked();

        elementarySchoolEditText.setEnabled(elementaryChecked);
        elementaryEducationEditText.setEnabled(elementaryChecked);

        secondarySchoolEditText.setEnabled(secondaryChecked);
        secondaryEducationEditText.setEnabled(secondaryChecked);

        vocationalSchoolEditText.setEnabled(vocationalChecked);
        vocationalEducationEditText.setEnabled(vocationalChecked);

        collegeSchoolEditText.setEnabled(collegeChecked);
        collegeEducationEditText.setEnabled(collegeChecked);

        graduateSchoolEditText.setEnabled(graduateChecked);
        graduateEducationEditText.setEnabled(graduateChecked);
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (elementaryCheckBox.isChecked()) {
            if (elementarySchoolEditText.getText().toString().isEmpty() ||
                    elementaryEducationEditText.getText().toString().isEmpty()) {
                isValid = false;
            }
        }

        if (secondaryCheckBox.isChecked()) {
            if (secondarySchoolEditText.getText().toString().isEmpty() ||
                    secondaryEducationEditText.getText().toString().isEmpty()) {
                isValid = false;
            }
        }

        if (vocationalCheckBox.isChecked()) {
            if (vocationalSchoolEditText.getText().toString().isEmpty() ||
                    vocationalEducationEditText.getText().toString().isEmpty()) {
                isValid = false;
            }
        }

        if (collegeCheckBox.isChecked()) {
            if (collegeSchoolEditText.getText().toString().isEmpty() ||
                    collegeEducationEditText.getText().toString().isEmpty()) {
                isValid = false;
            }
        }

        if (graduateCheckBox.isChecked()) {
            if (graduateSchoolEditText.getText().toString().isEmpty() ||
                    graduateEducationEditText.getText().toString().isEmpty()) {
                isValid = false;
            }
        }

        return isValid;
    }
}
