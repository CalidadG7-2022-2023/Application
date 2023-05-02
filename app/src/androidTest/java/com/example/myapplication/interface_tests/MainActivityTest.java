package com.example.myapplication.interface_tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    private Context context;
    private EligaNotesDB eligaNotesDB;
    private SQLiteTableUsers tableUsers;


    private void createUser() {
        this.context = InstrumentationRegistry.getTargetContext();

        this.eligaNotesDB = EligaNotesDB.getInstance(this.context);
        this.tableUsers = new SQLiteTableUsers(this.context);

        this.tableUsers.insertData(new User("Federico", "1234", "1234"));
    }

    public void deleteData() {
        this.tableUsers.clearData();
    }
    @Test
    public void testLoginUser() {
        createUser();
        onView(ViewMatchers.withId(R.id.nomb_txt)).perform(typeText("Federico"));
        onView(withId(R.id.pass_txt)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.listanotas)).check(matches(isCompletelyDisplayed()));
        deleteData();
    }
}
