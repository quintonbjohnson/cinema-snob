package project.github.cinemasnob.controller;

import android.content.Context;

import org.junit.Test;

import static org.junit.Assert.*;

import project.github.cinemasnob.model.Profile;


/**
 * Created by Maxwell on 4/4/2016.
 */
public class ProfileOpenHelperTest {

    private Context mockContext;
    @Test
    public void testPutProfile() throws Exception {
        ProfileOpenHelper profileDB = new ProfileOpenHelper(mockContext);
        boolean falseTest;
        boolean trueTest;

        //test null name
        falseTest = profileDB.putProfile(profileDB, null, "computer science", "soccer");
        assertFalse(falseTest);

        //test empty name
        falseTest = profileDB.putProfile(profileDB, "", "computer science", "soccer");
        assertFalse(falseTest);

        //test null major
        falseTest = profileDB.putProfile(profileDB, "Maxwell", null, "soccer");
        assertFalse(falseTest);

        //test null interests
        falseTest = profileDB.putProfile(profileDB, "Maxwell", "computer science", null);
        assertFalse(falseTest);

        // success scenarios
        try {
            trueTest = profileDB.putProfile(profileDB, "Maxwell", "cs", "soccer");
            assertTrue(trueTest);
        } catch (NullPointerException e) {
            System.out.println("Insert has a null input value.");
        }

        try {
            Profile testProfile = profileDB.getProfile(profileDB, "Maxwell");
            assertEquals(testProfile.getUsername(), "Maxwell");
            assertEquals(testProfile.getMajor(), "cs");
            assertEquals(testProfile.getInterests(), "soccer");
        } catch (NullPointerException e) {
            System.out.println("Insert has a null input value.");
        }
    }
}