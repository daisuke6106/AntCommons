package jp.co.dk.anttask.list;

import org.apache.tools.ant.BuildException;

public class ListRemove extends List{
	
	/** 削除文字列 */
	private String removeStr ;
	
	/** 削除文字列 */
	public void setRemove(String remove) {
		this.removeStr = remove;
	}
	
	/**
	 * 指定文字列削除
	 * 指定されたリストから指定文字列を削除する
	 */
	@Override
	public void execute() throws BuildException {
		java.util.List<String> list = ListRemove.listMap.get(this.id);
		list.remove(this.removeStr);
	}
}
