package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.MyDatabase;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.ReclamoDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap miMapa;
    private int tipoMapa;
    private int idReclamoSeleccionado;
    private OnMapaListener listener;

    private ReclamoDao reclamoDao;

    private List<Reclamo> reclamos;

    public MapaFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);
        tipoMapa=0;
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
        if(tipoMapa == 1){
            miMapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    listener.coordenadasSeleccionadas(latLng);
                }
            });
        }
        else {
            if(tipoMapa == 2){
                cargarMapaConTodosReclamos();
            }
        }
    }

    private void cargarMapaConTodosReclamos(){

        reclamoDao = MyDatabase.getInstance(getContext()).getReclamoDao();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                reclamos = reclamoDao.getAll();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for(Reclamo r : reclamos){
                            Marker marker = miMapa.addMarker(new MarkerOptions()
                                    .position(new LatLng(r.getLatitud(),r.getLongitud()))
                                    .title(r.getId() + "[" + r.getTipo().toString() + "]")
                                    .snippet(r.getReclamo()));
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds limite = builder.build();
                        miMapa.moveCamera(CameraUpdateFactory.newLatLngBounds(limite, 0));
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
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

    public void setListener(OnMapaListener listener) {
        this.listener = listener;
    }

    public interface OnMapaListener {
        public void coordenadasSeleccionadas(LatLng c);
    }
}