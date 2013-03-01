package jp.co.dk.anttask.list;

import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;

public class ListEach extends List implements TaskContainer{
	
	/** プロパティ接続子 */
	private String prefix = "listeach." ;
	
	/** プロパティ名：文字列 */
	private String str = "str" ;
	
	/** ネストタスク一覧 */
	private java.util.List<Task> nestTask = new ArrayList<Task>(); 
	
	/**
	 * 接続子設定 
	 * 接続子を設定する
	 * @param prefix 接続子
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * タスク設定
	 * ネストされたタスクを設定する
	 * @param task タスク
	 */
	@Override
	public void addTask(Task task) {
		this.nestTask.add(task);
	}
	
	/**
	 * 指定文字列一覧コピー生成
	 * 指定された文字列一覧のコピーを作成し、新しいIDで保持する
	 */
	@Override
	public void execute() throws BuildException {
		java.util.List<String> list = ListEach.listMap.get(this.id);
		for (String str : list) {
			this.getProject().setProperty(this.prefix + this.str, str);
			for (Task task : nestTask) {
				task.perform();
			}
		}
	}
	
}
