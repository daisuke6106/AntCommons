package jp.co.dk.anttask.file;

import java.text.ParseException;
import java.util.Date;

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
		
		// 指定のディレクトリにファイルが存在しない場合、削除がされいないこと。
		try {
			//ファイルを準備
			//更新日付が本日日付の０時０分０秒
			String day1 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 0));
			java.io.File file1 = createFileToTmpDir("test_" + day1 + "000000" + ".txt");
			setUpdateTimeToFile(file1, createDateByString(day1 + "000000"));
			//更新日付が本日日付の23時59分59秒
			java.io.File file2 = createFileToTmpDir("test_" + day1 + "235959" + ".txt");
			setUpdateTimeToFile(file2, createDateByString(day1 + "235959"));
			
			//更新日付が本日日付-1の０時０分０秒
			String day2 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 1));
			java.io.File file3 = createFileToTmpDir("test_" + day2 + "000000" + ".txt");
			setUpdateTimeToFile(file3, createDateByString(day2 + "000000"));
			//更新日付が本日日付-1の23時59分59秒
			java.io.File file4 = createFileToTmpDir("test_" + day2 + "235959" + ".txt");
			setUpdateTimeToFile(file4, createDateByString(day2 + "235959"));
			
			//更新日付が本日日付-2の０時０分０秒
			String day3 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 2));
			java.io.File file5 = createFileToTmpDir("test_" + day3 + "000000" + ".txt");
			setUpdateTimeToFile(file5, createDateByString(day3 + "000000"));
			//更新日付が本日日付-2の23時59分59秒
			java.io.File file6 = createFileToTmpDir("test_" + day3 + "235959" + ".txt");
			setUpdateTimeToFile(file6, createDateByString(day3 + "235959"));
			
			//更新日付が本日日付-3の０時０分０秒
			String day4 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 3));
			java.io.File file7 = createFileToTmpDir("test_" + day4 + "000000" + ".txt");
			setUpdateTimeToFile(file7, createDateByString(day4 + "000000"));
			//更新日付が本日日付-3の23時59分59秒
			java.io.File file8 = createFileToTmpDir("test_" + day4 + "235959" + ".txt");
			setUpdateTimeToFile(file8, createDateByString(day4 + "235959"));
			
			FileRotation fileRotation = new FileRotation();
			fileRotation.setDir(super.getTestTmpDir().getAbsolutePath());
			fileRotation.setFileName("test1");
			fileRotation.setDay("3");
			fileRotation.execute();
			
			if(!file1.exists())fail();
			if(!file2.exists())fail();
			if(!file3.exists())fail();
			if(!file4.exists())fail();
			if(!file5.exists())fail();
			if(!file6.exists())fail();
			if(!file7.exists())fail();
			if(!file8.exists())fail();
		}catch (BuildException | ParseException e) {
			fail(e);
		}
		
		// 指定のディレクトリにファイルが存在する場合、削除されること。
		try {
			//ファイルを準備
			//更新日付が本日日付の０時０分０秒
			String day1 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 0));
			java.io.File file1 = createFileToTmpDir("test_" + day1 + "000000" + ".txt");
			setUpdateTimeToFile(file1, createDateByString(day1 + "000000"));
			//更新日付が本日日付の23時59分59秒
			java.io.File file2 = createFileToTmpDir("test_" + day1 + "235959" + ".txt");
			setUpdateTimeToFile(file2, createDateByString(day1 + "235959"));
			
			//更新日付が本日日付-1の０時０分０秒
			String day2 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 1));
			java.io.File file3 = createFileToTmpDir("test_" + day2 + "000000" + ".txt");
			setUpdateTimeToFile(file3, createDateByString(day2 + "000000"));
			//更新日付が本日日付-1の23時59分59秒
			java.io.File file4 = createFileToTmpDir("test_" + day2 + "235959" + ".txt");
			setUpdateTimeToFile(file4, createDateByString(day2 + "235959"));
			
			//更新日付が本日日付-2の０時０分０秒
			String day3 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 2));
			java.io.File file5 = createFileToTmpDir("test_" + day3 + "000000" + ".txt");
			setUpdateTimeToFile(file5, createDateByString(day3 + "000000"));
			//更新日付が本日日付-2の23時59分59秒
			java.io.File file6 = createFileToTmpDir("test_" + day3 + "235959" + ".txt");
			setUpdateTimeToFile(file6, createDateByString(day3 + "235959"));
			
			//更新日付が本日日付-3の０時０分０秒
			String day4 = getStringByDate_YYYYMMDD(getBeforeDate(new Date(), 3));
			java.io.File file7 = createFileToTmpDir("test_" + day4 + "000000" + ".txt");
			setUpdateTimeToFile(file7, createDateByString(day4 + "000000"));
			//更新日付が本日日付-3の23時59分59秒
			java.io.File file8 = createFileToTmpDir("test_" + day4 + "235959" + ".txt");
			setUpdateTimeToFile(file8, createDateByString(day4 + "235959"));
			
			// ジョブ実行
			FileRotation fileRotation = new FileRotation();
			fileRotation.setDir(super.getTestTmpDir().getAbsolutePath());
			fileRotation.setFileName("test");
			fileRotation.setDay("3");
			fileRotation.execute();
			
			if(!file1.exists())fail();
			if(!file2.exists())fail();
			if(!file3.exists())fail();
			if(!file4.exists())fail();
			if(!file5.exists())fail();
			if(!file6.exists())fail();
			if(file7.exists())fail();
			if(file8.exists())fail();
			
		}catch (BuildException | ParseException e) {
			fail(e);
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
	
	@Test
	public void getBeforeDate() {
		// 1を渡した場合、１日前の日付オブジェクトが返却されること。
		FileRotation fileRotation = new FileRotation();
		try {
			Date newDate = fileRotation.getBeforeDate(super.createDateByString("20130101000000"), 1);
			assertEquals(super.getStringByDate_YYYYMMDD(newDate), "20121231");
		} catch (ParseException e) {
			fail(e);
		}
		
		// ０を渡した場合、０日前の日付オブジェクトが返却されること。
		try {
			Date newDate = fileRotation.getBeforeDate(super.createDateByString("20130101000000"), 0);
			assertEquals(super.getStringByDate_YYYYMMDD(newDate), "20130101");
		} catch (ParseException e) {
			fail(e);
		}
		
	}
}
