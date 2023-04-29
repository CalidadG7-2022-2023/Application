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
public class RegistroTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRegistroUsuario() {
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.txtUser)).perform(typeText("Manolo"));
        onView(withId(R.id.txtPassword)).perform(typeText("1234"));
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("1234"));
        onView(withId(R.id.bttconfirm)).perform(click());
        onView(withId(R.id.button2)).check(matches(isCompletelyDisplayed()));
    }

}
