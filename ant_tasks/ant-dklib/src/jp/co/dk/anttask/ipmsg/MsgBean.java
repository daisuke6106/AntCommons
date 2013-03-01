package jp.co.dk.anttask.ipmsg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.taskdefs.Property;

/**
 * IPメッセージ本文 Beanクラス
 * 
 * IPメッセンジャーのメッセージ本文に関連する情報を保持するBeanクラス
 * @author dvcskdkanno
 */
class MsgBean {
	
	/** メッセージ本文 */
	private String msg;
	
	/** メッセージファイルパス */
	private String msgFilePath;
	
	/** 置換文字リスト */
	private List<Property> replaceStr = new ArrayList<Property>();
	
	/** 改行文字 */
	private String newLineStr = "\r\n";
	
	/**
	 * メッセージ本文を返却します
	 * @return msg メッセージ本文
	 */
	String getMsg() {
		return msg;
	}
	
	/**
		* メッセージ本文を設定します
		* @param msg メッセージ本文
		*/
	void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * メッセージファイルパスを返却します
	 * @return msgFilePath メッセージファイルパス
	 */
	String getMsgFilePath() {
		return msgFilePath;
	}
	
	/**
	 * メッセージファイルパスを設定します
	 * @param msgFilePath メッセージファイルパス
	 * @throws IOException 指定されたパスが不正な場合
	 */
	void setMsgFilePath(String msgFilePath) throws IOException {
		// メッセージファイルパスを設定する
		this.msgFilePath = msgFilePath;
		
		// 指定されたファイルから文字を読込み、メッセージ本文へ設定する
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader( new FileReader( new File( msgFilePath ) ) );
		String line = null;
		while ( (line = br.readLine()) != null ) {
			sb.append(line);
			sb.append(this.newLineStr);
		}
		br.close();
		this.msg = sb.toString();
		
	}
	
	/**
	 * 置換文字リストを返却します
	 * @return replaceStr 置換文字リスト
	 */
	List<Property> getReplaceStr() {
		return replaceStr;
	}
	
	/**
	 * 置換文字リストを設定します
	 * @param replaceStr 置換文字リスト
	 */
	void addReplaceStr(Property replaceStr) {
		this.replaceStr.add(replaceStr);
	}
	
	/**
	 * 置換文字リストを設定します
	 * @param replaceStr 置換文字リスト
	 */
	void setReplaceStr(List<Property> replaceStr) {
		this.replaceStr = replaceStr;
	}
}