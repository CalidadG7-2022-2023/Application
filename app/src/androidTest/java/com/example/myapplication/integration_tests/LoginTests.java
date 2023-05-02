package com.example.myapplication.integration_tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import com.example.myapplication.R;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.SQLiteTableUsers;
import com.example.myapplication.model.Note;
import com.example.myapplication.model.User;
import com.example.myapplication.view.MainActivity;



import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);
    private EligaNotesDB eligaNotesDB;

    private Context context;

    private SQLiteTableNotes tableNotes;

    private SQLiteTableUsers tableUsers;
    private Note noteChecked;


    public void createDataBase() {
        this.context = InstrumentationRegistry.getTargetContext();

        this.eligaNotesDB = EligaNotesDB.getInstance(this.context);
        this.tableUsers = new SQLiteTableUsers(this.context);
        this.tableNotes = new SQLiteTableNotes(this.context);

        this.tableUsers.insertData(new User("User1", "pwd1", "pwd1"));
        this.tableUsers.insertData(new User("User2", "pwd2", "pwd2"));
        this.tableUsers.insertData(new User("User3", "pwd3", "pwd3"));
        this.tableUsers.insertData(new User("User4", "pwd4", "pwd4"));
        this.tableUsers.insertData(new User("User5", "pwd5", "pwd5"));

        this.tableNotes.insertData(new Note("Title1", "Description1"), "User1");
        this.noteChecked = new Note("Title2", "Description2");
        this.tableNotes.insertData(this.noteChecked, "User2");
        this.tableNotes.insertData(new Note("Title3", "Description3"), "User3");
        this.tableNotes.insertData(new Note("Title4", "Description4"), "User4");
        this.tableNotes.insertData(new Note("Title5", "Description5"), "User5");
        this.noteChecked = new Note("Title2", "Description2");
        this.tableNotes.insertData(this.noteChecked, "User2");
    }

    public void deleteData() {
        this.tableNotes.clearData();
        this.tableUsers.clearData();
    }

    @Test
    public void testLoginCorrect() {

        createDataBase();

        //Log in data
        User newUser = new User("User1", "pwd1", "pwd1");
        onView(withId(R.id.nomb_txt)).perform(typeText(newUser.getName()),closeSoftKeyboard());
        onView(withId(R.id.pass_txt)).perform(typeText(newUser.getPassword()), closeSoftKeyboard());
        assertTrue(this.tableUsers.existsUser(newUser));

        //Send data to the system
        onView(withId(R.id.button)).perform(click());

        //Check new element is displayed after (screen changed)
        onView(withId(R.id.listanotas)).check(matches(isEnabled()));

        //Check DB still stores newUser
        assertTrue(this.tableUsers.existsUser(newUser));
        deleteData();
    }
    @Test
    public void testLoginInCorrect() {

        createDataBase();

        //Log in data
        User newUser = new User("User8", "pwd8", "pwd8");
        assertFalse(this.tableUsers.existsUser(newUser));
        onView(withId(R.id.nomb_txt)).perform(typeText(newUser.getName()), closeSoftKeyboard());
        onView(withId(R.id.pass_txt)).perform(typeText(newUser.getPassword()), closeSoftKeyboard());
        assertFalse(this.tableUsers.existsUser(newUser));

        //Send data to the system
        onView(withId(R.id.button)).perform(click());

        //Check old element is displayed (screen not changed)
        onView(withId(R.id.button)).check(matches(isEnabled()));

        //Check DB doesn't store newUser
        assertFalse(this.tableUsers.existsUser(newUser));

        deleteData();
    }
}
