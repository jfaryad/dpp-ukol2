package cz.cuni.mff.dpp;

public interface OptionArgumentSetter<T> {
	
	
	/**
	 * Nastaví argument opsny
	 * @param target
	 * @param argument
	 */
	void setOptionArgument(Object target, String argument);
	
	ArgumentConverter<T> getArgumentConverter();
	
	

}
