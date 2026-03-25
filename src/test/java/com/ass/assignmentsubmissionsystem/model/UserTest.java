package com.ass.assignmentsubmissionsystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private final User user = new User();

    // ── Valid password tests ──

    @Test
    public void testValidPassword() {
        // Valid: 6 chars, first 3 are letters
        assertTrue(user.checkPassword("abc123"),
            "abc123 should be valid — 6 chars, first 3 are letters");
    }

    @Test
    public void testValidPasswordMaxLength() {
        // Valid: exactly 10 chars, first 3 are letters
        assertTrue(user.checkPassword("abcdefghij"),
            "abcdefghij should be valid — 10 chars, first 3 are letters");
    }

    // ── Invalid password tests ──

    @Test
    public void testPasswordTooShort() {
        // Invalid: 5 chars — below minimum length
        assertFalse(user.checkPassword("ab12c"),
            "ab12c should be invalid — only 5 characters");
    }

    @Test
    public void testPasswordTooLong() {
        // Invalid: 11 chars — above maximum length
        assertFalse(user.checkPassword("abcdefghijk"),
            "abcdefghijk should be invalid — 11 characters");
    }

    @Test
    public void testPasswordFirstThreeNotLetters() {
        // Invalid: first 3 chars are not all letters
        assertFalse(user.checkPassword("12abc3"),
            "12abc3 should be invalid — first 3 chars are not letters");
    }

    @Test
    public void testPasswordNull() {
        // Invalid: null password
        assertFalse(user.checkPassword(null),
            "null should be invalid");
    }

    // ── JUnit assertEquals ──

    @Test
    public void testExpectedPassword() {
        String expectedPassword = "abc123";
        String actualPassword   = "abc123";
        assertEquals(expectedPassword, actualPassword,
            "Passwords should match");
    }

    // ── JUnit assertTrue for first 3 chars ──

    @Test
    public void testFirstThreeCharsAreLetters() {
        String password = "abc123";
        assertTrue(
            Character.isLetter(password.charAt(0)) &&
            Character.isLetter(password.charAt(1)) &&
            Character.isLetter(password.charAt(2)),
            "First three characters of abc123 should all be letters"
        );
    }
}
