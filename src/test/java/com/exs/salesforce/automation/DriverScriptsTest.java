package com.exs.salesforce.automation;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DriverScriptsTest extends SFDCTestCases {

	@Test
	public static void mainMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {

		String dt_Path = "src/test/resources/executionScripts.xls";
		String[][] recdata = ReusableMethods.readSheet(dt_Path, "Sheet1");
		String testCase = null;
		executionReport("Sprint1");
		String flag = null;

		for (int i = 1; i < recdata.length; i++) {
			flag = recdata[i][0];
			if (flag.equalsIgnoreCase("Y")) {
				testCase = recdata[i][1];

				/* Java Reflection */
				Method testScript = SFDCTestCases.class.getMethod(testCase);
				testScript.invoke(testScript);

			} else {
				System.out.println("**********Row  number " + i + " test case Name " + recdata[i][1]
						+ " is not Executed*********");
			}

			extent.flush();
		}

	}
}
