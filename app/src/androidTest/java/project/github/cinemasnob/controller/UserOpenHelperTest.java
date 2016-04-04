package project.github.cinemasnob.controller;

import android.content.Context;

import org.junit.Test;

import project.github.cinemasnob.model.User;

import static org.junit.Assert.*;

public class UserOpenHelperTest {

    private Context mockContext;
    private UserOpenHelper userDB;

    @Test
    public void testPutUser() throws Exception {
        userDB = new UserOpenHelper(mockContext);

        boolean fail;
        boolean succeed;

        //null username
        fail = userDB.putUser(userDB, null, "pass", "email@email.com", "major", false);
        assertFalse(fail);

        //blank username
        fail = userDB.putUser(userDB, "", "pass", "email@email.com", "major", false);
        assertFalse(fail);

        //null password
        fail = userDB.putUser(userDB, "username", null, "email@email.com", "major", false);
        assertFalse(fail);

        //blank password
        fail = userDB.putUser(userDB, "username", "", "email@email.com", "major", false);
        assertFalse(fail);

        //null email
        fail = userDB.putUser(userDB, "username", "pass", null, "major", false);
        assertFalse(fail);

        //blank email
        fail = userDB.putUser(userDB, "username", "pass", "", "major", false);
        assertFalse(fail);

        // success scenarios
        try {
            succeed = userDB.putUser(userDB, "username", "pass",
                    "email@email.com", "major", false);
            assertTrue(succeed);
        } catch (NullPointerException e) {
            System.out.println("Insert uses a null input value.");
        }

        try {
            User testUser =
                    userDB.getUser(userDB, "username");
            assertEquals(testUser.getUserName(), "username");
            assertEquals(testUser.getPassword(), "password");
            assertEquals(testUser.getEmail(), "email@email.com");
            assertEquals(testUser.getMajor(), "major");
            assertEquals(testUser.getBanStatus(), false);
        } catch (NullPointerException e) {
            System.out.println("Insert uses a null input value.");
        }
    }
}