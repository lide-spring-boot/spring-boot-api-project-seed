package com.company.project.util.charging;

public class ClassInfo {
	public static String getInfo() {
		String clazz = Thread.currentThread() .getStackTrace()[2].getClassName();
		String method = Thread.currentThread().getStackTrace()[2].getMethodName();
		String msg = String.format("类[%s],方法[%s]::", clazz,method);
		return msg;
	}
}
