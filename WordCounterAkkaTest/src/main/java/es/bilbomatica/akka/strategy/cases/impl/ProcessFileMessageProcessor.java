package es.bilbomatica.akka.strategy.cases.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import es.bilbomatica.akka.actors.FileProcessingActor;
import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.exception.InitProcessException;
import es.bilbomatica.akka.messages.ProcessFileMessage;
import es.bilbomatica.akka.messages.ProcessLineMessage;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;

public class ProcessFileMessageProcessor implements MessageProcessor {

	@Override
	public void processMessage(ProcessingActor actor, Message message) {
		
		FileProcessingActor fileProcessingActor = (FileProcessingActor) actor;
		ProcessFileMessage processFile = (ProcessFileMessage) message;
		
		if (!fileProcessingActor.isRunning())
		{
			fileProcessingActor.setRunning(true);
			
			File path = new File(processFile.getFilePath());
			File sourceFile = new File(processFile.getFilePath() + File.separator + processFile.getFileName());
			
			InitProcessException.assertTrue(path.isDirectory() && path.exists(), " ERROR: La ruta de directorios indicada no existe. ");
			InitProcessException.assertTrue(sourceFile.isFile() && sourceFile.exists(), " ERROR: El fichero fuente indicado no existe. ");
			
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(new FileReader(sourceFile));
			
				String readLine = null;
				
				do
				{
					readLine = reader.readLine();
					fileProcessingActor.increaseNoLines();
					
					fileProcessingActor.getWorkerRouter().tell(new ProcessLineMessage(readLine), fileProcessingActor.getSelf());
				}
				while (readLine != null);
			}
			catch (IOException ioEx)
			{
				throw new RuntimeException();
			}
			finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					}
					catch (IOException ioEx) {}
				}
			}
		}
		else
		{
			System.out.println(" # ERROR: Se ha intentado iniciar el actor de procesado de ficheros mientras se ejecutaba. ");
		}
	}

}
