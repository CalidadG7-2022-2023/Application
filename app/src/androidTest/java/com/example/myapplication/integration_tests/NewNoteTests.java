package com.example.myapplication.integration_tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;
import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.SQLiteTableUsers;
import com.example.myapplication.model.Note;
import com.example.myapplication.model.User;
import com.example.myapplication.view.NotesMaker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NewNoteTests {

    @Rule
    public ActivityTestRule<NotesMaker> mActivityRule = new ActivityTestRule<>(NotesMaker.class);
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
    public void testBadNote() {
        createDataBaseData();
        assertTrue(this.tableNotes.existsNote(this.note));
        onView(withId(R.id.txtTitle)).perform(typeText("Title1"),closeSoftKeyboard());
        onView(withId(R.id.txtText)).perform(typeText("Description1"),closeSoftKeyboard());
        onView(withId(R.id.bttGuardar)).perform(click());
        // Check in DB Note1 doesn't exist
        assertFalse(this.tableNotes.existsNote(this.note));
        deleteData();
    }

    @Test
    public void testNote() {
        createDataBaseData();
        Note newNote = new Note("Title2", "Description2");
        assertFalse(this.tableNotes.existsNote(newNote));
        onView(withId(R.id.txtTitle)).perform(typeText(newNote.getTitle()),closeSoftKeyboard());
        onView(withId(R.id.txtText)).perform(typeText(newNote.getText()),closeSoftKeyboard());
        onView(withId(R.id.bttGuardar)).perform(click());
        assertTrue(mActivityRule.getActivity().isFinishing());
        // Check in DB User2 exists
        assertTrue(this.tableNotes.existsNote(newNote));
        deleteData();
    }

}
