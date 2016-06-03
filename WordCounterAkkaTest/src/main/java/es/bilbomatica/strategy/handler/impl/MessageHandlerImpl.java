package es.bilbomatica.strategy.handler.impl;

import es.bilbomatica.akka.actors.CompletionListenerActor;
import es.bilbomatica.akka.actors.FileProcessingActor;
import es.bilbomatica.akka.actors.LineProcessingActor;
import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.FileProcessedMessage;
import es.bilbomatica.akka.messages.LineProcessedMessage;
import es.bilbomatica.akka.messages.ProcessFileMessage;
import es.bilbomatica.akka.messages.ProcessLineMessage;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.strategy.cases.impl.FileProcessedMessageProcessor;
import es.bilbomatica.strategy.cases.impl.LineProcessedMessageProcessor;
import es.bilbomatica.strategy.cases.impl.ProcessFileMessageProcessor;
import es.bilbomatica.strategy.cases.impl.ProcessLineMessageProcessor;
import es.bilbomatica.strategy.handler.MessageHandler;

public class MessageHandlerImpl implements MessageHandler {
	
	private static volatile MessageHandler handlerInstance;
	
	private MessageHandlerImpl() {}
	
	public static final MessageHandler getInstance() {
		
		if (handlerInstance == null)
		{
			synchronized (MessageHandlerImpl.class)
			{
				if (handlerInstance == null)
				{
					handlerInstance = new MessageHandlerImpl();
				}
			}
		}
		
		return handlerInstance;
	}
	
	private final FileProcessedMessageProcessor fileProcessedMessageProcessor = new FileProcessedMessageProcessor();
	private final LineProcessedMessageProcessor lineProcessedMessageProcessor = new LineProcessedMessageProcessor();
	private final ProcessFileMessageProcessor processFileMessageProcessor = new ProcessFileMessageProcessor();
	private final ProcessLineMessageProcessor processLineMessageProcessor = new ProcessLineMessageProcessor();
	
	@Override
	public void handleMessage(ProcessingActor actor, Message message) {
		
		switch (actor.getActor())
		{
			case FILE_PROCESSING:
				FileProcessingActor fileProcessingActor = (FileProcessingActor) actor;
				
				switch (message.getMessageType())
				{
					case REQUEST:
						processFileMessageProcessor.processMessage(fileProcessingActor, (ProcessFileMessage)message);
					break;
					case RESPONSE:
						lineProcessedMessageProcessor.processMessage(fileProcessingActor, (LineProcessedMessage)message);
					break;
					default:
						actor.unhandled(message);
				}
			break;
			case LINE_PROCESSING:
				processLineMessageProcessor.processMessage((LineProcessingActor)actor, (ProcessLineMessage)message);
			break;
			case COMPLETION_LISTENER:
				fileProcessedMessageProcessor.processMessage((CompletionListenerActor)actor, (FileProcessedMessage)message);
			break;
			default:
				actor.unhandled(message);
		}
	}

}
