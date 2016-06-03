package es.bilbomatica.akka.messages;

import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.messages.base.MessageType;

public class ProcessFileMessage implements Message {
	
	private static final long serialVersionUID = -4007460308836479904L;
	
	private MessageType messageType = MessageType.REQUEST;
	
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
	
	@Override
	public MessageType getMessageType() {
		return messageType;
	}
}
