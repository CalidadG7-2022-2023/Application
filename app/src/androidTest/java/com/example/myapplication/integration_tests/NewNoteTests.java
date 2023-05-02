package com.example.myapplication.integration_tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;
import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.SQLiteTableUsers;
import com.example.myapplication.model.Note;
import com.example.myapplication.model.User;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.NotesMaker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NewNoteTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private EligaNotesDB eligaNotesDB;
    private SQLiteTableUsers tableUsers;
    private Context context;
    private SQLiteTableNotes tableNotes;
    private User user;
    private Note note;

    private void createDataBaseData() {
        this.context = InstrumentationRegistry.getInstrumentation().getTargetContext();

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
    public void testBadNote() {
        createDataBaseData();

        //Check note exists previously
        assertTrue(this.tableNotes.existsNote(this.note));

        //Log in
        User newUser = new User("User1", "pwd1", "pwd1");
        onView(withId(R.id.nomb_txt)).perform(typeText(newUser.getName()),closeSoftKeyboard());
        onView(withId(R.id.pass_txt)).perform(typeText(newUser.getPassword()), closeSoftKeyboard());
        assertTrue(this.tableUsers.existsUser(newUser));
        onView(withId(R.id.button)).perform(click());

        //Insert newNote with duplicated Primary Key and different description
        onView(withId(R.id.newNote)).perform(click());
        Note newNote = new Note("Title1", "Description2");
        onView(withId(R.id.txtTitle)).perform(typeText(newNote.getTitle()),closeSoftKeyboard());
        onView(withId(R.id.txtText)).perform(typeText(newNote.getText()),closeSoftKeyboard());
        onView(withId(R.id.bttGuardar)).perform(click());
        onView(withId(R.id.bttGuardar)).check(matches(isEnabled()));
        assertTrue(this.tableNotes.existsNote(this.note));

        //Check old note is still stored in DB
        Note dbNote = this.tableNotes.obtainNoteById(this.note.getTitle());
        assertEquals(this.note.getTitle(), dbNote.getTitle());
        assertEquals(this.note.getText(), dbNote.getText());
        deleteData();
    }

    @Test
    public void testNote() {
        createDataBaseData();

        //Log in
        User newUser = new User("User1", "pwd1", "pwd1");
        onView(withId(R.id.nomb_txt)).perform(typeText(newUser.getName()),closeSoftKeyboard());
        onView(withId(R.id.pass_txt)).perform(typeText(newUser.getPassword()), closeSoftKeyboard());
        assertTrue(this.tableUsers.existsUser(newUser));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.newNote)).perform(click());

        //Insert newNote
        Note newNote = new Note("Title2", "Description2");
        assertFalse(this.tableNotes.existsNote(newNote));
        onView(withId(R.id.txtTitle)).perform(typeText(newNote.getTitle()),closeSoftKeyboard());
        onView(withId(R.id.txtText)).perform(typeText(newNote.getText()),closeSoftKeyboard());
        onView(withId(R.id.bttGuardar)).perform(click());
        assertTrue(this.tableNotes.existsNote(newNote));
        deleteData();
    }

}
