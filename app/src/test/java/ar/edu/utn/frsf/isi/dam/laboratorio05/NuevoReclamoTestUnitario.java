package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.media.Image;
import android.widget.EditText;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.ReclamoDao;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class NuevoReclamoTestUnitario {

    NuevoReclamoFragment fragmentNR;

    public class ReclamoDaoFalso implements ReclamoDao {

        public void update(Reclamo r){
            System.out.println("EJECUTO UPDATE con "+r);
        }

        @Override
        public void delete(Reclamo r) {

        }

        @Override
        public List<Reclamo> getAll() {
            return null;
        }

        @Override
        public Reclamo getById(int rIdReclamo) {
            return null;
        }

        @Override
        public List<Reclamo> getByTipo(String pTipo) {
            return null;
        }

        public long insert(Reclamo r){
            System.out.println("EJECUTO insert con "+r);
            return 1l;
        }
    }

    ReclamoDao miDaoFalso = new ReclamoDaoFalso();

    @Mock
    Reclamo reclamoMock;


    @Test
    public void estadoHabilitadoBotonGuardar() {
        fragmentNR = new NuevoReclamoFragment();
        assertTrue("el boton esta habilitado para guardar el reclamo",
                fragmentNR.estadoBtnGuardar("SEMAFOROS","reclamo largo mayor que 8 caracteres","existeAudio",""));
    }

    @Test
    public void estadoHabilitadoBotonGuardar2() {
        fragmentNR = new NuevoReclamoFragment();

        assertTrue("el boton esta habilitado para guardar el reclamo porque existe reclamo mayor que 8 caracteres",
                fragmentNR.estadoBtnGuardar("SEMAFOROS","reclamo largo",null,""));
    }

    @Test
    public void estadoHabilitadoBotonGuardar3() {
        fragmentNR = new NuevoReclamoFragment();

        assertTrue("el boton esta habilitado para guardar el reclamo porque existe audio",
                fragmentNR.estadoBtnGuardar("SEMAFOROS","<8","existeAudio",""));
    }

    @Test
    public void estadoHabilitadoBotonGuardar4() {
        fragmentNR = new NuevoReclamoFragment();

        assertTrue("el boton esta habilitado para guardar el reclamo porque existe foto del reclamo",
                fragmentNR.estadoBtnGuardar("VEREDA","<8","existeAudio","existeFoto"));
    }

    @Test
    public void estadoNoHabilitadoBotonGuardar() {
        fragmentNR = new NuevoReclamoFragment();

        assertFalse("el boton no esta habilitado para guardar el reclamo",
                fragmentNR.estadoBtnGuardar("SEMAFOROS","<8",null,""));
    }

    @Test
    public void estadoNoHabilitadoBotonGuardar2() {
        fragmentNR = new NuevoReclamoFragment();

        assertFalse("el boton no esta habilitado para guardar el reclamo porque no existe foto",
                fragmentNR.estadoBtnGuardar("VEREDA","<8",null,""));
    }

}
