/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.concurrentmockito;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockitousage.IMethods;
import org.mockitoutil.TestBase;

public class ThreadsShareAMockTest extends TestBase {

    private IMethods mock;

    //This test calls the performTest() in the class TestBase where a Thread is created, and it just calls
    //performTest 100 times to see if a thread exists and if there is no thread created. It throw exception.
    @Test
    public void shouldAllowVerifyingInThreads() throws Exception {
        for(int i = 0; i < 100; i++) {
            performTest();
        }
    }

    //Testing mock.simpleMethod, creating a thread contains the strings and testing verify if string is the same.
    private void performTest() throws InterruptedException {
        mock = mock(IMethods.class);
        final Thread[] listeners = new Thread[3];
        for (int i = 0; i < listeners.length; i++) {
            listeners[i] = new Thread() {
                @Override
                public void run() {
                    mock.simpleMethod("foo");
                }
            };
            listeners[i].start();
        }
        for (Thread listener : listeners) {
            listener.join();
        }
        verify(mock, times(listeners.length)).simpleMethod("foo");
    }
}
