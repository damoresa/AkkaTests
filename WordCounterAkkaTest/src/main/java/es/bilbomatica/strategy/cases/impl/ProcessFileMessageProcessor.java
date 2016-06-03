package es.bilbomatica.strategy.cases.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import es.bilbomatica.akka.actors.FileProcessingActor;
import es.bilbomatica.akka.exception.InitProcessException;
import es.bilbomatica.akka.messages.ProcessFileMessage;
import es.bilbomatica.akka.messages.ProcessLineMessage;
import es.bilbomatica.strategy.cases.MessageProcessor;

public class ProcessFileMessageProcessor implements MessageProcessor<FileProcessingActor, ProcessFileMessage> {

	@Override
	public void processMessage(FileProcessingActor actor, ProcessFileMessage message) {
		
		if (!actor.isRunning())
		{
			actor.setRunning(true);
			
			ProcessFileMessage processFile = (ProcessFileMessage) message;
			
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
					actor.increaseNoLines();
					
					actor.getWorkerRouter().tell(new ProcessLineMessage(readLine), actor.getSelf());
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
