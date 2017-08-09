package com.arvato.scp.lucene;

import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

	// @Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return false;
		}
		
		if (file.isHidden()) {
			return false;
		}
		
		return file.getName().toLowerCase().endsWith(".txt");
	}
}
