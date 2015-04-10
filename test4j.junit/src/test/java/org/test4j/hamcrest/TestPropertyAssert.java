package org.test4j.hamcrest;

import org.junit.Test;
import org.test4j.fortest.beans.Address;
import org.test4j.fortest.beans.User;
import org.test4j.junit.Test4J;

public class TestPropertyAssert extends Test4J {
    @Test
    public void assertObject() {
        User user = new User(1, "wu", "darui");
        user.setAddress(new Address("天堂路998号", "310012", "xxxxxx滨江大楼"));

        User actualUser = yourApi();
        want.object(actualUser).reflectionEq(user);
        want.object(actualUser).propertyEq("first", "wu").propertyEq("address.postcode", "310012")
                .propertyMatch("last", the.string().isEqualTo("darui"));

        want.object(actualUser).propertyEq(new String[] { "first", "last", "address.postcode" },
                new String[] { "wu", "darui", "310012" });
    }

    public static User yourApi() {
        User user = new User(1, "wu", "darui");
        user.setAddress(new Address("天堂路998号", "310012", "xxxxxx滨江大楼"));
        return user;
    }
}
