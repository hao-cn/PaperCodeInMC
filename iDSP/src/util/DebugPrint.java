package util;

public enum DebugPrint {
	INSTANCE;
	public void print(boolean DEBUG,String debugS){
		if(DEBUG)
			System.out.println(debugS);
		System.out.println();
	}
}
