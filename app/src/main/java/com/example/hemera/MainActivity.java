package com.example.hemera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton addPlanetButton;
    private ImageButton resetFilterButton;
    private PlanetAdapter planetAdapter;
    private ArrayList<Planet> planetList;

    private ActivityResultLauncher<Intent> detailActivityResultLauncher;
    private ActivityResultLauncher<Intent> addPlanetActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetFilterButton = findViewById(R.id.reset_filter_button);
        resetFilterButton.setOnClickListener(v -> {
            planetAdapter.resetFilter();
            updateResetFilterButtonVisibility();
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        planetList = new ArrayList<>(Arrays.asList(
                new Planet("Titan", 1221.83f, "M km", "Satellite",
                        "Titan, la plus grande lune de Saturne et la deuxième plus grande lune du système solaire, est connue pour son atmosphère" +
                                " dense et riche en azote. Les conditions météorologiques sur Titan sont similaires à celles de la Terre, avec des pluies," +
                                " des rivières et des lacs de méthane et d'éthane liquides. Les scientifiques pensent que Titan pourrait abriter des formes de " +
                                "vie basées sur ces hydrocarbures, en raison de la présence de composés organiques complexes et d'énergie chimique disponible. " +
                                "La surface de Titan est également parsemée de dunes de sable et de montagnes, offrant un paysage diversifié pour l'étude et l'exploration.", "android.resource://"
                        + getPackageName() + "/drawable/titan"),
        new Planet("Sirius", 8.6f, "AL","Étoile",
                        "Sirius, également connue sous le nom d'Alpha Canis Majoris, est l'étoile la plus brillante dans le ciel nocturne et se trouve " +
                                "à environ 8,6 années-lumière de la Terre. C'est une étoile binaire composée d'une étoile de la séquence principale de type spectral" +
                                " A (Sirius A) et d'une naine blanche (Sirius B). Sirius A est environ deux fois plus massive que le Soleil et 25 fois plus lumineuse, tandis " +
                                "que Sirius B est une naine blanche dense et chaude, ayant épuisé son combustible nucléaire. Les deux étoiles orbitent l'une autour de" +
                                " l'autre sur une période d'environ 50 ans.", "android.resource://"
                + getPackageName() + "/drawable/sirius"),
                new Planet("Europe", 628.7f, "M km","Satellite",
                        "Europe est l'une des lunes galiléennes de Jupiter, découverte par Galilée en 1610. Elle est légèrement plus petite que la Lune terrestre" +
                                " et est principalement constituée de silicates et de glace d'eau. La surface glacée d'Europe dissimule un océan souterrain d'eau salée, qui pourrait" +
                                " s'étendre sur une profondeur de 100 km. Cet océan est maintenu à l'état liquide grâce au réchauffement provoqué par les forces de" +
                                " marée générées par l'interaction gravitationnelle avec Jupiter. Les scientifiques pensent que cet océan souterrain pourrait potentiellement " +
                                "abriter des formes de vie extraterrestre, car il offre un environnement stable et une source d'énergie chimique sous la forme de réactions hydrothermales." +
                                " Les futures missions d'exploration d'Europe visent " +
                                "à étudier cet océan et à rechercher des preuves de vie.", "android.resource://"
                        + getPackageName() + "/drawable/europe"),
                new Planet("Saturne", 0f, "Km", "Planète",
                        "La Terre est la troisième planète du système solaire et se distingue comme la seule planète connue pour abriter la vie. Avec une atmosphère riche en oxygène" +
                                " et un climat tempéré, la Terre est caractérisée par des conditions idéales pour soutenir la vie, en grande partie grâce à sa distance optimale du Soleil. " +
                                "La composition de la Terre comprend une croûte solide, un manteau visqueux et un noyau métallique. La surface terrestre est recouverte d'environ 71% d'eau, " +
                                "principalement sous forme d'océans, et abrite une biodiversité impressionnante d'espèces animales et végétales.", "android.resource://" + getPackageName() + "/drawable/saturne"),
                new Planet("Voie lactée", 0f, "AL", "Galaxie",
                        "La Voie lactée est notre galaxie, une spirale barrée contenant environ 100 à 400 milliards d'étoiles, dont notre Soleil. Elle s'étend sur environ 100 000 années-lumière et abrite une grande variété d'objets célestes, tels que des étoiles, des planètes, des nébuleuses et des amas d'étoiles. Le centre de la Voie lactée contient un trou noir supermassif appelé Sagittaire A*.",
                        "android.resource://" + getPackageName() + "/drawable/voie_lactee"),
                new Planet("Mars", 78.3f, "M km","Planète",
                        "Mars, surnommée la planète rouge en raison de sa couleur caractéristique due à l'oxyde de fer, est la quatrième planète du système solaire. L'atmosphère de" +
                                " Mars est principalement composée de dioxyde de carbone, avec des traces d'azote et d'argon. La surface de Mars présente des signes de formations géologiques " +
                                "passées, telles que des volcans, des canyons et des lits de rivières " +
                                "asséchées. Les chercheurs s'intéressent particulièrement à Mars en raison de la possibilité qu'elle ait pu abriter la vie à un moment de son histoire et de son " +
                                "potentiel pour la colonisation humaine future.", "android.resource://"
                        + getPackageName() + "/drawable/mars"),
                new Planet("Nébuleuse d'Orion", 1350, "AL", "Autre",
                        "La Nébuleuse d'Orion, ou M42, est une région de formation d'étoiles située à environ 1 350 années-lumière de la Terre dans la constellation d'Orion. Elle est visible à l'œil nu dans le ciel nocturne et est l'une des nébuleuses les plus étudiées et les mieux connues. La Nébuleuse d'Orion est principalement constituée de gaz, principalement de l'hydrogène, et de poussières interstellaires. Les jeunes étoiles massives situées au cœur de la nébuleuse émettent une intense lumière ultraviolette qui ionise le gaz environnant, lui donnant sa brillance caractéristique et sa couleur rougeâtre.",
                        "android.resource://"
                                + getPackageName() + "/drawable/orion_nebula"),
                new Planet("Andromède", 2.537f, "M AL", "Galaxie",
                        "La galaxie d'Andromède, ou M31, est une galaxie spirale située à environ 2,537 millions d'années-lumière de la Terre. C'est la galaxie la plus proche de la Voie lactée et est en train de s'en rapprocher. On estime qu'elle entrera en collision avec notre galaxie dans environ 4,5 milliards d'années, fusionnant pour former une nouvelle galaxie.",
                        "android.resource://"
                                + getPackageName() + "/drawable/andromede"),
                new Planet("Jupiter", 628.7f, "M km", "Planète",
                        "En tant que plus grande planète du système solaire, Jupiter est principalement composée d'hydrogène et d'hélium. Jupiter est célèbre pour sa Grande Tache Rouge," +
                                " une tempête anticyclonique massive, et pour son puissant champ magnétique. La planète est également connue" +
                                " pour son grand nombre de lunes, dont quatre, appelées lunes galiléennes, ont été découvertes par Galilée en 1610. Les conditions extrêmes à la surface" +
                                " de Jupiter, y compris des tempêtes gigantesques et des pressions intenses, rendent la planète inhabitable pour les humains.", "android.resource://"
                        + getPackageName() + "/drawable/jupiter"),
                new Planet("Proxima Centauri", 4.24f, "AL","Étoile",
                        "Proxima Centauri est une naine rouge située à environ 4,24 années-lumière de la Terre, ce qui en fait l'étoile la plus proche de notre système solaire. " +
                                "Proxima Centauri possède une faible luminosité et une faible masse, avec une température de surface plus froide que celle du Soleil. La découverte d'au " +
                                "moins deux " +
                                "exoplanètes orbitant autour de Proxima Centauri, dont Proxima Centauri b, qui est située dans la zone habitable de l'étoile, a suscité un grand intérêt " +
                                "dans la recherche de vie extraterrestre et la possibilité d'exploration interstellaire future.", "android.resource://"
                        + getPackageName() + "/drawable/proxima"),
                new Planet("Vénus", 41.4f, "M km","Planète",
                        "Vénus, la deuxième planète du système solaire, est souvent appelée la sœur jumelle de la Terre en raison de sa taille et de sa composition similaires. " +
                                "Cependant, les conditions sur Vénus sont radicalement différentes de celles de notre planète. Son atmosphère épaisse, composée principalement de dioxyde" +
                                " de carbone" +
                                " et de nuages d'acide sulfurique, crée un effet de serre intense qui fait de Vénus la planète la plus chaude du système solaire, avec des températures " +
                                "de surface pouvant atteindre 470°C. La pression atmosphérique à la surface de Vénus est également 90 fois supérieure à celle de la Terre, ce qui rend la " +
                                "planète inhospitalière pour les humains.", "android.resource://"
                        + getPackageName() + "/drawable/venus"),
                new Planet("Triangulum", 3f, "M KM", "Galaxie",
                        "La galaxie du Triangulum, également appelée M33, est une galaxie spirale située à environ 3 millions d'années-lumière de la Terre. C'est la troisième " +
                                "plus grande galaxie du Groupe local, après la Voie lactée et Andromède. La galaxie du Triangulum abrite une variété d'objets célestes, notamment des étoiles, des nébuleuses et des amas d'étoiles.",
                        "android.resource://"
                                + getPackageName() + "/drawable/triangulum"),
                new Planet("Centaurus A", 13f, "M Km", "Galaxie",
                        "Centaurus A, également connue sous le nom de NGC 5128, est une galaxie elliptique située à environ 13 millions d'années-lumière de la Terre. Elle est " +
                                "caractérisée par une bande de poussière sombre qui traverse son centre, résultant probablement de la fusion de deux galaxies. Centaurus A est également un " +
                                "puissant émetteur de rayons X et de radio, ce qui indique la présence d'un trou noir supermassif en son centre.",
                        "android.resource://"
                                + getPackageName() + "/drawable/centaurus_a"),
                new Planet("Soleil", 149.6f, "M km","Étoile",
                        "Le Soleil est une étoile naine jaune de la séquence principale située au centre de notre système solaire, à environ 150 millions de kilomètres de la " +
                                "Terre. Il est composé principalement d'hydrogène (environ 75%) et d'hélium (environ 25%), et génère son énergie par fusion nucléaire, convertissant " +
                                "l'hydrogène en hélium." +
                                " Le Soleil est responsable de la chaleur, de la lumière et des conditions propices à la vie sur Terre. Il a également un impact significatif sur " +
                                "notre climat, notre météo et les aurores polaires. L'étude du Soleil est cruciale pour comprendre les processus stellaires et les effets des éruptions " +
                                "solaires sur les systèmes électroniques terrestres et spatiaux.", "android.resource://"
                        + getPackageName() + "/drawable/soleil"),
                new Planet("Uranus", 2.87f, "B km", "Planète",
                        "Uranus est la septième planète du système solaire et est classée parmi les géantes glacées, composée principalement d'hydrogène, d'hélium et de " +
                                "glaces, telles que l'eau, l'ammoniac et le méthane. Uranus est unique en raison de son inclinaison axiale extrême, qui fait que ses pôles sont presque " +
                                "dans le plan de son orbite " +
                                "autour du Soleil. Cette inclinaison inhabituelle est probablement due à une collision avec un autre corps céleste au cours de la formation du système " +
                                "solaire. Uranus possède également un système d'anneaux et de nombreuses lunes, dont certaines pourraient abriter des océans souterrains. En raison de sa " +
                                "distance éloignée du Soleil et de ses températures extrêmement froides, il est peu probable que la vie telle que nous la connaissons puisse exister sur Uranus.", "android.resource://"
                        + getPackageName() + "/drawable/uranus")
        ));

        planetAdapter = new PlanetAdapter(planetList);
        recyclerView.setAdapter(planetAdapter);

        planetAdapter.setOnItemClickListener(new PlanetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Planet planet, int position) {
                showDetailActivity(planet, position);
            }

            @Override
            public void onObjectTypeClick(String objectType) {
                planetAdapter.filterByObjectType(objectType);
                updateResetFilterButtonVisibility();
            }
        });

        String objectType = getIntent().getStringExtra("objectType");
        if (objectType != null) {
            planetAdapter.filterByObjectType(objectType);
            updateResetFilterButtonVisibility();
        }

        addPlanetButton = findViewById(R.id.floating_button);
        addPlanetButton.setOnClickListener(v -> {
            Intent addPlanetIntent = new Intent(MainActivity.this, AddPlanetActivity.class);
            addPlanetActivityResultLauncher.launch(addPlanetIntent);
        });

        detailActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        int position = result.getData().getIntExtra("position_to_remove", -1);
                        if (position != -1) {
                            planetList.remove(position);
                            planetAdapter.notifyItemRemoved(position);
                        }
                    }
                });

        addPlanetActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Planet newPlanet = (Planet) result.getData().getSerializableExtra("NEW_PLANET");
                        if (newPlanet != null) {
                            planetList.add(newPlanet);
                            planetAdapter.notifyItemInserted(planetList.size() - 1);
                        }
                    }
                });
    }

    private void showDetailActivity(Planet planet, Integer position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("planet", planet);
        intent.putExtra("position", (Integer) position);
        detailActivityResultLauncher.launch(intent);
    }

    private void updateResetFilterButtonVisibility() {
        if (planetAdapter.isFilterApplied()) {
            resetFilterButton.setVisibility(View.VISIBLE);
        } else {
            resetFilterButton.setVisibility(View.GONE);
        }
    }
}
