package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends SupportMapFragment implements
        OnMapReadyCallback {
    private GoogleMap miMapa;
    public MapaFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);
        int tipoMapa=0;
        Bundle argumentos = getArguments();
        if(argumentos !=null) {
            tipoMapa = argumentos.getInt("tipo_mapa",0);
        }
        getMapAsync(this);
        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        miMapa = googleMap;
        actualizarMapa();
    }
    private void actualizarMapa() {
        if (ActivityCompat.checkSelfPermission(this.getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        miMapa.setMyLocationEnabled(true);
        
    }
}