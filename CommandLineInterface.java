package sbw.project.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sbw.architecture.support.Assert;
import sbw.project.cli.action.ActionSet;
import sbw.project.cli.action.MapActuator;
import sbw.project.cli.parser.CommandParser;
//import sbw.project.cli.parser.Parser;
import sbw.project.cli.parser.ParseException;

//=============================================================================================================================================================
/**
 * Defines the command-line interface. CS 350 will modify this class.
 * 
 * @author Dan Tappan [31.10.13]
 */
public class CommandLineInterface
{
   /** the action set to which commands are mapped for execution */
   private final ActionSet _actionSet = new ActionSet(this);

   /** whether the build sequence has been committed */
   private boolean _isCommitted = false;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a command-line interface.
    */
   public CommandLineInterface()
   {
      System.err.println("*** ACCESSING YOUR COMMAND LINE INTERFACE ***\n"); // <CS350> leave this here as a reality check
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Commits the build sequence.
    */
   public void commit() // <CS350> call this method from your commit command in CommandParser
   {
      _isCommitted = true;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets input from the keyboard.
    */
   private void elicitInput()
   {
      System.out.print("> ");

      BufferedReader instream = new BufferedReader(new InputStreamReader(System.in));

      try
      {
         String command = instream.readLine().trim();

         processInput(command);
      }
      catch (IOException exception)
      {
         exception.printStackTrace();
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Loops over keyboard inputs one command at a time.
    */
   public void execute()
   {
      while (true)
      {
         elicitInput();
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the action set to which commands are mapped for execution.
    * 
    * @return the action set.
    */
   public ActionSet getActionSet()
   {
      return _actionSet;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets whether the build sequence has been committed.
    * 
    * @return the result
    */
   public boolean isCommitted()
   {
      return _isCommitted;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Parses and executes a command.
    * 
    * @param command - the command
    */
   public void processInput(final String command)
   {
      Assert.nonnull(command);

      if (!command.isEmpty())
      {
         // <CS350> replace the code in this block with a call to your parser called CommandParser, however you are doing this. Be sure to supply it with
         // _actionSet and command with this signature. Do not change it because it allows you to turn on my parser later
          CommandParser parser = new CommandParser(_actionSet, command);   	  
	  
    	  
//         Parser Parser = new Parser(_actionSet, command);
//    	  
//         try
//         {
//            parser.parse();
//         }
//         catch (ParseException exception)
//         {
//            System.err.println("invalid command: " + exception);
//
//            exception.printStackTrace();
//         }
         // </CS350>
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Writes an output string to the command line.
    * 
    * @param output - the output
    */
   public void processOutput(final String output)
   {
      Assert.nonnull(output);

      System.out.println(": " + output);
   }
}
