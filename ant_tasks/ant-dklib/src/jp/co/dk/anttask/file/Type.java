package jp.co.dk.anttask.file;

enum Type {
	/** ファイル */
	FILE("f"),
	
	/** ディレクトリ */
	DIRECTORY("d");
	
	String type;
	
	Type(String type){
		this.type = type;
	}
	
	String getType(){
		return this.type;
	}
}
