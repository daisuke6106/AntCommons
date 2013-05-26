package jp.co.dk.anttask.file;

import jp.co.dk.test.template.TestCaseTemplate;

import org.apache.tools.ant.BuildException;
import org.junit.Test;

public class TestFileRotation extends TestCaseTemplate {

	@Test
	public void setDir() {
		FileRotation fileRotation = new FileRotation();
		
		// null文字が設定された場合、例外が送出されること
		try {
			fileRotation.setDir(null);
			fail();
		} catch(BuildException e) {
			assertEquals(e.getMessage(), "target dir is not set.");
		}
		
		// 空文字が設定された場合、例外が送出されること
		try {
			fileRotation.setDir("");
			fail();
		} catch(BuildException e) {
			assertEquals(e.getMessage(), "target dir is not set.");
		}
		
		// 存在しないパスが設定された場合、例外が送出されること
		try {
			fileRotation.setDir(super.getFraudTestTmpDir().getAbsolutePath());
			fail();
		} catch(BuildException e) {
			assertEquals(e.getMessage(), "dir is not exist.[" + super.getFraudTestTmpDir().getAbsolutePath() + "]");
		}
		
		// 存在しないパスが設定された場合、例外が送出されること
		try {
			fileRotation.setDir(super.getTestTmpFile().getAbsolutePath());
			fail();
		} catch(BuildException e) {
			assertEquals(e.getMessage(), "dir is not directory.[" + super.getTestTmpFile().getAbsolutePath() + "]");
		}
		
		// 存在するディレクトリへのパスが設定された場合、正常に値が設定できること
		try {
			fileRotation.setDir(super.getTestTmpDir().getAbsolutePath());
			assertNotNull(fileRotation.dir);
		} catch(BuildException e) {
			fail(e);
		}
	}
	
	@Test
	public void setFileName() {
		FileRotation fileRotation = new FileRotation();
		
		// nullが設定された場合、例外が送出されること。
		try {
			fileRotation.setFileName(null);
		} catch(BuildException e) {
			assertEquals(e.getMessage(), "filename is not set.");
		}
		
		// 空文字が設定された場合、例外が送出されること。
		try {
			fileRotation.setFileName("");
		} catch(BuildException e) {
			assertEquals(e.getMessage(), "filename is not set.");
		}
		
		// 空文字以外が設定された場合、正常に値が保持されること
		try {
			fileRotation.setFileName("test");
			assertEquals(fileRotation.filename, "test");
		} catch(BuildException e) {
			fail(e);
		}
	}
	
	@Test
	public void setDay() {
		FileRotation fileRotation = new FileRotation();
		
		// nullが設定された場合、例外が送出されること。
		try {
			fileRotation.setDay(null);
		} catch (BuildException e) {
			assertEquals(e.getMessage(), "day is not set.");
		}
		
		// 空文字が設定された場合、例外が送出されること。
		try {
			fileRotation.setDay("");
		} catch (BuildException e) {
			assertEquals(e.getMessage(), "day is not set.");
		}
		
		// 数値以外が設定された場合、例外が送出されること。
		try {
			fileRotation.setDay("test");
		} catch (BuildException e) {
			assertEquals(e.getMessage(), "day is not number.[test]");
		}
		
		// 0が設定された場合、例外を送出すること
		try {
			fileRotation.setDay("0");
		} catch (BuildException e) {
			assertEquals(e.getMessage(), "Less than 1 is set to date.");
		}
		// マイナス値が設定された場合、例外を送出すること
		try {
			fileRotation.setDay("-1");
		} catch (BuildException e) {
			assertEquals(e.getMessage(), "Less than 1 is set to date.");
		}
		// 数値が設定された場合、正常に値が設定されること。
		try {
			fileRotation.setDay("1");
			assertEquals(fileRotation.day , 1);
		} catch (BuildException e) {
			fail(e);
		}
		
	}

	@Test
	public void execite() {
		// dirが設定されていない場合、例外を送出すること。
		try {
			FileRotation fileRotation = new FileRotation();
			fileRotation.execute();
			fail();
		}catch (BuildException e) {
			assertEquals(e.getMessage(), "dir is not set.");
		}
		
		// filenameが設定されていない場合、例外を送出すること。
		try {
			FileRotation fileRotation = new FileRotation();
			fileRotation.setDir(super.getTestTmpDir().getAbsolutePath());
			fileRotation.execute();
			fail();
		}catch (BuildException e) {
			assertEquals(e.getMessage(), "filename is not set.");
		}
		
		// dayが設定されていない場合、例外を送出すること。
		try {
			FileRotation fileRotation = new FileRotation();
			fileRotation.setDir(super.getTestTmpDir().getAbsolutePath());
			fileRotation.setFileName("test");
			fileRotation.execute();
			fail();
		}catch (BuildException e) {
			assertEquals(e.getMessage(), "day is not set.");
		}
		
		// 指定のディレクトリにファイルが存在しない場合、正常に処理が狩猟するこ。
		try {
			FileRotation fileRotation = new FileRotation();
			fileRotation.setDir(super.getTestTmpDir().getAbsolutePath());
			fileRotation.setFileName("test");
			fileRotation.setDay("7");
			fileRotation.execute();
		}catch (BuildException e) {
			fail(e);
		}
	}
}
