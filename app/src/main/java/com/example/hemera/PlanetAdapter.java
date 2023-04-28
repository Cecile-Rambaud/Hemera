package com.example.hemera;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder> {

    private List<Planet> planetList;
    private List<Planet> unfilteredPlanetList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Planet planet, int position);
        void onObjectTypeClick(String objectType);
    }

    public PlanetAdapter(List<Planet> planetList) {
        this.planetList = planetList;
        this.unfilteredPlanetList = new ArrayList<>(planetList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public boolean isFilterApplied() {
        return !planetList.equals(unfilteredPlanetList);
    }

    public void filterByObjectType(String objectType) {
        planetList = new ArrayList<>();
        if (objectType == null || objectType.isEmpty()) {
            planetList.addAll(unfilteredPlanetList);
        } else {
            for (Planet planet : unfilteredPlanetList) {
                if (planet.getObjectType().equalsIgnoreCase(objectType)) {
                    planetList.add(planet);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.planet_item, parent, false);
        return new PlanetViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        Planet planet = planetList.get(position);
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(planet.getImageUri())
                .into(holder.image);
        holder.name.setText(planet.getName());
        holder.object.setText(planet.getObjectType());
        holder.object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objectType = holder.object.getText().toString();
                if (mListener != null) {
                    mListener.onObjectTypeClick(objectType);
                }
            }
        });
        holder.distance.setText(String.valueOf(planet.getDistance()));
        holder.magnitude.setText(planet.getOrderOfMagnitude());
        holder.description.setText(getTruncatedDescription(planet.getDescription()));
        setObjectTypeStyle(holder.object, planet.getObjectType());
    }

    private String getTruncatedDescription(String description) {
        int wordLimit = 4;
        String[] words = description.split(" ");
        if (words.length > wordLimit) {
            StringBuilder truncatedDescription = new StringBuilder();
            for (int i = 0; i < wordLimit; i++) {
                truncatedDescription.append(words[i]).append(" ");
            }
            truncatedDescription.append("...");
            return truncatedDescription.toString();
        } else {
            return description;
        }
    }

    private void setObjectTypeStyle(TextView textView, String objectType) {
        switch (objectType) {
            case "Planète":
                textView.setBackgroundResource(R.drawable.background_planet);
                textView.setTextColor(Color.parseColor("#FF009688"));
                textView.setTypeface(null);
                break;
            case "Étoile":
                textView.setBackgroundResource(R.drawable.background_star);
                textView.setTextColor(Color.parseColor("#FFE91E63"));
                textView.setTypeface(null);
                break;
            case "Satellite":
                textView.setBackgroundResource(R.drawable.background_moon);
                textView.setTextColor(Color.parseColor("#FF0B47FD"));
                textView.setTypeface(null);
                break;
            case "Galaxie":
                textView.setBackgroundResource(R.drawable.background_galaxy);
                textView.setTextColor(Color.parseColor("#FFF44336"));
                textView.setTypeface(null);
                break;
            case "Autre":
                textView.setBackgroundResource(R.drawable.background_other);
                textView.setTextColor(Color.parseColor("#D1B00D"));
                textView.setTypeface(null);
                break;
            default:
                textView.setBackgroundResource(R.drawable.background_default);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public class PlanetViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name, object, distance, magnitude, description;

        public PlanetViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            object = itemView.findViewById(R.id.object_type);
            distance = itemView.findViewById(R.id.distance);
            magnitude = itemView.findViewById(R.id.magnitude);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(planetList.get(position), position);
                    }
                }
            });
        }
    }

    public void addPlanet(Planet newPlanet) {
        planetList.add(newPlanet);
        notifyDataSetChanged();
    }

    public void resetFilter() {
        planetList.clear();
        planetList.addAll(unfilteredPlanetList);
        notifyDataSetChanged();
    }
}
