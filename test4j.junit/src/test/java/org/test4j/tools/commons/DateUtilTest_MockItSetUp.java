package org.test4j.tools.commons;

import java.util.Calendar;
import java.util.Date;

import mockit.Mock;

import org.junit.Test;
import org.test4j.junit.Test4J;

public class DateUtilTest_MockItSetUp extends Test4J {
    public static class MockDateUtil extends MockUp<DateHelper> {
        @Mock
        public Date now() {
            Calendar cal = DateUtilTest.mockCalendar(2012, 1, 28);
            return cal.getTime();
        }
    }

    @Test
    public void testCurrDateTimeStr_format() {
        new MockDateUtil();
        String str = DateHelper.currDateTimeStr("MM/dd/yy hh:mm:ss");
        want.string(str).isEqualTo("01/28/12 07:58:55");
    }

    @Test(expected = AssertionError.class)
    public void testCurrDateTimeStr_format_Exception() {
        String str = DateHelper.currDateTimeStr("MM/dd/yy hh:mm:ss");
        want.string(str).isEqualTo("01/28/12 07:58:55");
    }

    @Test
    public void testCurrDateTimeStr_dynamicMock() {
        new Expectations(DateHelper.class) {
            {
                when(DateHelper.now()).thenReturn(DateUtilTest.mockCalendar(2014, 1, 28).getTime());
            }
        };
        String str = DateHelper.currDateTimeStr("MM/dd/yy hh:mm:ss");
        want.string(str).isEqualTo("01/28/14 07:58:55");
    }
}
