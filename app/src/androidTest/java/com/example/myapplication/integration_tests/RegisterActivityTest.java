package com.example.myapplication.integration_tests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
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
import com.example.myapplication.view.Registro;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<Registro> mActivityRule = new ActivityTestRule<>(Registro.class);
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
    public void testBadRegister(){
        createDataBaseData();
        //Check user exists previously
        assertTrue(this.tableUsers.existsUser(this.user));

        //Insert data
        onView(withId(R.id.txtUser)).perform(typeText("User1"));
        onView(withId(R.id.txtPassword)).perform(typeText("pwd100"),closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("pwd100"), closeSoftKeyboard());
        onView(withId(R.id.bttconfirm)).perform(click());

        //Check user still exists and it hasn't been modified
        assertTrue(this.tableUsers.existsUser(this.user));
        User dbUser = this.tableUsers.obtainUserById(this.user.getName());

        assertEquals(this.user.getName(), dbUser.getName());
        assertEquals(this.user.getPassword(), dbUser.getPassword());
        assertEquals(this.user.getRepassword(), dbUser.getRepassword());

        deleteData();
    }

    @Test
    public void testRegister() {
        createDataBaseData();
        User newUser = new User("User2", "pwd2", "pwd2");

        //Check user doesn't exist previously
        assertFalse(this.tableUsers.existsUser(newUser));
        onView(withId(R.id.txtUser)).perform(typeText(newUser.getName()));
        onView(withId(R.id.txtPassword)).perform(typeText(newUser.getPassword()),closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText(newUser.getRepassword()), closeSoftKeyboard());
        onView(withId(R.id.bttconfirm)).perform(click());

        //Check Activity ended
        assertTrue(mActivityRule.getActivity().isFinishing());

        //Check new user is stored in DB
        assertTrue(this.tableUsers.existsUser(newUser));
        deleteData();
    }
}
