package com.example.finalproj;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;

public class PersonalBackground extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;

    private static final String SHARED_PREF_NAME = "myPrefs";

    private SharedPreferences shareToReport;

    private ImageView photoImageView;
    private EditText nameLastName;
    private EditText nameFirstName;
    private EditText phoneEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private Spinner relationshipSpinner;
    private EditText pagibigEditText;
    private EditText philhealthEditText;
    private EditText tinEditText;
    private EditText gsisEditText;
    private EditText contactEditText;
    private EditText emailEditText;
    private EditText emergencyContactNameEditText;

    private String gender;
    private Bitmap image;
    private byte[] storedimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Final Project");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pastel_purple)));

        photoImageView = findViewById(R.id.photoImageView);
        nameLastName = findViewById(R.id.lastnameEditText);
        nameFirstName = findViewById(R.id.firstnameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        relationshipSpinner = findViewById(R.id.relationshipSpinner);
        pagibigEditText = findViewById(R.id.pagibigEditText);
        philhealthEditText = findViewById(R.id.philhealthEditText);
        tinEditText = findViewById(R.id.tinEditText);
        gsisEditText = findViewById(R.id.gsisEditText);
        contactEditText = findViewById(R.id.emergencyContactNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        emergencyContactNameEditText = findViewById(R.id.emergencyContactNameEditText);

        shareToReport = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        Button captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    openCamera();
                } else {
                    requestCameraPermission();
                }
            }
        });

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastname = nameLastName.getText().toString().trim();
                String firstname = nameFirstName.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String height = heightEditText.getText().toString().trim();
                String weight = weightEditText.getText().toString().trim();
                String contact = contactEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String relationship = relationshipSpinner.getSelectedItem().toString();
                String pagibig = pagibigEditText.getText().toString().trim();
                String philhealth = philhealthEditText.getText().toString().trim();
                String tin = tinEditText.getText().toString().trim();
                String gsis = gsisEditText.getText().toString().trim();
                String contactname = emergencyContactNameEditText.getText().toString().trim();

                SharedPreferences.Editor inputToReport = shareToReport.edit();
                inputToReport.clear();
                inputToReport.putString("LastName", lastname);
                inputToReport.putString("FirstName", firstname);
                inputToReport.putString("Phone", phone);
                inputToReport.putString("Height", height);
                inputToReport.putString("Weight", weight);
                inputToReport.putString("Contact", contact);
                inputToReport.putString("Email", email);
                inputToReport.putString("Gender", gender);
                inputToReport.putString("Relationship", relationship);
                inputToReport.putString("Pagibig", pagibig);
                inputToReport.putString("PhilHealth", philhealth);
                inputToReport.putString("Tin", tin);
                inputToReport.putString("Gsis", gsis);
                inputToReport.putString("ContactName", contactname);

                if (validateForm()) {
                    RadioGroup civilStatusRadioGroup = findViewById(R.id.civilStatusRadioGroup);
                    int selectedRadioButtonId = civilStatusRadioGroup.getCheckedRadioButtonId();
                    String civilStatus = "";
                    if (selectedRadioButtonId != -1) {
                        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                        civilStatus = selectedRadioButton.getText().toString();
                        inputToReport.putString("CivilStatus", civilStatus);
                    }
                    inputToReport.apply();
                    ByteArrayOutputStream storedPhotoBytes = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG,100,storedPhotoBytes);
                    storedimage = storedPhotoBytes.toByteArray();
                    submitForm();
                } else {
                    Toast.makeText(PersonalBackground.this, "Please fill in all necessary information.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                gender = selectedRadioButton.getText().toString();
            }
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            photoImageView.setImageBitmap(image);

        }
    }

    private boolean validateForm() {
        String lastname = nameLastName.getText().toString().trim();
        String firstname = nameFirstName.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String height = heightEditText.getText().toString().trim();
        String weight = weightEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String pagibig = pagibigEditText.getText().toString().trim();
        String philhealth = philhealthEditText.getText().toString().trim();
        String tin = tinEditText.getText().toString().trim();
        String gsis = gsisEditText.getText().toString().trim();
        String contactname = emergencyContactNameEditText.getText().toString().trim();

        return !lastname.isEmpty() && !firstname.isEmpty() && !phone.isEmpty() && !height.isEmpty() &&
                !weight.isEmpty() && !contact.isEmpty() && !email.isEmpty() && !pagibig.isEmpty() &&
                !philhealth.isEmpty() && !tin.isEmpty() && !gsis.isEmpty() && !contactname.isEmpty();
    }

    private void submitForm() {

        Toast.makeText(PersonalBackground.this, "Form submitted proceeding to next page.", Toast.LENGTH_SHORT).show();
        SharedPreferences shareToReport = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Log.d("Gender", shareToReport.getString("Gender", ""));
        Intent intent = new Intent(PersonalBackground.this, Educational.class);
        intent.putExtra("StoredImage",storedimage);
        startActivity(intent);
    }
}
