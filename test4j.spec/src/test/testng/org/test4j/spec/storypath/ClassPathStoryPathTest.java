package org.test4j.spec.storypath;

import java.util.List;

import mockit.Mock;

import org.test4j.spec.annotations.StoryFile;
import org.test4j.spec.annotations.StoryType;
import org.test4j.spec.inner.IScenario;
import org.test4j.testng.JSpec;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

public class ClassPathStoryPathTest extends Test4J {
    @Test(groups = "jspec")
    public void testGetStory() {
        StoryPath path = new ClassPathStoryPath(SpecDemo.class);
        new MockUp<ClassPathStoryPath>() {
            @Mock
            public String getStoryFile(StoryType type, StoryFile story) {
                return "org/test4j/spec/scenario/TxtJSpecScenarioTest.testParseSpecScenarioFrom.story";
            }
        };

        new MockUp<StoryPath>() {
            @Mock
            public StoryType getStoryType(StoryFile story) {
                return StoryType.TXT;
            }
        };
        want.object(path).propertyEq("path", "org/test4j/spec/storypath").propertyEq("name", "SpecDemo");
        List<IScenario> list = path.getStory(null, null).getScenarios();
        want.list(list).sizeEq(2);
    }

    @Test
    public void testGetStoryFile() {
        StoryFile story = SpecDemo.class.getAnnotation(StoryFile.class);
        new MockUp<StoryFile>() {
            @Mock
            public String value() {
                return "/test.story";
            }
        };
        String file = new ClassPathStoryPath(SpecDemo.class).getStoryFile(null, story);
        want.string(file).isEqualTo("test.story");
    }

    @Test
    public void testGetStoryFile_IsNull() {
        String file = new ClassPathStoryPath(SpecDemo.class).getStoryFile(null, null);
        want.string(file).isEqualTo(SpecDemo.class.getName().replace('.', '/') + ".story");
    }
}

@StoryFile(value = "classpath:test.story")
class SpecDemo extends JSpec {
    @Override
    public void runScenario(IScenario scenario) throws Throwable {
        this.run(scenario);
    }
}
