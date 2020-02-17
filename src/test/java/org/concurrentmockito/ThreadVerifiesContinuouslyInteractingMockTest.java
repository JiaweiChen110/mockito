/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.concurrentmockito;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mock;
import org.mockitousage.IMethods;
import org.mockitoutil.TestBase;

//this test exposes the problem most of the time
public class ThreadVerifiesContinuouslyInteractingMockTest extends TestBase {

    //Mock object, testing for depencies for the class IMethods
    @Mock private IMethods mock;

    //Testing the mock object to see if having more than 100 threads would crush the program or not.
    //If the running time exeed or can not handle at most 100 threads, throw exception.
    @Test
    public void shouldAllowVerifyingInThreads() throws Exception {
        for(int i = 0; i < 100; i++) {
            performTest();
        }
    }

    private void performTest() throws InterruptedException {
        mock.simpleMethod();
        final Thread[] listeners = new Thread[2];
        for (int i = 0; i < listeners.length; i++) {
            final int x = i;
            listeners[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(x * 10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    mock.simpleMethod();
                }
            };
            listeners[i].start();
        }

        verify(mock, atLeastOnce()).simpleMethod();

        for (Thread listener : listeners) {
            listener.join();
        }
    }
}
