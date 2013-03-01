package jp.co.dk.anttask.ipmsg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * IPメッセンジャー受取人 Beanクラス
 * IPメッセンジャー受取人を表すBeanクラス
 * @author dvcskdkanno
 */
class ReceiverBean {
	/** IPメッセンジャー 受取人一覧 */
	private List<String> receiverList = new ArrayList<String>();
	
	/** 受取人リスト記載ファイルパス */
	private String receiverListFile;
	
	/** 受取人リスト記載ファイル コメントアウト文字 */
	private String comment = "#";
	
	/** 受取人リスト 区切り文字 */
	private String toListsplitStr = ",";
	
	/**
	 * IPメッセンジャー 受取人一覧を返却します
	 * @return receiverBeanList　IPメッセンジャー 受取人一覧 
	 */
	List<String> getReceiverList() {
		return receiverList;
	}
	
	/**
	 * IPメッセンジャー 受取人一覧を設定します
	 * @param list IPメッセンジャー 受取人一覧
	 */
	void setReceiverList(List<String> receiverList) {
		this.receiverList = receiverList;
	}
	
	/**
	 * カンマで区切られた受取人のリストを設定します
	 * @param toList 受取人一覧のIPアドレス 例：192.168.1.1, 192.168.1.2
	 */
	void setReceiverList(String receiverList){
		
		// 一覧を区切り文字で区切る
		StringTokenizer st = new StringTokenizer(receiverList, this.toListsplitStr);
		
		// リストに設定する
		while (st.hasMoreTokens()) {
			this.receiverList.add(st.nextToken());
		}
	}
	
	/**
	 * 受取人リスト記載ファイルパスを返却します
	 * @return receiverListFile 受取人リスト記載ファイルパス
	 */
	String getReceiverListFile() {
		return receiverListFile;
	}
	
	/**
	 * 受取人リスト記載ファイルパスを設定し、ファイルパスからコメントアウト行を除く
	 * 読み込んだ行の一覧を保持します
	 * 
	 * @param receiverListFile 受取人リスト記載ファイルパス
	 * @throws IOException 指定されたパスが不正な場合
	 */
	void setReceiverListFile(String receiverListFile) throws IOException {
		
		// 受取人リスト記載ファイルパスを設定する
		this.receiverListFile = receiverListFile;
		
		// 指定されたファイルからコメント以外のファイルを読込み、IPメッセンジャー 受取人一覧に保持する
		BufferedReader br = new BufferedReader( new FileReader( new File( receiverListFile ) ) );
		String line = null;
		while ( (line = br.readLine() ) != null ) {
			if ( ! line.startsWith(comment) ){
				receiverList.add(line);
			}
		}
		br.close();
	}
	

}