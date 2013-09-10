package ext.test4j.hamcrest.collection;

import java.util.Collection;

import ext.test4j.hamcrest.Description;
import ext.test4j.hamcrest.Factory;
import ext.test4j.hamcrest.Matcher;
import ext.test4j.hamcrest.TypeSafeMatcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyCollection<E> extends TypeSafeMatcher<Collection<E>> {

	@Override
	public boolean matchesSafely(Collection<E> item) {
		return item.isEmpty();
	}

	@Override
	public void describeMismatchSafely(Collection<E> item, Description mismatchDescription) {
	  mismatchDescription.appendValue(item);
	}
	
  public void describeTo(Description description) {
		description.appendText("an empty collection");
	}

    /**
     * Matches an empty collection.
     */
	@Factory
	public static <E> Matcher<Collection<E>> empty() {
		return new IsEmptyCollection<E>();
	}
}