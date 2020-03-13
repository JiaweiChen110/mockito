/*
 * Copyright (c) 2017 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.verification;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.FriendlyReminderException;

public class NegativeDurationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    //this tests the Mockito function timeout when the input is -1, and it should throw an null exception
    @Test
    public void should_throw_exception_when_duration_is_negative_for_timeout_method() {
        expectedException.expect(FriendlyReminderException.class);
        expectedException.expectMessage("Don't panic! I'm just a friendly reminder!");
        Mockito.timeout(-1);
    }

    //This test tests the timeout method of mockito with input -1, but
    //the timeout is only called after performing a method. Which as a resolve, it throws exception
    //because of timeout negative value.
    @Test
    public void should_throw_exception_when_duration_is_negative_for_after_method() {
        expectedException.expect(FriendlyReminderException.class);
        expectedException.expectMessage("Don't panic! I'm just a friendly reminder!");
        Mockito.after(-1);
    }
}
