package com.example.myapplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.myapplication.database.SQLiteTableUsers;
import com.example.myapplication.database.TableUsers;
import com.example.myapplication.model.User;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SQLiteTableUsersTest {

    private User[] users;

    @Before
    public void initializeUsers() {

        this.users = new User[8];

        String[] usernames = { "", "User1", "User2", "User3", "User4", "", "User6", "User1" };
        String[] passwords = { "", "G5rBgF35", "4774lfLA7tEf", "-aM7KJEdO?=178Cj", "Y%Mt5j*GNz5%Si@SwwZh=i*N6=NI0ye8", "19sa2sf1af21", "", "i*N6=NI0ye8" };

        User user;

        for (int i = 0; i < 8; i++) {

            user = new User();
            user.setName(usernames[i]);
            user.setPassword(passwords[i]);

            users[i] = user;

        }

    }

    @Test
    public void insertUser1Test() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[1])).thenReturn(1L);

        Assert.assertNotEquals(db.insertData(this.users[1]), 0L);

    }

    @Test
    public void insertUser2Test() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[2])).thenReturn(1L);

        Assert.assertNotEquals(db.insertData(this.users[2]), 0L);

    }

    @Test
    public void insertUser3Test() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[3])).thenReturn(1L);

        Assert.assertNotEquals(db.insertData(this.users[3]), 0L);

    }

    @Test
    public void insertUser4Test() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[4])).thenReturn(1L);

        Assert.assertNotEquals(db.insertData(this.users[4]), 0L);

    }

    @Test
    public void insertUserEmptyUsernameTest() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[5])).thenReturn(0L);

        Assert.assertEquals(db.insertData(this.users[5]), 0L);

    }

    @Test
    public void insertUserEmptyPasswordTest() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[6])).thenReturn(0L);

        Assert.assertEquals(db.insertData(this.users[6]), 0L);

    }

    @Test
    public void insertUserEmptyUsernameAndPasswordTest() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[0])).thenReturn(0L);

        Assert.assertEquals(db.insertData(this.users[0]), 0L);

    }

    @Test
    public void insertUserExistingUsernameTest() {

        TableUsers db = mock(SQLiteTableUsers.class);
        when(db.insertData(this.users[1])).thenReturn(1L);
        when(db.insertData(this.users[7])).thenReturn(0L);

        Assert.assertNotEquals(db.insertData(this.users[1]), 0L);
        Assert.assertEquals(db.insertData(this.users[7]), 0L);

    }

    @Test
    public void getUsersTest() {

        TableUsers db = mock(SQLiteTableUsers.class);
        List<User> expectedUserList = new ArrayList<>();
        for (int i = 1; i < 5; i++) expectedUserList.add(this.users[i]);

        for (int i = 0; i < expectedUserList.size(); i++) db.insertData(expectedUserList.get(i));

        when(db.getUsers()).thenReturn(expectedUserList);

        Assert.assertEquals(db.getUsers(), expectedUserList);

    }

    @Test
    public void getUsersWithExistingUsernameTest() {

        TableUsers db = mock(SQLiteTableUsers.class);
        List<User> expectedUserList = new ArrayList<>();
        for (int i = 1; i < 5; i++) expectedUserList.add(this.users[i]);

        List<User> userList = expectedUserList.subList(0, expectedUserList.size());
        userList.add(this.users[7]);

        for (int i = 0; i < userList.size(); i++) db.insertData(userList.get(i));

        when(db.getUsers()).thenReturn(expectedUserList);

        Assert.assertEquals(db.getUsers(), expectedUserList);

    }

}