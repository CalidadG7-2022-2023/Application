package com.example.myapplication.integration_tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;
import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.SQLiteTableUsers;
import com.example.myapplication.model.Note;
import com.example.myapplication.model.User;
import com.example.myapplication.view.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DeleteNoteTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    private EligaNotesDB eligaNotesDB;
    private SQLiteTableUsers tableUsers;
    private Context context;
    private SQLiteTableNotes tableNotes;
    private User user;
    private Note note;

    private void createDataBaseData() {
        this.context = InstrumentationRegistry.getTargetContext();

        this.eligaNotesDB = EligaNotesDB.getInstance(this.context);
        this.tableUsers = new SQLiteTableUsers(this.context);
        this.tableNotes = new SQLiteTableNotes(this.context);

        tableUsers.clearData();
        tableNotes.clearData();
        this.user = new User("User1", "pwd1", "pwd1");
        this.tableUsers.insertData(this.user);
        this.note = new Note("Title1", "Description1");
        this.tableNotes.insertData(this.note, "User1");
    }

    private void deleteData() {
        tableUsers.clearData();
        tableNotes.clearData();
    }

    @Test
    public void testDelete() {
        createDataBaseData();

        //Check note exists previously
        assertTrue(this.tableNotes.existsNote(this.note));

        //Log in
        User newUser = new User("User1", "pwd1", "pwd1");
        onView(withId(R.id.nomb_txt)).perform(typeText(newUser.getName()),closeSoftKeyboard());
        onView(withId(R.id.pass_txt)).perform(typeText(newUser.getPassword()), closeSoftKeyboard());
        assertTrue(this.tableUsers.existsUser(newUser));
        onView(withId(R.id.button)).perform(click());

        //Delete note
        onView(withId(R.id.listanotas))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.button3)));

        //Check note is not stored in DB
        assertFalse(this.tableNotes.existsNote(this.note));

        deleteData();
    }

    private static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(ViewGroup.class);
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}