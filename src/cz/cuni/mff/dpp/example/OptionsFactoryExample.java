package cz.cuni.mff.dpp.example;
import cz.cuni.mff.dpp.Options;
import cz.cuni.mff.dpp.OptionsFactory;


public class OptionsFactoryExample {
	
	
	public static void main(String[] args) {
		
		Options createOptions = OptionsFactory.createOptions(GnuTimeBean.class);
		
		System.out.println(createOptions);
		
		Options createOptions2 = OptionsFactory.createOptions(GnuTimeBean2.class);
		
		System.out.println(createOptions2);
		
	}
}