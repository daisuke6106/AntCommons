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
		} catch(BuildException e) {}
		
		// 空文字が設定された場合、例外が送出されること
		try {
			fileRotation.setDir("");
			fail();
		} catch(BuildException e) {}
		
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
	}

}
