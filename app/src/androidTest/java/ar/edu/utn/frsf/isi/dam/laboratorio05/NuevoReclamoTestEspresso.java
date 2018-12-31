package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NuevoReclamoTestEspresso {

    NuevoReclamoFragment fragmentNR;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =  new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        fragmentNR = new NuevoReclamoFragment();
        //cargo las coordenadas previamente
        fragmentNR.setCoordenadas("-60.123;30.134");
        mActivityRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenido, fragmentNR)
                .commit();
    }

    @Test
    public void GuardarReclamoSemaforoConDescripcionMayor8() {
        //suponiendo las coordenadas ya cargadas anteriomente
        SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).perform(click()).perform(typeText("reclamo@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.reclamo_desc)).perform(click()).perform(typeText("descripcion de reclamo larga"),closeSoftKeyboard());
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.reclamo_tipo)).check(matches(withSpinnerText(containsString(Reclamo.TipoReclamo.SEMAFOROS.toString()))));
        SystemClock.sleep(2500);
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));
        onView(withId(R.id.btnGuardar)).perform(click());
        //SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).check(matches(withText("")));
        onView(withId(R.id.reclamo_desc)).check(matches(withText("")));
        onView(withId(R.id.reclamo_coord)).check(matches(withText("0;0")));
    }

    @Test
    public void GuardarReclamoSemaforoConAudio() {
        //suponiendo las coordenadas ya cargadas anteriomente
        SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).perform(click()).perform(typeText("reclamo@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.reclamo_tipo)).check(matches(withSpinnerText(containsString(Reclamo.TipoReclamo.SEMAFOROS.toString()))));
        SystemClock.sleep(2500);
        onView(withId(R.id.btnGrabarAudio)).perform(click());
        SystemClock.sleep(2500);
        onView(withId(R.id.btnGrabarAudio)).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));
        onView(withId(R.id.btnGuardar)).perform(click());
        //SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).check(matches(withText("")));
        onView(withId(R.id.reclamo_desc)).check(matches(withText("")));
        onView(withId(R.id.reclamo_coord)).check(matches(withText("0;0")));
    }

    @Test
    public void GuardarReclamoVeredaSinFoto() {
        //suponiendo las coordenadas ya cargadas anteriomente
        SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).perform(click()).perform(typeText("reclamo@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.reclamo_tipo)).check(matches(withSpinnerText(containsString(Reclamo.TipoReclamo.VEREDAS.toString()))));
        SystemClock.sleep(2500);
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));
    }

    @Test
    public void GuardarReclamoCalleEnMalEstadoConFoto() {
        //suponiendo las coordenadas ya cargadas anteriomente
        SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).perform(click()).perform(typeText("reclamo@gmail.com"),closeSoftKeyboard());
        //cargo path de foto
        fragmentNR.setPathImagen("/este/es/un/path/de/foto.jpg");
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.reclamo_tipo)).check(matches(withSpinnerText(containsString(Reclamo.TipoReclamo.CALLE_EN_MAL_ESTADO.toString()))));
        SystemClock.sleep(2500);
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));
        onView(withId(R.id.btnGuardar)).perform(click());
        SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).check(matches(withText("")));
        onView(withId(R.id.reclamo_desc)).check(matches(withText("")));
        onView(withId(R.id.reclamo_coord)).check(matches(withText("0;0")));
    }


    public void enableOrDisableBtnGuardar() {
        //suponiendo las coordenadas ya cargadas anteriomente
        SystemClock.sleep(2500);
        onView(withId(R.id.reclamo_mail)).perform(click()).perform(typeText("reclamo@gmail.com"),closeSoftKeyboard());

        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.reclamo_tipo)).check(matches(withSpinnerText(containsString(Reclamo.TipoReclamo.SEMAFOROS.toString()))));

        onView(withId(R.id.reclamo_desc)).perform(click()).perform(typeText("reclamoMayorA8Caracteres"),closeSoftKeyboard());
        SystemClock.sleep(2500);

        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        onView(withId(R.id.reclamo_desc)).perform(click()).perform(clearText());
        onView(withId(R.id.reclamo_desc)).perform(click()).perform(typeText("menorA8"),closeSoftKeyboard());
        SystemClock.sleep(2500);

        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        onView(withId(R.id.btnGrabarAudio)).perform(click());
        SystemClock.sleep(2500);
        onView(withId(R.id.btnGrabarAudio)).perform(click());

        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));
        onView(withId(R.id.btnGuardar)).perform(click());

        onView(withId(R.id.reclamo_mail)).check(matches(withText("")));
        onView(withId(R.id.reclamo_desc)).check(matches(withText("")));
        onView(withId(R.id.reclamo_coord)).check(matches(withText("0;0")));
    }
}