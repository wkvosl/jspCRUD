package board.util;

public class FileExt {

	public boolean checkEXT(String filename) {

		String[] extOK = { "jpg", "jpeg", "gif" };

		String[] filenameSplit = filename.split("\\.");

		for (int i = 0; i < filenameSplit.length; i++) {
			String ext = filenameSplit[filenameSplit.length - 1];
			if (!extOK[i].equals(ext)) {
				System.out.println("확장자가 아님.");
				return false;
			}
		}
		return true;
	}
	
	

}
