package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.content.Context;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.ReclamoDao;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class NuevoReclamoTestRobolectric {

    NuevoReclamoFragment fragmentNR;

    @Mock
    Context ctx;

    @Mock
    ReclamoDao daoMock;

    @Mock
    Reclamo reclamoMock;

    @Mock
    TextView tvCoordMock;
    @Mock
    Spinner tipoReclamoMock;
    @Mock
    ImageView imagenMock;
    @Mock
    Button btnGuardarMock;

    @Mock
    ArrayAdapter<Reclamo.TipoReclamo> tipoReclamoAdapterMock;
    @Mock
    EditText edtReclamoDescMock;
    @Mock
    EditText edtMailMock;
    @Mock
    Editable edtMock;
    @Mock
    Editable edtMock1;

    @Test
    public void reclamo_insert() {
        fragmentNR = new NuevoReclamoFragment();

        doReturn("reclamoooooooo").when(edtMock).toString();
        doReturn("60.41;35.23").when(edtMock1).toString();
        doReturn(edtMock).when(edtMailMock).getText();
        doReturn(edtMock).when(edtReclamoDescMock).getText();
        doReturn("60.41;35.23").when(tvCoordMock).getText();
        //doReturn(tipoReclamoMock.getSelectedItemPosition()).when(tipoReclamoMock).getSelectedItemPosition();
        //doReturn(Reclamo.TipoReclamo.SEMAFOROS).when(tipoReclamoAdapterMock).getItem(a);
        doNothing().when(reclamoMock).setLatitud(anyDouble());
        doNothing().when(reclamoMock).setLongitud(anyDouble());

        doReturn(1l).when(daoMock).insert(reclamoMock);
        fragmentNR.setCoordenadas("60.41;35.23");
        fragmentNR.setTipoReclamoSeleccionado(Reclamo.TipoReclamo.SEMAFOROS);
        fragmentNR.setReclamo(reclamoMock);
        fragmentNR.setReclamoDao(daoMock);
        fragmentNR.setEditTextMail(edtMailMock);
        fragmentNR.setEditTextDesc(edtReclamoDescMock);
        fragmentNR.setPathImagen("pathImagen");
        fragmentNR.setPathAudio("pathAudio");
        fragmentNR.saveOrUpdateReclamo();


        verify(daoMock, times(1)).insert(reclamoMock);
        verify(daoMock, times(0)).update(reclamoMock);


    }

}