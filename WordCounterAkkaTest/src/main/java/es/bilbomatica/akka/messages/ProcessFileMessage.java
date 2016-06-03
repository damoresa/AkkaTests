package es.bilbomatica.akka.messages;

import es.bilbomatica.akka.messages.base.Message;

public class ProcessFileMessage implements Message {
	
	private static final long serialVersionUID = -4007460308836479904L;
	
	private String filePath;
	private String fileName;
	
	public ProcessFileMessage(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileName() {
		return fileName;
	}
}
