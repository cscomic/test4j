package org.test4j.tools.reflector;

import org.junit.Test;
import org.test4j.junit.Test4J;
import org.test4j.junit.annotations.DataFrom;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReflectorTest_NewInstance extends Test4J {

    @Test
    @DataFrom("instance_data")
    public void testNewInstance(Class claz) {
        Object instance = reflector.newInstance(claz);
        want.object(instance).notNull();
        String result = ((ISayHello) instance).getName();
        want.string(result).isNull();
    }

    public static Object[][] instance_data() {
        return new Object[][] { { NoDefaultConstructor.class },// <br>
                { ISayHello.class } // <br>
        };
    }

    @Test
    public void testPrivateConstruction() {
        Object instance = reflector.newInstance(PrivateConstructor.class);
        want.object(instance).notNull();
        String result = ((ISayHello) instance).getName();
        want.string(result).isEqualTo("construction");
    }

    @Test
    public void testNewInstance_AbstractClazz() {
        try {
            reflector.newInstance(AbstractClazz.class);
            want.fail();
        } catch (Exception e) {
            String message = e.getMessage();
            want.string(message).contains("unsupport").contains("abstract class");
        }
    }

    @Test
    public void zzz() {
        System.out.println("============");
    }
}

class NoDefaultConstructor implements ISayHello {
    private String name = "defualt";

    public NoDefaultConstructor(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

class PrivateConstructor implements ISayHello {
    private String name = "defualt";

    private PrivateConstructor() {
        this.name = "construction";
    }

    @Override
    public String getName() {
        return name;
    }
}

abstract class AbstractClazz implements ISayHello {
    private String name = "defualt";

    private AbstractClazz(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

interface ISayHello {
    public String getName();
}
