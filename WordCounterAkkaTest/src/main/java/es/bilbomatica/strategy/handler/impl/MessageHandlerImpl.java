package es.bilbomatica.strategy.handler.impl;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.actors.CompletionListenerActor;
import es.bilbomatica.akka.actors.FileProcessingActor;
import es.bilbomatica.akka.actors.LineProcessingActor;
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
	public void handleMessage(UntypedActor actor, Message message) {
		
		if (actor instanceof FileProcessingActor)
		{
			FileProcessingActor fileProcessingActor = (FileProcessingActor) actor;
			
			if (message instanceof ProcessFileMessage)
			{
				processFileMessageProcessor.processMessage(fileProcessingActor, (ProcessFileMessage)message);
			}
			else if (message instanceof LineProcessedMessage)
			{
				lineProcessedMessageProcessor.processMessage(fileProcessingActor, (LineProcessedMessage)message);
			}
			else
			{
				actor.unhandled(message);
			}
		}
		else if (actor instanceof LineProcessingActor)
		{
			processLineMessageProcessor.processMessage((LineProcessingActor)actor, (ProcessLineMessage)message);
		}
		else if (actor instanceof CompletionListenerActor)
		{
			fileProcessedMessageProcessor.processMessage((CompletionListenerActor)actor, (FileProcessedMessage)message);
		}
		else
		{
			actor.unhandled(message);
		}
	}

}
