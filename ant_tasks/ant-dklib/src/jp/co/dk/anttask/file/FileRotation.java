package jp.co.dk.anttask.file;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * <pre>
 * Ant ファイルローテーション タスク<br/>
 * Antから指定のディレクトリに存在する一定の日数を越えて存在しているファイルを削除します。<br/>
 * ファイルの日数はファイル自身の最終更新日付を元に判定されます。<br/>
 * 
 * <h4>パラメータ</h4>
 * <table border="1" cellpadding="2" cellspacing="0">
 * 		<tr>
 * 			<th>
 * 				属性
 * 			</th>
 * 			<th>
 * 				説明
 * 			</th>
 * 			<th>
 * 				必須
 * 			</th>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				dir
 * 			</td>
 * 			<td>
 * 				ローテーション対象のディレクトリ
 * 			</td>
 * 			<td>
 * 				Yes
 * 			</td>
 * 		</tr>
 *		<tr>
 * 			<td>
 * 				filename
 * 			</td>
 * 			<td>
 * 				ローテーション対象のファイル名
 * 			</td>
 * 			<td>
 * 				Yes
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				day
 * 			</td>
 * 			<td>
 * 				ローテーション対象の日付
 * 			</td>
 * 			<td>
 * 				Yes
 * 			</td>
 * 		</tr>
 * </table>
 * <br/>
 * <h4>例</h4>
 * typedef 定義例：<br/>
 * &lt;typedef name="filerotation" classname="jp.co.dk.anttask.file.FileRotation" classpath="./file/lib"/&gt;<br/><br/>
 * 
 * 例1：<br/>
 * &lt;filerotation dir="/home/log" filename="example.log" day="7"/><br/>
 * 
 * </pre>
 * 
 * @author dkanno
 */
public class FileRotation  extends Task {
	
	/**
	 * ログローテーション対象のディレクトリを設定する。
	 * 
	 * @param dir ローテーション対象ディレクトリ
	 */
	public void setDir(String dir) {
		if (dir == null || dir.equals("")) throw new BuildException("target dir is not set."); 
	}
}
