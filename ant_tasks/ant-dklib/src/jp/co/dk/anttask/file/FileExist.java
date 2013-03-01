package jp.co.dk.anttask.file;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Sequential;

/**
 * <pre>
 * ファイル存在チェック タスク<br/>
 * 指定されたファイル、又はディレクトリが存在する場合に指定されたタスクを実行する。
 * <br/>
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
 * 				type
 * 			</td>
 * 			<td>
 * 				ファイル種別の指定<br/>
 * 				ディレクトリ=d, ファイル=f
 * 			</td>
 * 			<td>
 * 				yes
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				file
 * 			</td>
 * 			<td>
 * 				存在チェック対象のファイル、ディレクトリ
 * 			</td>
 * 			<td>
 * 				yes
 * 			</td>
 * 		</tr>
 * </table>
 * <h4>ネストパラメータ</h4>
 * <table border="1" cellpadding="2" cellspacing="0">
 * 	<tr>
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
 * 		<td>
 * 			true
 * 		</td>
 * 		<td>
 * 			ファイル、ディレクトリが存在する場合、ネストされたタスクを実行する
 * 		</td>
 * 		<td>
 * 			No
 * 		</td>
 * 		</tr>
 * 		<tr>
 * 		<td>
 * 			false
 * 		</td>
 * 		<td>
 * 			ファイル、ディレクトリが存在しない場合、ネストされたタスクを実行する
 * 		</td>
 * 		<td>
 * 			No
 * 		</td>
 * 		</tr>
 * 	</tr>
 * </table>
 * <br/>
 * <h4>例</h4>
 * typedef 定義例：<br/>
 * &lt;typedef name="fileexist" classname="jp.co.dk.anttask.file.FileExist" classpath="./file/lib"/&gt;<br/><br/>
 * 
 * 例1：<br/>
 * &lt;fileexist type="f" file="C:\example.txt"  &gt;
 *     &lt;true&gt;
 *         &lt;echo message="true" /&gt;
 *     &lt;/true&gt;
 *     &lt;false&gt;
 *         &lt;echo message="false" /&gt;
 *     &lt;/false&gt;
 * &lt;/fileexist&gt;<br/>
 * 
 * ファイルが存在する場合、trueが表示され、存在しない場合はfalseが表示される。
 * 
 * </pre>
 * 
 * @author dkanno
 */
public class FileExist extends File{
	
	/** ファイル名 */
	private String file;
	
	/** ファイルオブジェクト */
	private java.io.File fileObj;
	
	/** true時のタスク */
	private Sequential trueTask ;
	
	/** false時のタスク */
	private Sequential falseTask ;
	
	/**
	 * チェック対象のファイルを設定
	 * @param file ファイル名
	 */
	public void setFile(String file) {
		this.file = file;
		this.fileObj = new java.io.File(this.file);
	}
	
	/**
	 * True時実行タスク
	 * チェック結果がTrueの場合の実施される実行タスクを設定する
	 * @param trueTask True時実行タスク
	 */
	public void addTrue(Sequential trueTask) {
		if (this.trueTask!=null) {
			throw new BuildException("You can not define more than one true");
		}
		this.trueTask = trueTask;
	}
	
	/**
	 * False時実行タスク
	 * チェック結果がFalseの場合の実施される実行タスクを設定する
	 * @param trueFalse True時実行タスク
	 */
	public void addFalse(Sequential falseTask) {
		if (this.falseTask!=null) {
			throw new BuildException("You can not define more than one false");
		}
		this.falseTask = falseTask;
	}
	
	/**
	 * ファイル存在チェック実行
	 * 指定されたファイル、又はディレクトリが存在する場合に指定されたタスクを実行する。
	 */
	@Override
	public void execute() throws BuildException {
		
		super.execute();
		
		if (Type.FILE == this.type) {
			if (this.fileObj.isFile() && this.fileObj.exists() && this.trueTask!=null) {
				this.trueTask.execute();
			} else if (this.fileObj.isFile() && !this.fileObj.exists() && this.falseTask!=null) {
				this.falseTask.execute();
			}
			
		} else if (Type.DIRECTORY == this.type) {
			if (this.fileObj.isDirectory() && this.fileObj.exists() && this.trueTask!=null) {
				this.trueTask.execute();
			} else if (this.fileObj.isDirectory() && !this.fileObj.exists() && this.falseTask!=null) {
				this.falseTask.execute();
			}
		}
		
	}

	
}
