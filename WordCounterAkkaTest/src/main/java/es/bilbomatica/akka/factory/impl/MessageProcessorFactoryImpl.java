package es.bilbomatica.akka.factory.impl;

import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.factory.MessageProcessorFactory;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;
import es.bilbomatica.akka.strategy.cases.impl.FileProcessedMessageProcessor;
import es.bilbomatica.akka.strategy.cases.impl.LineProcessedMessageProcessor;
import es.bilbomatica.akka.strategy.cases.impl.ProcessFileMessageProcessor;
import es.bilbomatica.akka.strategy.cases.impl.ProcessLineMessageProcessor;

public class MessageProcessorFactoryImpl implements MessageProcessorFactory {
	
	private static MessageProcessorFactoryImpl factoryInstance;
	
	private MessageProcessorFactoryImpl() {}
	
	public static final MessageProcessorFactory getInstance() {
		
		if (factoryInstance == null)
		{
			synchronized (MessageProcessorFactoryImpl.class)
			{
				if (factoryInstance == null)
				{
					factoryInstance = new MessageProcessorFactoryImpl();
				}
			}
		}
		
		return factoryInstance;
	}
	
	private static MessageProcessor fileProcessedMessageProcessor;
	private static MessageProcessor lineProcessedMessageProcessor;
	private static MessageProcessor processFileMessageProcessor;
	private static MessageProcessor processLineMessageProcessor;
	
	static
	{
		fileProcessedMessageProcessor = new FileProcessedMessageProcessor();
		lineProcessedMessageProcessor = new LineProcessedMessageProcessor();
		processFileMessageProcessor = new ProcessFileMessageProcessor();
		processLineMessageProcessor = new ProcessLineMessageProcessor();
	}

	@Override
	public MessageProcessor getMessageProcessor(ProcessingActor actor, Message message) {
		
		MessageProcessor messageProcessor = null;
		
		switch (actor.getActor())
		{
			case FILE_PROCESSING:
				
				switch (message.getMessageType())
				{
					case REQUEST:
						messageProcessor = processFileMessageProcessor;
					break;
					case RESPONSE:
						messageProcessor = lineProcessedMessageProcessor;
					break;
					default:
				}
			break;
			case LINE_PROCESSING:
				messageProcessor = processLineMessageProcessor;
			break;
			case COMPLETION_LISTENER:
				messageProcessor = fileProcessedMessageProcessor;
			break;
			default:
		}
		
		return messageProcessor;
	}

}
