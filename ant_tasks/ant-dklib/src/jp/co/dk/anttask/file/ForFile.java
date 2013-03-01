package jp.co.dk.anttask.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;

/**
 * <pre>
 * ファイル・ディレクトリループ タスク<br/>
 * 指定されたディレクトリにあるディレクリ、またはファイルの数だけループ処理を実行する。<br/>
 * また、ループ処理中には現在アクティブになっているファイル、またはディレクトリの様々な状態をプロパティとして使用することができる。<br/>
 * 使用できるプロパティは以下の"ループ時使用可能プロパティ"に記載する。<br/>
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
 * 				繰り返し対象のディレクトリ
 * 			</td>
 * 			<td>
 * 				yes
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				prefix
 * 			</td>
 * 			<td>
 * 				プロパティの接続子<br/>
 * 				指定しなかった場合、forfile.がデフォルトとなる。
 * 			</td>
 * 			<td>
 * 				no
 * 			</td>
 * 		</tr>
 * </table>
 * <br/>
 * 
 * <h4>ループ時使用可能プロパティ</h4>
 * <table border="1" cellpadding="2" cellspacing="0">
 * 	<tr>
 * 			<th>
 * 				プロパティ名
 * 			</th>
 * 			<th>
 * 				説明
 * 			</th>
 * 	</tr>
 * 	<tr>
 * 		<td>
 * 			canexecute
 * 		</td>
 * 		<td>
 * 			アプリケーションが実行できるかどうかを判定します
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			canread
 * 		</td>
 * 		<td>
 * 			アプリケーションが読み込めるかどうかを判定します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			canwrite
 * 		</td>
 * 		<td>
 * 			アプリケーションが変更できるかどうかを判定します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			exists
 * 		</td>
 * 		<td>
 * 			ファイルまたはディレクトリが存在するかどうかを判定します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			absolutepath
 * 		</td>
 * 		<td>
 * 			絶対パス名文字列を返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			canonicalpath
 * 		</td>
 * 		<td>
 * 			この抽象パス名の正規の形式を返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			freespace
 * 		</td>
 * 		<td>
 * 			指定されるパーティション内で未割り当てのバイト数を返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			parent
 * 		</td>
 * 		<td>
 * 			親のパス名文字列を返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			path
 * 		</td>
 * 		<td>
 * 			パス名文字列に変換します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			name
 * 		</td>
 * 		<td>
 * 			ファイルまたはディレクトリの名前を返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			totalspace
 * 		</td>
 * 		<td>
 * 			この抽象パス名で指定されるパーティションのサイズを返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			usablespace
 * 		</td>
 * 		<td>
 * 			この抽象パス名で指定されるパーティション上で、この仮想マシンが利用できるバイト数を返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			isabsolute
 * 		</td>
 * 		<td>
 * 			この抽象パス名が絶対かどうかを判定します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			isdirectory
 * 		</td>
 * 		<td>
 * 			この抽象パス名が示すファイルがディレクトリであるかどうかを判定します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			isfile
 * 		</td>
 * 		<td>
 * 			この抽象パス名が示すファイルが普通のファイルかどうかを判定します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			ishidden
 * 		</td>
 * 		<td>
 * 			この抽象パス名が示すファイルが隠しファイルかどうかを判定します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			lastmodified
 * 		</td>
 * 		<td>
 * 			この抽象パス名が示すファイルが最後に変更された時刻を返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			length
 * 		</td>
 * 		<td>
 * 			この抽象パス名に指定されているファイルの長さを返します。
 * 		</td>
 * 	</tr>
 * 
 * 	<tr>
 * 		<td>
 * 			list
 * 		</td>
 * 		<td>
 * 			この抽象パス名が示すディレクトリにあるファイルおよびディレクトリを示す文字列の配列を返します。
 * 		</td>
 * 	</tr>
 * 
 * </table>
 * <br/>
 * <h4>例</h4>
 * typedef 定義例：<br/>
 * &lt;typedef name="forfile" classname="jp.co.dk.anttask.file.ForFile" classpath="./file/lib"/&gt;<br/><br/>
 * 
 * 例1：<br/>
 * &lt;forfile type="f" file="C:\" prefix="forfileprefix."  &gt;
 *     &lt;echo message="${forfileprefix.name}" /&gt;
 * &lt;/forfile&gt;<br/>
 * 
 * fileに指定されたディレクトリにファイル<br/>
 * ・A.txt<br/>
 * ・B.txt<br/>
 * ・C.txt<br/>
 * 
 * がある場合、<br/>
 * [echo] A.txt<br/>
 * [echo] B.txt<br/>
 * [echo] C.txt<br/>
 * と出力される。
 * 
 * </pre>
 * 
 * @author dkanno
 */
public class ForFile extends File implements TaskContainer{
	
	/** ファイルパス */
	private String file;
	
	/** ファイルオブジェクト */
	private java.io.File fileObj;
	
	/** タスクリスト */
	private List<Task> nestTasks = new ArrayList<Task>();
	
	/** 区切り文字 */
	private String cepStr = ",";
	
	/** プロパティ接続子 */
	private String prefix = "forfile.";
	
	private String property_canexecute = "canexecute";
	private String property_canread = "canread";
	private String property_canwrite = "canwrite";
	private String property_exists = "exists";
	private String property_absolutepath = "absolutepath";
	private String property_canonicalpath = "canonicalpath";
	private String property_freespace = "freespace";
	private String property_parent = "parent";
	private String property_path = "path";
	private String property_name = "name";
	private String property_totalspace = "totalspace";
	private String property_usablespace = "usablespace";
	private String property_isabsolute = "isabsolute";
	private String property_isdirectory = "isdirectory";
	private String property_isfile = "isfile";
	private String property_ishidden = "ishidden";
	private String property_lastmodified = "lastmodified";
	private String property_length = "length";
	private String property_list = "list";
	
	/**
	 * コンストラクタ
	 * @param project プロジェクト
	 */
	public ForFile(Project project){
		this.setProject(project);
	}
	
	/**
	 * ファイルパスを設定する
	 * @param file
	 */
	public void setFile(String file){
		this.file = file;
		this.fileObj = new java.io.File(this.file);
		if (!fileObj.exists()) {
			StringBuilder sb = new StringBuilder("The parameter is illegal.");
			sb.append("[").append("file=").append(file).append("]");
			throw new BuildException(sb.toString());
		} else if (!fileObj.isDirectory()) {
			StringBuilder sb = new StringBuilder(".");
			sb.append("[").append("file=").append(file).append("]");
			throw new BuildException(sb.toString());
		}
	}
	
	/**
	 * 接続子を設定する。
	 * プロパティに設定する際の接続子を指定する。
	 * 省略した場合：forfile.
	 * @param prefix 接続子
	 */
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}
	
	
	/**
	 * タスク登録
	 * ネストされたタスクを登録する
	 * @param task タスク
	 */
	@Override
	public void addTask(Task task) {
		this.nestTasks.add(task);
	}
	
	/**
	 * ファイル一覧取得
	 * 
	 * fileに指定されたディレクトリからファイル(又は、ディレクトリ)を読み込み
	 * 区切り文字で区切った文字列をプロパティに設定する
	 * 
	 * 例：
	 * <forfile file="C:\" type="f"/>
	 * filelist=dir1,dir2
	 */
	@Override
	public void execute()throws BuildException{
		
		super.execute();
		
		java.io.File[] fileList = this.fileObj.listFiles();
		List<java.io.File> list = new ArrayList<java.io.File>();
		for (java.io.File f : fileList) {
			if (type == Type.FILE && f.isFile()) {
				list.add(f);
			}else if (type == Type.DIRECTORY && f.isDirectory()) {
				list.add(f);
			}
			
		}
		
		Project project = super.getProject();
		project.setProperty( prefix + property_list, this.toString(list) );
		
		try{
			for (java.io.File f : list) {
				project.setProperty( prefix + property_canexecute, Boolean.toString( f.canExecute() ) );
				project.setProperty( prefix + property_canread, Boolean.toString( f.canRead() ) );
				project.setProperty( prefix + property_canwrite, Boolean.toString( f.canWrite() ) );
				project.setProperty( prefix + property_exists, Boolean.toString( f.exists() ) );
				project.setProperty( prefix + property_absolutepath, f.getAbsolutePath() );
				project.setProperty( prefix + property_canonicalpath, f.getCanonicalPath() );
				project.setProperty( prefix + property_freespace, Long.toString(f.getFreeSpace()) );
				project.setProperty( prefix + property_parent, f.getParent() );
				project.setProperty( prefix + property_path, f.getPath() );
				project.setProperty( prefix + property_name, f.getName() );
				project.setProperty( prefix + property_totalspace, Long.toString(f.getTotalSpace()) );
				project.setProperty( prefix + property_usablespace, Long.toString(f.getUsableSpace()) );
				project.setProperty( prefix + property_isabsolute, Boolean.toString(f.isAbsolute()) );
				project.setProperty( prefix + property_isdirectory, Boolean.toString(f.isDirectory()) );
				project.setProperty( prefix + property_isfile, Boolean.toString(f.isFile()) );
				project.setProperty( prefix + property_ishidden, Boolean.toString(f.isHidden()) );
				project.setProperty( prefix + property_lastmodified, Long.toString(f.lastModified()) );
				project.setProperty( prefix + property_length, Long.toString(f.length()) );
				
				for (Task task : nestTasks) {
					task.perform();
				}
			}
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}
	
	/**
	 * ファイル一覧を区切り文字で区切った文字列を返却する
	 * @param list ファイル一覧
	 * @return ファイル一覧文字列
	 */
	private String toString(List<java.io.File> list){
		StringBuilder sb = new StringBuilder();
		for (java.io.File file : list) {
			sb.append(file.getName());
			sb.append(cepStr);
		}
		return sb.toString().substring(0, sb.length()-cepStr.length());
	}
	
}