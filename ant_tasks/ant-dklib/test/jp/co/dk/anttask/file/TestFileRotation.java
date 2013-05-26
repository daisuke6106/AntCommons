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

}
