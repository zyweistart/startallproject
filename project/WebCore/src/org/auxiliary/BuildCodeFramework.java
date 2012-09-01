package org.auxiliary;

import java.io.IOException;


public class BuildCodeFramework {

	public static void main(String[] args) throws IOException{
		BuildCodeTemplates buildCodeTemplates=new BuildCodeTemplates("org/platform","file","directoryID");
		buildCodeTemplates.buildCodeFile();
	}
	
}