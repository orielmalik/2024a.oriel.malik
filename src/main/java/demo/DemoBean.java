package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DemoBean implements Demo {
	private String afekaDemoMessage;
	
	@Value("${afeka.demo.message:Default Message}")
	public void setAfekaDemoMmessage(String afekaDemoMessage) {
		this.afekaDemoMessage = afekaDemoMessage;
	}
	
	@PostConstruct
	public void init() {
		System.err.println("** The message configuration is set to: " + this.afekaDemoMessage);
	}
	
	@Override
	public String getAfekaDemoMessage() {
		return afekaDemoMessage;
	}
}
