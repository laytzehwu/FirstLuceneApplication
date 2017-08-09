# First Lucene Application

Nothing important. It is a backup for the first try to build Lucene:
1. Lucene 6.6.0
2. Simple txt file scanning in folder and create document as below:
	1. file content
	2. file name
	3. path

## Settings
Check /src/main/java/com/arvato/scp/lucene/LuceneConstants.java
```
package com.arvato.scp.lucene;

public class LuceneConstants {
   public static final String CONTENTS = "contents";
   public static final String FILE_NAME = "filename";
   public static final String FILE_PATH = "filepath";
   public static final int MAX_SEARCH = 10;
   public static final String INDEX_DIR = "C:\\lucene-test\\index";
   public static final String DATA_DIR = "C:\\lucene-test\\data";
}
```
