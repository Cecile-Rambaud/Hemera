package com.example.hemera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.example.hemera.R;

public class DetailActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView distanceTextView;
    private ImageView imageView;
    private TextView descriptionTextView;
    private TextView objectTypeTextView;
    private Button deleteButton;
    private TextView orderOfMagnitudeTextView;
    private LinearLayout backgroundButtonColor;
    private View backgroundViewColor;

    private AlertDialog dialog;

    private void setViewBackground(String objectType) {
        if (backgroundViewColor != null) {
            switch (objectType) {
                case "Planète":
                    backgroundViewColor.setBackgroundResource(R.drawable.background_planet_detail);
                    break;
                case "Étoile":
                    backgroundViewColor.setBackgroundResource(R.drawable.background_star_detail);
                    break;
                case "Satellite":
                    backgroundViewColor.setBackgroundResource(R.drawable.background_satellite_detail);
                    break;
                case "Galaxie":
                    backgroundViewColor.setBackgroundResource(R.drawable.background_galaxy_detail);
                    break;
                case "Autre":
                    backgroundViewColor.setBackgroundResource(R.drawable.background_other_detail);
                    break;
                default:
                    backgroundViewColor.setBackgroundResource(R.drawable.background_default_detail);
                    break;
            }
        }
    }

    private void setBackgroundButtonColor(String objectType) {
        if (backgroundButtonColor != null) {
            switch (objectType) {
                case "Planète":
                    backgroundButtonColor.setBackgroundResource(R.drawable.background_planet);
                    break;
                case "Étoile":
                    backgroundButtonColor.setBackgroundResource(R.drawable.background_star);
                    break;
                case "Satellite":
                    backgroundButtonColor.setBackgroundResource(R.drawable.background_moon);
                    break;
                case "Galaxie":
                    backgroundButtonColor.setBackgroundResource(R.drawable.background_galaxy);
                    break;
                case "Autre":
                    backgroundButtonColor.setBackgroundResource(R.drawable.background_other);
                    break;
                default:
                    backgroundButtonColor.setBackgroundResource(R.drawable.background_default);
                    break;
            }
        }
    }

    private void setDeleteButtonTextStyle(String objectType) {
        if (deleteButton != null) {
            switch (objectType) {
                case "Planète":
                    deleteButton.setTextColor(Color.parseColor("#FF009688"));
                    break;
                case "Étoile":
                    deleteButton.setTextColor(Color.parseColor("#FFE91E63"));
                    break;
                case "Satellite":
                    deleteButton.setTextColor(Color.parseColor("#FF0B47FD"));
                    break;
                case "Galaxie":
                    deleteButton.setTextColor(Color.parseColor("#FFF44336"));
                    break;
                case "Autre":
                    deleteButton.setTextColor(Color.parseColor("#D1B00D"));
                    break;
                default:
                    deleteButton.setTextColor(Color.parseColor("#000000"));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        deleteButton = findViewById(R.id.delete_button);
        backgroundButtonColor = findViewById(R.id.background_button_color);
        backgroundViewColor = findViewById(R.id.background_view_color);
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this, R.style.CustomAlertDialog);

        View customDialogLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        builder.setView(customDialogLayout);

        AlertDialog dialog = builder.create();

        Button positiveButton = customDialogLayout.findViewById(R.id.dialog_positive_button);
        Button negativeButton = customDialogLayout.findViewById(R.id.dialog_negative_button);

        positiveButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("position_to_remove", getIntent().getIntExtra("position", -1));
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        negativeButton.setOnClickListener(view -> dialog.dismiss());

        deleteButton.setOnClickListener(v -> {
            dialog.show();
        });

        nameTextView = findViewById(R.id.name_edit_text);
        distanceTextView = findViewById(R.id.distance);
        orderOfMagnitudeTextView = findViewById(R.id.magnitude);
        imageView = findViewById(R.id.imageUri);
        objectTypeTextView = findViewById(R.id.object_type_title);
        descriptionTextView = findViewById(R.id.description);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("planet")) {
            Planet planet = (Planet) intent.getSerializableExtra("planet");
            if (planet != null) {
                if (planet.getName() != null) {
                    nameTextView.setText(planet.getName());
                }
                distanceTextView.setText(String.format("%.2f", planet.getDistance()));
                orderOfMagnitudeTextView.setText(planet.getOrderOfMagnitude());
                if (planet.getImageUri() != null) {
                    imageView.setImageURI(Uri.parse(planet.getImageUri()));
                }
                if (planet.getDescription() != null) {
                    descriptionTextView.setText(planet.getDescription());
                }
                if (planet.getObjectType() != null) {
                    objectTypeTextView.setText(planet.getObjectType());
                    setViewBackground(planet.getObjectType());
                    setBackgroundButtonColor(planet.getObjectType());
                    setDeleteButtonTextStyle(planet.getObjectType());
                }
            }
        }
    }
}
