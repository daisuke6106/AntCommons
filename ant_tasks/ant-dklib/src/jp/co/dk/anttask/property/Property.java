package jp.co.dk.anttask.property;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class Property extends Task{
	
	private String file;
	
	public void setFile(String file) {
		this.file = file;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws BuildException {
		try {
			Configuration config = new PropertiesConfiguration(this.file);
			
			Project project = this.getProject();
			
			for (Iterator<String> keys = config.getKeys(); keys.hasNext();) {
				String key = keys.next();
				project.setProperty(key, (String)config.getProperty(key));
			}
		} catch (ConfigurationException e) {
			throw new BuildException(e);
		}
	}
}
