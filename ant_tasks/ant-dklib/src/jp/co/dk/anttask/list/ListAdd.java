package jp.co.dk.anttask.list;

import org.apache.tools.ant.BuildException;

public class ListAdd extends List{
	
	/** 追加文字列 */
	private String addStr ;
	
	/**
	 * 追加文字設定
	 * 指定された追加文字を設定します
	 * @param add 追加文字列
	 */
	public void setAdd(String add) {
		this.addStr = add;
	}
	
	/**
	 * 指定文字列追加
	 * 指定されたリストに指定文字列を追加する
	 */
	@Override
	public void execute() throws BuildException {
		java.util.List<String> list = ListAdd.listMap.get(this.id);
		list.add(this.addStr);
	}
}
