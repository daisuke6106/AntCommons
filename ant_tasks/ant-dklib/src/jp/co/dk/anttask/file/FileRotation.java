package jp.co.dk.anttask.file;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * <pre>
 * Ant ファイルローテーション タスク<br/>
 * Antから指定のディレクトリに存在する一定の日数を越えて存在しているファイルを削除します。<br/>
 * ファイルは指定の名前を含む名称のもののみ削除対象とします。<br/>
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
	
	/** ディレクトリ */
	protected java.io.File dir;
	
	/** ファイル名 */
	protected String filename;
	
	/** 日付 */
	protected int day = -1;
	
	/**
	 * ファイルローテーション対象のディレクトリを設定する。<p/>
	 * 指定のパスが存在しない、またはパスがディレクトリをさしていなかった場合、例外を送出する。
	 * 
	 * @param dir ローテーション対象ディレクトリ
	 * @throws BuildException パスが不正な場合
	 */
	public void setDir(String dir) {
		if (dir == null || dir.equals("")) throw new BuildException("target dir is not set.");
		this.dir = new java.io.File(dir);
		if (!(this.dir.exists())) {
			StringBuilder sb = new StringBuilder("dir is not exist.[").append(dir).append("]");
			throw new BuildException(sb.toString());
		}
		if (! (this.dir.isDirectory())) {
			StringBuilder sb = new StringBuilder("dir is not directory.[").append(dir).append("]");
			throw new BuildException(sb.toString());
		}
	}
	
	/**
	 * ファイルローテーション対象のファイル名を設定する。<p/>
	 * 指定のパスが存在しない、またはパスがディレクトリをさしていなかった場合、例外を送出する。
	 * 
	 * @param dir ローテーション対象ディレクトリ
	 * @throws BuildException パスが不正な場合
	 */
	public void setFileName(String filename) {
		if (filename == null || filename.equals("")) throw new BuildException("filename is not set.");
		this.filename = filename;
	}
	
	/**
	 * ファイルローテーション対象の日付を設定する。<p/>
	 * 指定していなかった、またはパスがディレクトリをさしていなかった場合、例外を送出する。
	 * 
	 * @param dir ローテーション対象ディレクトリ
	 * @throws BuildException パスが不正な場合
	 */
	public void setDay(String day) {
		if (day == null || day.equals("")) throw new BuildException("day is not set.");
		try {
			this.day = Integer.parseInt(day);
		} catch (NumberFormatException e) {
			throw new BuildException("day is not number.[" + day + "]");
		}
		if (this.day < 1) throw new BuildException("Less than 1 is set to date.");
	}
	
	@Override
	public void execute() {
		if (this.dir      == null) throw new BuildException("dir is not set.");
		if (this.filename == null) throw new BuildException("filename is not set.");
		if (this.day < 0) throw new BuildException("day is not set.");
		java.io.File[] fileList = this.dir.listFiles();
		if (fileList == null || fileList.length == 0) return;
		
		String boforeDate = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), this.day));
		for (java.io.File file : fileList) {
			if (file.getName().indexOf(this.filename) != -1) {
				String fileDate = getStringByDate_YYYYMMDD(new Date(file.lastModified()));
				if (fileDate.compareTo(boforeDate)<=0) {
					file.delete();
				}
			}
		}
	}
	
	/**
	 * 指定の日付インスタンスから指定の日数を引いた日付インスタンスを返却する。
	 * 
	 * @param date 計算対象日付インスタンス
	 * @param dayCount 日数
	 * @return 計算結果日付インスタンス
	 */
	protected Date getBeforeDate(Date date, int dayCount) {
		long mday = (1000L * 60L * 60L * 24L) * (long)dayCount;
		long time = date.getTime();
		return new Date(time - mday);
	}
	
	/**
	 * 指定の日付インスタンスをYYYYMMDD形式の文字列に変換し、返却します。
	 * @param date 日付インスタンス
	 * @return YYYMMDD形式の文字列
	 */
	protected String getStringByDate_YYYYMMDD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date).toString();
	}
}
