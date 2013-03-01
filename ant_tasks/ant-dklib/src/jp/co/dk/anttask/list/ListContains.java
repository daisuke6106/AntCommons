package jp.co.dk.anttask.list;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Sequential;

public class ListContains extends List {
	
	/** 削除文字列 */
	private String has ;
	
	private Sequential trueTask;
	
	private Sequential falseTask;
	
	/** 追加文字列 */
	public void setHas(String has) {
		this.has = has;
	}
	
	public void addTrue(Sequential trueTask) {
		if (this.trueTask != null) {
			throw new BuildException("You can not define more than one true");
		}
		this.trueTask = trueTask;
	}
	
	public void addFalse(Sequential falseTask) {
		if (this.falseTask != null) {
			throw new BuildException("You can not define more than one false");
		}
		this.falseTask = falseTask;
	}
	
	/**
	 * 
	 */
	@Override
	public void execute() throws BuildException {
		java.util.List<String> list = ListContains.listMap.get(this.id);
		if (list.contains(this.has)) {
			this.execute();
		} else {
			this.execute();
		}
	}

}
