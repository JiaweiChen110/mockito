/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.runners;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.mockito.internal.runners.InternalRunner;
import org.mockito.internal.util.ConsoleMockitoLogger;
import org.mockitoutil.TestBase;

public class ConsoleSpammingMockitoJUnitRunnerTest extends TestBase {

    private ConsoleSpammingMockitoJUnitRunner runner;

    private MockitoLoggerStub loggerStub;

    private RunNotifier notifier;

    @Before
    public void setup() throws InitializationError {
        loggerStub = new MockitoLoggerStub();
        notifier = new RunNotifier();
    }

    //TODO add sensible tests
    //Test for description, assertion to check if the description, string is the same as expectedDescription when calling
    //the method createSuiteDescription, which return a class Description with variable string. Pass if the string is correct.
    @Test
    public void shouldDelegateToGetDescription() throws Exception {
        //given
        final Description expectedDescription = Description.createSuiteDescription(this.getClass());
        runner = new ConsoleSpammingMockitoJUnitRunner(loggerStub, new InternalRunnerStub() {
            public Description getDescription() {
                return expectedDescription;
            }
        });

        //when
        Description description = runner.getDescription();

        //then
        assertEquals(expectedDescription, description);
    }

    public class MockitoLoggerStub extends ConsoleMockitoLogger {

        StringBuilder loggedInfo = new StringBuilder();

        public void log(Object what) {
            super.log(what);
            loggedInfo.append(what);
        }

        public String getLoggedInfo() {
            return loggedInfo.toString();
        }
    }

    static class InternalRunnerStub implements InternalRunner {

        public Description getDescription() {
            return null;
        }

        public void run(RunNotifier notifier) {
        }

        public void filter(Filter filter) throws NoTestsRemainException {
        }

    }
}
