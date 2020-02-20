/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.internal.progress.ThreadSafeMockingProgress.mockingProgress;

import java.util.List;

import org.junit.Test;
import org.mockito.exceptions.misusing.NotAMockException;
import org.mockito.exceptions.misusing.NullInsteadOfMockException;
import org.mockito.internal.creation.MockSettingsImpl;

@SuppressWarnings("unchecked")
public class MockitoTest {

    //This test tests the Mock function which creates a process to be run in a multi-thread environment.
    //If mock is null, it returns fail to the test.
    @Test
    public void shouldRemoveStubbableFromProgressAfterStubbing() {
        List mock = Mockito.mock(List.class);
        Mockito.when(mock.add("test")).thenReturn(true);
        //TODO Consider to move to separate test
        assertThat(mockingProgress().pullOngoingStubbing()).isNull();
    }

    //if it is not a mock exception, it check whether it is a mock or not. Test to see if the object is NULL
    @SuppressWarnings({"CheckReturnValue", "MockitoUsage"})
    @Test(expected=NotAMockException.class)
    public void shouldValidateMockWhenVerifying() {
        Mockito.verify("notMock");
    }
    //This test check if the threads are all null or not
    @SuppressWarnings({"CheckReturnValue", "MockitoUsage"})
    @Test(expected=NotAMockException.class)
    public void shouldValidateMockWhenVerifyingWithExpectedNumberOfInvocations() {
        Mockito.verify("notMock", times(19));
    }

    //This test test if the thread if done or not, such as full list
    @Test(expected=NotAMockException.class)
    public void shouldValidateMockWhenVerifyingNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions("notMock");
    }

    //This test test if the thread has no action as a list, size 0
    @Test(expected=NotAMockException.class)
    public void shouldValidateMockWhenVerifyingZeroInteractions() {
        Mockito.verifyZeroInteractions("notMock");
    }

    //Test for an empty thread.
    @Test(expected=NotAMockException.class)
    public void shouldValidateMockWhenVerifyingNoInteractions() {
        Mockito.verifyNoInteractions("notMock");
    }

    //Test for null mock
    @Test(expected=NullInsteadOfMockException.class)
    public void shouldValidateNullMockWhenVerifyingNoInteractions() {
        Mockito.verifyNoInteractions(new Object[] { null });
    }

    //Test for multi-threads to be process inOrder
    @SuppressWarnings({"CheckReturnValue", "MockitoUsage"})
    @Test(expected=NotAMockException.class)
    public void shouldValidateMockWhenCreatingInOrderObject() {
        Mockito.inOrder("notMock");
    }

    //Test for default setting during mocking object. Such as some variables didnt get change when mocking.
    @Test
    public void shouldStartingMockSettingsContainDefaultBehavior() {
        //when
        MockSettingsImpl<?> settings = (MockSettingsImpl<?>) Mockito.withSettings();

        //then
        assertThat(Mockito.RETURNS_DEFAULTS).isEqualTo(settings.getDefaultAnswer());
    }

}
