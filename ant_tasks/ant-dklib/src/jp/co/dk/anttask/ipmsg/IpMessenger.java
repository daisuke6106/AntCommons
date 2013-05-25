package jp.co.dk.anttask.ipmsg;

import java.io.IOException;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Property;

import tk.ipmsg.IPMessenger;

/**
 * <pre>
 * Ant IPメッセンジャー タスク<br/>
 * AntからIPメッセンジャーを送信します。<br/>
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
 * 				user
 * 			</td>
 * 			<td>
 * 				IPメッセンジャーのユーザ名を指定する
 * 			</td>
 * 			<td>
 * 				No
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				nicname
 * 			</td>
 * 			<td>
 * 				IPメッセンジャーのニックネームを指定する
 * 			</td>
 * 			<td>
 * 				No
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				groupname
 * 			</td>
 * 			<td>
 * 				IPメッセンジャーのグループ名を指定する
 * 			</td>
 * 			<td>
 * 				No
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				tolist
 * 			</td>
 * 			<td>
 * 				IPメッセンジャー送信先のIPアドレスをカンマ区切りで指定する。
 * 			</td>
 * 			<td>
 * 				No
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				tolistfile
 * 			</td>
 * 			<td>
 * 				設定されたファイルからIPアドレスを読込み、そこに定義されているすべてのIPへメッセージを送信する。<br/>
 * 				(先頭に#が付いている場合、その行はコメントとし無視する)
 * 			</td>
 * 			<td>
 * 				No
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				msg
 * 			</td>
 * 			<td>
 * 				IPメッセンジャーで送信される本文を指定する。
 * 			</td>
 * 			<td>
 * 				No
 * 			</td>
 * 		</tr>
 * 		<tr>
 * 			<td>
 * 				msgfile
 * 			</td>
 * 			<td>
 * 				設定されたファイルを読込み、IPメッセンジャー本文として送信する
 * 			</td>
 * 			<td>
 * 				No
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
 * 		<td>
 * 			property
 * 		</td>
 * 		<td>
 * 			メッセージ本文に記載されたkeyをvalueに置換する
 * 		</td>
 * 		<td>
 * 			No
 * 		</td>
 * 	</tr>
 * </table>
 * <br/>
 * <h4>例</h4>
 * typedef 定義例：<br/>
 * &lt;typedef name="ipmsg" classname="jp.co.dk.anttask.ipmsg.IpMessenger" classpath="./ipmsg/lib"/&gt;<br/><br/>
 * 
 * 例1：<br/>
 * &lt;ipmsg user="ユーザ名" nicname="ニックネーム" groupname="グループ名" tolist="192.168.1.1,192.168.1.2" msg="テスト"/&gt;<br/><br/>
 * 
 * 例2：
 * &lt;ipmsg user="ユーザ名" nicname="ニックネーム" groupname="グループ名" tolistfile="./iplist.txt" msgfile="./msg.txt"&gt;
 *     &lt;property name="msg1" value="メッセージ１"/&gt;
 *     &lt;property name="msg2" value="メッセージ２"/&gt;
 * &lt;/ipmsg&gt;
 * </pre>
 * 
 * @author dkanno
 */
public class IpMessenger extends Task {
	
	/** IPユーザ名 */
	private String userName;
	
	/** IPニックネーム */
	private String nicName;
	
	/** グループ名 */
	private String groupName;
	
	/** 受取人 Bean */
	private ReceiverBean receiver = new ReceiverBean();
	
	/** メッセージ本文Bean */
	private MsgBean msg = new MsgBean();
	
	/** IPメッセンジャーインスタンス */
	private static IPMessanger ipmsg;
	
	/**
	 * IPユーザ名を設定する
	 * @param userName IPユーザ名
	 */
	public void setUser(String userName) {
		this.userName = userName;
	}
	
	/**
	 * IPニックネームを設定する
	 * @param nicName IPニックネーム
	 */
	public void setNickName(String nicName) {
		this.nicName = nicName;
	}
	
	/**
	 * グループ名を設定する
	 * @param groupName グループ名
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	/**
	 * カンマで区切られた受取人のリストを設定します
	 * @param toList 受取人一覧のIPアドレス 例：192.168.1.1, 192.168.1.2
	 */
	public void setToList(String toList){
		this.receiver.setReceiverList(toList);
	}
	
	/**
	 * 受取人のリストが記載されているファイルを設定します
	 * 指定されたパスが不正、もしくは読込み不可能の場合、Antの例外を生成します。
	 */
	public void setToListFile(String toListFile){
		try {
			this.receiver.setReceiverListFile(toListFile);
		
		// ファイル読込みに失敗した場合
		} catch (IOException e) {
			// エラーメッセージを生成
			StringBuilder sb  = new StringBuilder("It failed in reading the receiver file. ");
			sb.append("receiverfile = ").append(msg.getMsgFilePath());
			throw new BuildException(sb.toString(), e);
		}
	}
	
	/**
	 * メッセージ本文を設定する
	 * @param msg メッセージ本文
	 */
	public void setMsg(String msg){
	 this.msg.setMsg(msg);
	}
	
	/**
	 * メッセージ本文が記載されているファイルを設定します
	 * 指定されたパスが不正、もしくは読込み不可能の場合、Antの例外を生成します。
	 */
	public void setMsgFile(String msgFile){
		try {
			this.msg.setMsgFilePath(msgFile);
			
		// ファイル読込みに失敗した場合
		} catch (IOException e) {
			
			// エラーメッセージを生成
			StringBuilder sb  = new StringBuilder("It failed in reading the message file. ");
			sb.append("messagefile = ").append(msg.getMsgFilePath());
			throw new BuildException(sb.toString(), e);
		}
	}
	
	/**
	 * 置換文字列設定
	 * ネストされたプロパティをメッセージの置換文字として保持します
	 * @return prop プロパティ
	 */
	public Property createProperty(){
		Property prop = new Property(); 
		this.msg.addReplaceStr(prop);
		return prop;
	}
	
	/**
	 * IPメッセンジャー送信
	 * 指定されたIPアドレスにメッセージを送信します
	 */
	@Override
	public void execute() throws BuildException {
		
		//受取人一覧を取得する
		List<String> toList = this.receiver.getReceiverList();
		
		//メッセージ本文を取得する
		String messageStr = this.msg.getMsg();
		
		if (ipmsg == null) {
			try {
				if (this.userName == null  || this.userName.equals("")) throw new BuildException("IP Messanger start fail. username is non");
				if (this.nicName == null   || this.nicName.equals("")) throw new BuildException("IP Messanger start fail. nicName is non");
				if (this.groupName == null || this.groupName.equals("")) throw new BuildException("IP Messanger start fail. groupName is non");
				ipmsg = new IPMessanger(this.userName, this.nicName, this.groupName, false);
			} catch (IOException e) {
				StringBuilder sb = new StringBuilder("IP Messanger start fail. ");
				throw new BuildException(sb.toString(), e);
			}
		}
		if (msg == null || msg.equals("")) throw new BuildException("Message is not set.");; 
		try {
			// メッセージ本文を送信する
			for (String ip : toList) {
				ipmsg.sendMsg(ip, messageStr, false);
			}
		// 送信に失敗した場合
		} catch (IOException e) {
			StringBuilder sb = new StringBuilder("It failed in the message sending. ");
			throw new BuildException(sb.toString(), e);
		}
		
	}
	

	/**
	 * IPメッセンジャー送信内部クラス
	 * IPメッセンジャーの送信を制御を行う内部クラスです
	 */
	class IPMessanger extends IPMessenger {
	
		/**
		 * コンストラクタ。
		 *
		 * @param userName ユーザ名
		 * @param nickName ニックネーム
		 * @param group    グループ名
		 * @param debug    デバッグモード
		 */
		IPMessanger (String userName,String nickName,String group,boolean debug) throws IOException {
			super(userName, nickName, group, debug);
		}
		
		/**
		 * メッセージ受信時にフックされるメソッドです。
		 * アプリケーション固有の処理を実装してください。
		 *
		 * @param host 送信者のホスト名
		 * @param user ユーザ名
		 * @param msg メッセージ
		 * @param lock 封書かどうか
		 */
		public void receiveMsg(String arg0, String arg1, String arg2, boolean arg3) {
			
		}
		
		/**
		 * メンバー追加時にフックされるメソッドです。
		 * アプリケーション固有の処理を実装してください。
		 *
		 * @param host
		 * @param nickName
		 * @param group
		 * @param addr
		 * @param absence
		 */
		public void addMember(String arg0, String arg1, String arg2, String arg3, int arg4) {
			
		}
		
		/**
		 * メンバー削除時にフックされるメソッドです。
		 * アプリケーション固有の処理を実装してください。
		 *
		 * @param host
		 */
		public void removeMember(String arg0) {
			
		}
		
		/**
		 * 封書メッセージの開封時にフックされるメソッドです。
		 * アプリケーション固有の処理を実装してください。
		 *
		 * @param host
		 * @param user
		 */
		public void openMsg(String arg0, String arg1) {
			
		}
		
		public void sendMsg(String host, String msg, boolean secret) throws IOException{
			super.sendMsg(host, msg, secret);
		}
	}
}