/* Class: CSCI 340
 * Project: 1a-OS Shell
 * Name: Bernice Tran
 * Emplid: 15043327
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class shell 
{	
	public static void main(String args[]) throws InterruptedException, IOException 
	{
		Scanner reader = new Scanner(System.in);
		String command = "";
		while (true)
		{ 
			System.out.print("Bernice> ");
			command = reader.nextLine();
			
			if ((!(command.trim().isEmpty()))) //checks for spaces
			{
				//StringTokenizer st = new StringTokenizer(command);
				//commands(st);
				commands(command);
			}
		}
	}
	
	public static void commands(String command) throws IOException
	{
		
		
		String [] string = command.split(" "); //splits string where there is a space and saves it to array
		String input = string[0]; //saves the first element in the array to be used as a command 
		
		switch(input) 
		{
			case "clr": //clear command 
				try
				{
					final String os = System.getProperty("os.name");
		        
					if (os.contains("Windows")) //if Windows OS invoke 'cls' command
					{
						process("cls");
					}
					else //otherwise invoke 'clear' command
					{
						process("clear"); 
					}
				}
				catch (Exception e)
				{
					System.err.println("Unable to clear."); //if particular OS cannot support 'cls' or 'clear' command
				}
				break;
			case "echo": //echo command to echo user input on new line
				try
				{
					for (int i = 1; i < string.length; i++) 
					{
						System.out.print(string[i] + " "); //prints out each string besides string (command) in first index
					}
				} 
				catch (NullPointerException e)
				{
					System.err.println("Unable to echo.");
				}
				System.out.println();
				break;
		
			case "help": //help command to display user manual 
				try
				{
					BufferedReader in = new BufferedReader(new FileReader("readme")); //opens readme text file which contains user manual
					String line;
					while((line = in.readLine()) != null)
					{
						System.out.println(line);
					}
					in.close();
				}
				catch (IOException e)
				{
					System.err.println("The file does not exist or cannot be opened."); //displays error message if the file does not exist
				}																		//or cannot be opened
				break;
			case "pause": //pauses operation of shell until 'Enter' is pressed
				//Thread.sleep(2000);
				System.console().readPassword("");
				break;
			case "exit": //terminates program
				System.exit(0);
			default:
				//System.out.println("");
				try
				{
					process(input); //all other command line input interpreted as program invocation done by shell creating child processes
				}
				catch (Exception e) 
				{
					System.err.println("Invalid command."); //if not a valid command, display error message
				}
		}
	}
	
	//passes in string parameter and creates process
	//to be invoked by command line interpreter to execute command which allows invoking built in commands 
	public static void process(String args) throws IOException 
	{
		ProcessBuilder pb = new ProcessBuilder(args);
		Process process = pb.start();
		
		// obtain the input and output streams
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;
		while ( (line = br.readLine()) != null)
		{
			System.out.println(line);
		}

		br.close();
	}
	
}
	
	//Using stringtokenizer
	/*public static void commands(StringTokenizer st) throws InterruptedException, IOException
	{	
		switch (st.nextToken())
		{
			case "clr": 
				
				try
			    {
			        final String os = System.getProperty("os.name");
			        
			        if (os.contains("Windows"))
			        {
			            process("cls");
			        }
			        else
			        {
			        	process("clear");
			        }
			    }
			    catch (final Exception e)
			    {
			        //Handle any exceptions
			    }
				break;
			case "echo":	
				try
				{
					while (st.hasMoreTokens())
					{
						System.out.print(st.nextToken() + " ");
					}
				} 
				catch (NullPointerException e)
				{
					System.out.println("Invalid command.");
				}
				System.out.println();
				break;
			case "help":
				try
				{
					BufferedReader in = new BufferedReader(new FileReader("readme"));
					String line;
					while((line = in.readLine()) != null)
					{
					    System.out.println(line);
					}
					in.close();
				}
				catch (IOException e)
				{
					System.out.println("No such file found.");
				}
				break;
			case "pause":
				//Thread.sleep(2000);
				System.console().readPassword("");
				break;
			case "exit":
				System.exit(0);
			default:


				//System.out.println("Bernice> Invalid command.");			
		}
	}
	*/