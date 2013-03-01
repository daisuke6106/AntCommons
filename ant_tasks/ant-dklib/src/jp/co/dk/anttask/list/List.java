package jp.co.dk.anttask.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.Task;

public class List extends Task{
	
	/** リスト識別ＩＤ */
	protected String id;
	
	/** リスト保持マップ */
	protected static Map<String, java.util.List<String>> listMap = new HashMap<String, java.util.List<String>>();
	
	public void setId(String id){
		this.id = id;
		if (List.listMap.get(this.id) == null) {
			List.listMap.put(this.id, new ArrayList<String>());
		}
	}
}
