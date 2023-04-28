package com.example.hemera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPlanetActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nameEditText;
    private EditText distanceEditText;
    private Spinner objectTypeSpinner;
    private Spinner orderOfMagnitudeSpinner;
    private EditText descriptionEditText;
    private ImageView planetImageView;

    private Uri selectedImageUri;

    private boolean isValidFloat(String value) {
        String floatRegex = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
        return value.matches(floatRegex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_planet);

        nameEditText = findViewById(R.id.name_edit_text);
        distanceEditText = findViewById(R.id.distance_edit_text);
        objectTypeSpinner = findViewById(R.id.object_type_spinner);
        orderOfMagnitudeSpinner = findViewById(R.id.order_of_magnitude_spinner);
        descriptionEditText = findViewById(R.id.description_edit_text);
        planetImageView = findViewById(R.id.planet_image_view);

        List<String> objectTypeList = Arrays.asList(getResources().getStringArray(R.array.object_type_spinner));
        CustomSpinnerAdapter objectTypeAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_dropdown_item, objectTypeList);
        objectTypeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        objectTypeSpinner.setAdapter(objectTypeAdapter);

        List<String> magnitudeList = Arrays.asList(getResources().getStringArray(R.array.order_of_magnitude_options));
        CustomSpinnerAdapter magnitudeAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_dropdown_item, magnitudeList);
        magnitudeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        orderOfMagnitudeSpinner.setAdapter(magnitudeAdapter);

        objectTypeSpinner.setAdapter(objectTypeAdapter);
        objectTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedItem = (TextView) view;
                selectedItem.setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        orderOfMagnitudeSpinner.setAdapter(magnitudeAdapter);
        orderOfMagnitudeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedItem = (TextView) view;
                selectedItem.setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button selectImageButton = findViewById(R.id.select_image_button);
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Sélectionnez l'image"), PICK_IMAGE_REQUEST);
        });

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String distanceString = distanceEditText.getText().toString();
            String objectType = objectTypeSpinner.getSelectedItem().toString();
            String orderOfMagnitude = orderOfMagnitudeSpinner.getSelectedItem().toString();
            String description = descriptionEditText.getText().toString();

            if (name.isEmpty() || distanceString.isEmpty() || objectType.isEmpty() || description.isEmpty() || orderOfMagnitude.isEmpty()) {
                Toast.makeText(AddPlanetActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else if (!isValidFloat(distanceString)) {
                Toast.makeText(AddPlanetActivity.this, "Veuillez entrer une distance sous format : n,n", Toast.LENGTH_SHORT).show();
            } else {
                float distance = Float.parseFloat(distanceString);
                String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : null;
                Planet newPlanet = new Planet(name, distance, orderOfMagnitude, objectType, description, imageUriString);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("NEW_PLANET", newPlanet);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri originalImageUri = data.getData();
            selectedImageUri = saveImage(originalImageUri);
            planetImageView.setImageURI(selectedImageUri);
        }
    }
    public class CustomSpinnerAdapter extends ArrayAdapter<String> {

        private Context context;
        private List<String> items;

        public CustomSpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
            this.context = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return createView(position, convertView, parent, Color.WHITE);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return createView(position, convertView, parent, Color.BLACK);
        }

        private View createView(int position, View convertView, ViewGroup parent, int textColor) {
            View view = super.getView(position, convertView, parent);
            TextView textView = (TextView) view;

            if (position == 0) {
                textView.setTextColor(textColor);
            }

            return view;
        }
    }



    private Uri saveImage(Uri originalImageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(originalImageUri);
            if (inputStream == null) {
                return null;
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "PLANET_" + timeStamp + ".jpg";

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDir, imageFileName);

            OutputStream outputStream = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                outputStream = Files.newOutputStream(imageFile.toPath());
            }
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

            return Uri.fromFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
