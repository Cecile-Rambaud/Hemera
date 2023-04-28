
# Hemera - Android Planetary Exploration App

Hemera is an Android app that allows users to explore and discover celestial objects such as planets, satellites, stars, and galaxies. Users can view a list of celestial objects, filter objects by type, view detailed information, and even add new objects to the list.

## Features
> List of celestial objects including planets, satellites, stars, and galaxies
1. Filter objects by type
2. View detailed information about each celestial object
3. Add new celestial objects to the list
4. Remove objects from the list
5. Reset filters to view the full list of celestial objects again

## Getting Started
To get started with the Hemera project, simply clone the repository and import it into your favorite Android IDE (such as Android Studio).

```
git clone https://github.com/your-username/hemera.git
Once the project is imported, you can run it on an Android emulator or a physical device.
```

## Code Structure
The code for Hemera is organized into the following packages and classes:

1. **MainActivity:** the main activity that displays the list of celestial objects, filter options, and the add button. It also handles the launching of other activities (AddPlanetActivity and DetailActivity) and communication between them.
2. **DetailActivity:** Displays detailed information about a specific celestial object and allows for the removal of the object from the list.
3. **AddPlanetActivit:** Allows users to input information about a new celestial object and add it to the list.
4. **PlanetAdapter:** A custom RecyclerView adapter to display the celestial object list and handle filtering and object interactions.
5. **Planet:** A model class representing a celestial object, containing information such as name, distance, type, description, and image URI.

