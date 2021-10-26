package com.feeling.batch.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<String> getFilePathList(String folderpath) {
		List<String> list;
		File dir;
		File[] files;
		try {
			dir = new File(folderpath);
			files = dir.listFiles();
			list = new ArrayList<String>();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String filePathName = files[i].getPath();
					if (!files[i].isDirectory()) {
						list.add(filePathName);
					} else {
						System.out.println("this is Directory");
					}
				}
				return list;
			}
		} catch (Exception e) {
			System.out.println("FileUtil .getFilePathList :" + e);
		}
		return null;
	}

	public static String[] readAllFiles(String folderpath) {
		File file;
		String[] filelist = null;
		try {
			file = new File(folderpath);
			if (!file.isDirectory()) {
				System.out.println("FileUtil .readAllFiles: 不是目录！！");
			} else if (file.isDirectory()) {
				filelist = file.list();
			}
		} catch (Exception e) {
			System.out.println("FileUtil .readAllFiles: " + e);
		}
		return filelist;
	}

}
