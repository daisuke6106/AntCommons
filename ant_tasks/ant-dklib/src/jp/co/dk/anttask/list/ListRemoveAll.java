package jp.co.dk.anttask.list;

import org.apache.tools.ant.BuildException;

public class ListRemoveAll extends List{
	
	/** 削除リスト識別子 */
	private String removeId ;
	
	/**
	 * 削除リスト設定
	 * IDに指定されたリストから指定文字列一覧を削除する
	 */
	public void setRemoveId(String removeId) {
		this.removeId = removeId;
	}
	
	/**
	 * 重複コレクション削除
	 * 指定のリストから、指定されたコレクションに含まれる要素をすべて削除します
	 */
	@Override
	public void execute() throws BuildException {
		// 削除対象一覧
		java.util.List<String> list = ListRemoveAll.listMap.get(this.id);
		
		// 削除元一覧
		java.util.List<String> rmlist = ListRemoveAll.listMap.get(this.removeId);
		
		// 削除を実行する
		list.removeAll(rmlist);
		
	}
}
