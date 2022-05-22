package bsuir.diplom.mercury.fragments.driverSide;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.turf.TurfMeasurement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.Car;
import bsuir.diplom.mercury.entities.Offer;
import bsuir.diplom.mercury.entities.TSP;
import bsuir.diplom.mercury.entities.dto.AddressDTO;
import bsuir.diplom.mercury.entities.enums.OfferStatus;
import bsuir.diplom.mercury.utils.CarHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverNavigationFragment extends Fragment implements OnMapReadyCallback, PermissionsListener, LocationEngineListener {
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Point originPoint;
    private Point destinationPoint;
    private Marker destinationMarker;
    private Button startButton;
    private NavigationMapRoute navigationMapRoute;
    private final List<Point> pointList = new ArrayList<>();

    private final DatabaseReference offersReference = FirebaseDatabase.getInstance().getReference("Offers");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), getString(R.string.access_token));
        View view = inflater.inflate(R.layout.fragment_driver_navigation, container, false);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        startButton = view.findViewById(R.id.start_button);

        startButton.setOnClickListener(view1 -> {
            originPoint = pointList.get(0);
            destinationPoint = pointList.get(1);
            NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                    .origin(originPoint)
                    .destination(destinationPoint)
                    .shouldSimulateRoute(true)
                    .build();
            NavigationLauncher.startNavigation(getActivity(), options);
        });

        mapView.getMapAsync(this);
        offersReference.orderByChild("offerStatus").equalTo(OfferStatus.IN_PROGRESS.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Offer> offerList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Offer offer = snap.getValue(Offer.class);
                    offer.setOfferId(snap.getKey());
                    Car currentCar = CarHelper.getCarByDriverPhoneNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    if (offer.getChosenCar().getCarId().equals(currentCar.getCarId())) {
                        offerList.add(offer);
                        Log.d("OfferList", offer.toString());
                    }
                }

                Map<AddressDTO, Map<AddressDTO, Double>> vtDistances = new HashMap<>();
                List<AddressDTO> addressDTOS = new ArrayList<>();
                offerList.forEach(offer -> {
                    addressDTOS.add(offer.getAddressFrom());
                    addressDTOS.add(offer.getAddressTo());
                });
                addressDTOS.forEach(addressDTO -> {
                    List<AddressDTO> addressesCopy = new ArrayList<>(addressDTOS);
                    addressesCopy.remove(addressDTO);
                    Map<AddressDTO, Double> distancesFromPoint = new HashMap<>();
                    addressesCopy.forEach(otherAddress -> {
                        Point originPoint = Point.fromLngLat(addressDTO.getLongitude(), addressDTO.getLatitude());
                        Point destinationPoint = Point.fromLngLat(otherAddress.getLongitude(), otherAddress.getLatitude());
                        Double distance = TurfMeasurement.distance(originPoint, destinationPoint);
                        distancesFromPoint.put(otherAddress, distance);
                    });
                    vtDistances.put(addressDTO, distancesFromPoint);
                });

                Log.d("Result", String.valueOf(vtDistances));

                TSP tsp = new TSP(vtDistances);
                AddressDTO[] shortestPathArr = tsp.findShortestPath();
                List<AddressDTO> shortestPath = new ArrayList<>(Arrays.asList(tsp.findShortestPath()));
                double distance = tsp.pathDistance(shortestPathArr);

                Log.d("Shortest path", shortestPath.toString());
                Log.d("distance", distance + "");

                NavigationRoute.Builder builder = NavigationRoute.builder()
                        .accessToken(Mapbox.getAccessToken());
                //Point currentLocationPoint = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());
                //pointList.add(currentLocationPoint);
                //builder.addWaypoint(currentLocationPoint);
                Point origin = Point.fromLngLat(shortestPath.get(0).getLongitude(), shortestPath.get(0).getLatitude());
                pointList.add(origin);
                builder.origin(origin);
                for (int i = 1; i < shortestPath.size() - 2; i++) {
                    Point wayPoint = Point.fromLngLat(shortestPath.get(i).getLongitude(), shortestPath.get(i).getLatitude());
                    builder.addWaypoint(wayPoint);
                    pointList.add(wayPoint);
                }
                Point destination = Point.fromLngLat(shortestPath.get(shortestPath.size() - 2).getLongitude(), shortestPath.get(shortestPath.size() - 2).getLatitude());
                builder.destination(destination)
                        .build()
                        .getRoute(new Callback<DirectionsResponse>() {
                            @Override
                            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                                if (response.body() == null) {
                                    Log.e("MainActivity", "No routes found, check for access token");
                                } else if (response.body().routes().size() == 0) {
                                    Log.e("MainActivity", "No routes found");
                                } else {
                                    DirectionsRoute currentRoute = response.body().routes().get(0);

                                    if (navigationMapRoute != null) {
                                        navigationMapRoute.removeRoute();
                                    } else {
                                        navigationMapRoute = new NavigationMapRoute(null, mapView, map);
                                    }
                                    navigationMapRoute.addRoute(currentRoute);
                                }
                            }

                            @Override
                            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                                Log.e("MainActivity", t.getMessage());
                            }
                        });

                //map.addMarker(new MarkerOptions().position(new LatLng(origSecPoint.latitude(), origSecPoint.longitude())));
                //map.addMarker(new MarkerOptions().position(new LatLng(destSecPoint.latitude(), destSecPoint.longitude())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        //map.addOnMapClickListener(this);
        enableLocation();
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
    }

/*    @Override
    public void onMapClick(@NonNull LatLng point) {
        if (destinationMarker != null) {
            map.removeMarker(destinationMarker);
        }

        destinationMarker = map.addMarker(new MarkerOptions().position(point));
        destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        originPoint = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());

        getRoute(originPoint, destinationPoint);
    }*/

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null) {
                            Log.e("MainActivity", "No routes found, check for access token");
                        } else if (response.body().routes().size() == 0) {
                            Log.e("MainActivity", "No routes found");
                        } else {
                            DirectionsRoute currentRoute = response.body().routes().get(0);

                            if (navigationMapRoute != null) {
                                navigationMapRoute.removeRoute();
                            } else {
                                navigationMapRoute = new NavigationMapRoute(null, mapView, map);
                            }
                            navigationMapRoute.addRoute(currentRoute);
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e("MainActivity", t.getMessage());
                    }
                });
    }

    private void enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
            initLocationEngine();
            initLocationLayer();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = new LocationEngineProvider(getContext()).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
            setCameraPosition(lastLocation);
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocationLayer() {
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }

    private void setCameraPosition(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
            setCameraPosition(location);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //Present toast or dialog
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();
        if (locationEngine != null)
            locationEngine.requestLocationUpdates();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }
}