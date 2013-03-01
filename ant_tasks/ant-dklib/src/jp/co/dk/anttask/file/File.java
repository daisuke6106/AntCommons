package jp.co.dk.anttask.file;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

class File  extends Task {
	
	/** ファイルタイプ */
	protected Type type;
	
	/**
	 * ファイル種別を設定する。
	 * 記載以外のパラメータは不正とし、BuildExceptionをthrowする
	 * @param type ファイル種別(ディレクトリ=d, ファイル=f)
	 */
	public void setType(String type){
		boolean find = false;
		
		for (Type t : Type.values()) {
			if (t.getType().equals(type)) {
				this.type = t;
				find = true;
			}
		}
		
		// 見つからなかった場合
		if (!find) {
			StringBuilder sb = new StringBuilder("The parameter is illegal.");
			sb.append("[").append("type=").append(type).append("]");
			throw new BuildException(sb.toString());
		}
		
	}
	
	@Override
	public void execute() throws BuildException {
		if (this.type == null) {
			throw new BuildException("please specify the Type");
		}
	}
}
