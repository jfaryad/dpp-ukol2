package cz.cuni.mff.dpp;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.SimpleOption;

/**
 * This class contains methods, which create Options objects from different
 * parameters
 * 
 * @author Tom
 * 
 */
public final class OptionsFactory {

	private OptionsFactory() {

	}

	public static Options createOptions(Class<?> beanClass) {
		return new AnnotatedBeanOptionsBuilder(beanClass).getOptionsBuilder();
	}

	private static final class AnnotatedBeanOptionsBuilder {

		private final OptionsBuilder optionsBuilder;

		private final Class<?> beanClass;

		private AnnotatedBeanOptionsBuilder(Class<?> beanClass) {
			this.beanClass = beanClass;
			this.optionsBuilder = new OptionsBuilder();

			build();

		}

		private void build() {

			addOptions();

		}

		private void addOptions() {

			Field[] fields = beanClass.getDeclaredFields();
			for (Field field : fields) {
				processAnnotations(field);
			}

			Method[] methods = beanClass.getMethods();
			for (Method method : methods) {
				processAnnotations(method);
			}

		}

		private void processAnnotations(AccessibleObject accessibleObject) {

			processSimpleOption(accessibleObject);
			processParameterOption(accessibleObject);

		}

		private SingleOptionBuilder processSimpleOption(AccessibleObject accessibleObject) {

			SimpleOption simpleOption = accessibleObject.getAnnotation(SimpleOption.class);

			if (simpleOption == null) {
				return null;
			}

			String optionNames[] = simpleOption.names();
			// todo - budeme to kontrolovat???
			checkOptionNames(optionNames);

			return optionsBuilder.addOption(optionNames).setArgumentObligation(OptionArgumentObligation.FORBIDDEN)
					.setDescription(simpleOption.description()).dependentOn(simpleOption.dependentOn())
					.incompatibleWith(simpleOption.incompatibleWith()).setRequired(false);

			// todo musime nastavit, kam se to vubec bude nastavovat :-D

		}

		private SingleOptionBuilder processParameterOption(AccessibleObject accessibleObject) {

			ParameterOption parameterOption = accessibleObject.getAnnotation(ParameterOption.class);

			if (parameterOption == null) {
				return null;
			}

			String[] optionNames = parameterOption.names();

			// otazka - budeme to kontrolovat???
			checkOptionNames(optionNames);

			return optionsBuilder.addOption(optionNames)
					.setArgumentObligation(translateOptionParameterRequired(parameterOption.parameterRequired()))
					.setDescription(parameterOption.description()).setArgumentName(parameterOption.argumentName())
					.dependentOn(parameterOption.dependentOn()).incompatibleWith(parameterOption.incompatibleWith())
					.setRequired(parameterOption.optionRequired());

		}

		private OptionArgumentObligation translateOptionParameterRequired(boolean parameterRequired) {
			return parameterRequired ? OptionArgumentObligation.REQUIRED : OptionArgumentObligation.OPTIONAL;
		}

		private void checkOptionNames(String[] optionNames) {

			for (String optionName : optionNames) {
				if (optionsBuilder.isExistsOption(optionName)) {
					throw new MissConfiguratedAnnotationException("There are at least two configurations for option '"
							+ optionName + "'");
				}
			}
		}

		private OptionsBuilder getOptionsBuilder() {
			return optionsBuilder;
		}
	}

	public static class MissConfiguratedAnnotationException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MissConfiguratedAnnotationException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MissConfiguratedAnnotationException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public MissConfiguratedAnnotationException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public MissConfiguratedAnnotationException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

	}

}
