package fr.ekinci.scopes;

/**
 * @author Gokan EKINCI
 */
public abstract class SomeValue {
	protected String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ".value: " + value;
	}
}
