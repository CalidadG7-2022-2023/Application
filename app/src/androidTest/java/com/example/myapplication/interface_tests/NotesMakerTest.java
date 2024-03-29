package com.example.myapplication.interface_tests;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;
import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.SQLiteTableUsers;
import com.example.myapplication.model.User;
import com.example.myapplication.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NotesMakerTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private Context context;
    private EligaNotesDB eligaNotesDB;
    private SQLiteTableUsers tableUsers;
    private SQLiteTableNotes tableNotes;

    private void createUser() {
        this.context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        this.eligaNotesDB = EligaNotesDB.getInstance(this.context);
        this.tableUsers = new SQLiteTableUsers(this.context);

        this.tableUsers.insertData(new User("Federico", "1234", "1234"));
        this.tableNotes = new SQLiteTableNotes(this.context);
    }
    public void deleteData() {
        this.tableUsers.clearData();
        this.tableNotes.clearData();
    }

    @Test
    public void testCreateNote() {
        createUser();
        onView(ViewMatchers.withId(R.id.nomb_txt)).perform(typeText("Federico"), closeSoftKeyboard());
        onView(withId(R.id.pass_txt)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.newNote)).perform(click());
        onView(withId(R.id.txtTitle)).perform(typeText("Prueba"), closeSoftKeyboard());
        onView(withId(R.id.txtText)).perform(typeText("Descripcion"), closeSoftKeyboard());
        onView(withId(R.id.bttGuardar)).perform(click());
        onView(withId(R.id.listanotas)).check(matches(isCompletelyDisplayed()));
        deleteData();
    }
}
