package jp.co.dk.anttask.list;

import java.util.ArrayList;

import org.apache.tools.ant.BuildException;

public class ListCopy extends List{
	
	/** 削除文字列 */
	private String newId ;
	
	/** 追加文字列 */
	public void setNewId(String newId) {
		this.newId = newId;
	}
	
	/**
	 * 指定文字列一覧コピー生成
	 * 指定された文字列一覧のコピーを作成し、新しいIDで保持する
	 */
	@Override
	public void execute() throws BuildException {
		java.util.List<String> list = ListCopy.listMap.get(this.id);
		ListCopy.listMap.put(this.newId, new ArrayList<String>(list));
		
	}
}
