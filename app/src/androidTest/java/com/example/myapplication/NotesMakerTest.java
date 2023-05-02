package com.example.myapplication;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NotesMakerTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCreateNote() {
        onView(withId(R.id.nomb_txt)).perform(typeText("Federico"));
        onView(withId(R.id.pass_txt)).perform(typeText("1234"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.newNote)).perform(click());
        onView(withId(R.id.txtTitle)).perform(typeText("Prueba"));
        onView(withId(R.id.txtText)).perform(typeText("shdagfjhdasgfhjgdsaf"));
        onView(withId(R.id.bttGuardar)).perform(click());
        onView(withId(R.id.listanotas)).check(matches(isCompletelyDisplayed()));
    }
}
