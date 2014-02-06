package sbw.project.cli.parser;

import sbw.project.cli.action.*;
import sbw.architecture.datatype.*;

// <CS350> replace this with your command parser and this constructor.

public class Parser
{
	public static void main(String[] args)
	{
		//Temporary String Command for testing, this is where the String that comes in will be placed.
		String command = "CREATE MAIN GEAR cat WITH SPEED 138 ACCELERATION 500";

		//This sets the delims to any space " ".
		String delims = "[ ]+";

		//Creates an array of Strings based on the delimiters chosen above, which is the " " space character.
		String[] tokens = command.split(delims);

		String id;
		double acceleration, angle1, angle2, percent, power, speed;
		int rate;


		try
		{
			if(tokens[0].compareToIgnoreCase("CREATE") == 0)	//CREATIONAL COMMANDS
			{
				if(tokens[1].compareToIgnoreCase("RUDDER") == 0)
				{
					if(isValid("id", tokens[2]))
						if(tokens[3].concat(tokens[4]).compareToIgnoreCase("WITHLIMIT") == 0)
						{
							angle1 = Double.parseDouble(tokens[5]);
							if(isValid("angle", angle1))
								if(tokens[6].compareToIgnoreCase("SPEED") == 0)
								{
									speed = Double.parseDouble(tokens[7]);
									if(isValid("speed", speed))
										if(tokens[8].compareToIgnoreCase("ACCELERATION") == 0)
										{
											acceleration = Double.parseDouble(tokens[9]);
											Object Identifier;
											if(isValid("acceleration", acceleration))
											{
												Identifier finalID = new Identifier(tokens[2]);
												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);

												doCreateRudder(finalID, finalAngle1, finalSpeed, finalAcceleration);
											}
											//System.out.println("Made it to RUDDER PRINT");//doCreateRudder()
										}
								}
						}
				}
				else if(tokens[1].compareToIgnoreCase("ELEVATOR") == 0)
				{
					if(isValid("id", tokens[2]))
						if(tokens[3].concat(tokens[4]).compareToIgnoreCase("WITHLIMIT") == 0)
						{
							angle1 = Double.parseDouble(tokens[5]);
							if(isValid("angle", angle1))
								if(tokens[6].compareToIgnoreCase("SPEED") == 0)
								{
									speed = Double.parseDouble(tokens[7]);
									if(isValid("speed", speed))
										if(tokens[8].compareToIgnoreCase("ACCELERATION") == 0)
										{
											acceleration = Double.parseDouble(tokens[9]);
											if(isValid("acceleration", acceleration))
											{
												Identifier finalID = new Identifier(tokens[2]);
												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);

												doCreateElevator(finalID, finalAngle1, finalSpeed, finalAcceleration);
											}
											//System.out.println("Made it to ELEVATOR PRINT");//doCreateElevator()
										}
								}
						}
				}
				else if(tokens[1].compareToIgnoreCase("AILERON") == 0)
				{
					if(isValid("id", tokens[2]))
						if(tokens[3].concat(tokens[4]).concat(tokens[5]).compareToIgnoreCase("WITHLIMITUP") == 0)
						{
							angle1 = Double.parseDouble(tokens[6]);
							if(isValid("angle", angle1))
								if(tokens[7].compareToIgnoreCase("DOWN") == 0)
								{
									angle2 = Double.parseDouble(tokens[8]);
									if(isValid("angle", angle2))

										if(tokens[9].compareToIgnoreCase("SPEED") == 0)
										{
											speed = Double.parseDouble(tokens[10]);
											if(isValid("speed", speed))
												if(tokens[11].compareToIgnoreCase("ACCELERATION") == 0)
												{
													acceleration = Double.parseDouble(tokens[12]);
													if(isValid("acceleration", acceleration))
													{
														Identifier finalID = new Identifier(tokens[2]);
														Angle finalAngle1 = new Angle(angle1);
														Angle finalAngle2 = new Angle(angle2);
														Speed finalSpeed = new Speed(speed);
														Acceleration finalAcceleration = new Acceleration(acceleration);

														doCreateAileron(finalID, finalAngle1, finalSpeed, finalAcceleration);
													}
													//System.out.println("Made it to AILERON PRINT");//doCreateAileron()
												}
										}
								}
						}
				}
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("SPLITFLAP") == 0)
				{
					if(isValid("id", tokens[3]))
						if(tokens[4].concat(tokens[5]).compareToIgnoreCase("WITHLIMIT") == 0)
						{
							angle1 = Double.parseDouble(tokens[6]);
							if(isValid("angle", angle1))
								if(tokens[7].compareToIgnoreCase("SPEED") == 0)
								{
									speed = Double.parseDouble(tokens[8]);
									if(isValid("speed", speed))
										if(tokens[9].compareToIgnoreCase("ACCELERATION") == 0)
										{
											acceleration = Double.parseDouble(tokens[10]);
											if(isValid("acceleration", acceleration))
											{
												Identifier finalID = new Identifier(tokens[2]);
												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);

												doCreateFlap(finalID, false, finalAngle1, finalSpeed, finalAcceleration);
											}
											//System.out.println("Made it to SPLITFLAP PRINT");//doCreateFlap()
										}
								}
						}
				}			
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("FOWLERFLAP") == 0)
				{
					if(isValid("id", tokens[3]))
						if(tokens[4].concat(tokens[5]).compareToIgnoreCase("WITHLIMIT") == 0)
						{
							angle1 = Double.parseDouble(tokens[6]);
							if(isValid("angle", angle1))
								if(tokens[7].compareToIgnoreCase("SPEED") == 0)
								{
									speed = Double.parseDouble(tokens[8]);
									if(isValid("speed", speed))
										if(tokens[9].compareToIgnoreCase("ACCELERATION") == 0)
										{
											acceleration = Double.parseDouble(tokens[10]);
											if(isValid("acceleration", acceleration))
											{
												Identifier finalID = new Identifier(tokens[2]);
												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);

												doCreateFlap(finalID, true, finalAngle1, finalSpeed, finalAcceleration);
											}
										}
									//System.out.println("Made it to FOWLERFLAP PRINT");//doCreateFlap()
								}
						}
				}
				else if(tokens[1].compareToIgnoreCase("ENGINE") == 0)
				{
					if(isValid("id", tokens[2]))
						if(tokens[3].concat(tokens[4]).compareToIgnoreCase("WITHSPEED") == 0)
						{
							speed = Double.parseDouble(tokens[5]);
							if(isValid("speed", speed))
								if(tokens[6].compareToIgnoreCase("ACCELERATION") == 0)
								{
									acceleration = Double.parseDouble(tokens[7]);
									if(isValid("acceleration", acceleration))
									{
										Identifier finalID = new Identifier(tokens[2]);
										Speed finalSpeed = new Speed(speed);
										Acceleration finalAcceleration = new Acceleration(acceleration);

										doCreateEngine(finalID, finalSpeed, finalAcceleration);
									}
									//System.out.println("Made it to ENGINE PRINT");//doCreateEngine()
								}
						}
				}
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("NOSEGEAR") == 0)
				{
					if(isValid("id", tokens[3]))
						if(tokens[4].concat(tokens[5]).compareToIgnoreCase("WITHSPEED") == 0)
						{
							speed = Double.parseDouble(tokens[6]);
							if(isValid("speed", speed))
								if(tokens[7].compareToIgnoreCase("ACCELERATION") == 0)
								{
									acceleration = Double.parseDouble(tokens[8]);
									if(isValid("acceleration", acceleration))
									{
										Identifier finalID = new Identifier(tokens[2]);
										Speed finalSpeed = new Speed(speed);
										Acceleration finalAcceleration = new Acceleration(acceleration);

										doCreateGearNose(finalID, finalSpeed, finalAcceleration);
									}
									//System.out.println("Made it to NOSEGEAR PRINT");//doCreateGearNose()
								}
						}
				}
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("MAINGEAR") == 0)
				{
					if(isValid("id", tokens[3]))
						if(tokens[4].concat(tokens[5]).compareToIgnoreCase("WITHSPEED") == 0)
						{
							speed = Double.parseDouble(tokens[6]);
							if(isValid("speed", speed))
								if(tokens[7].compareToIgnoreCase("ACCELERATION") == 0)
								{
									acceleration = Double.parseDouble(tokens[8]);
									if(isValid("acceleration", acceleration))
									{
										Identifier finalID = new Identifier(tokens[2]);
										Speed finalSpeed = new Speed(speed);
										Acceleration finalAcceleration = new Acceleration(acceleration);

										doCreateGearMain(finalID, finalSpeed, finalAcceleration);
									}
									//System.out.println("Made it to MAINGEAR PRINT");//doCreateGearMain()
								}
						}
				}
			}

			else if(tokens[0].compareToIgnoreCase("DECLARE") == 0 || tokens[0].compareToIgnoreCase("COMMIT") == 0)	//STRUCTURAL COMMANDS
				System.out.println("TEMPORARY FILL");	//part2

			else if(tokens[0].compareToIgnoreCase("DO") == 0 || tokens[0].compareToIgnoreCase("HALT") == 0)	//BEHAVIORAL COMMANDS
				System.out.println("TEMPORARY FILL");	//part3

			else if(tokens[0].charAt(0) == '@')	//MISCELLANEOUS COMMANDS
				System.out.println("TEMPORARY FILL");	//part4

			else	//Incorrect input.
				System.out.println("Incorrect Command Declaration.");
		}

		catch(Exception e)	//Catch all for incorrect input causing exceptions. Will change with further clarification.
		{
			System.out.println("Incorrect Command Declaration.");
		}
	}	

	static boolean isValid(String type, double input)
	{
		if(type.compareToIgnoreCase("acceleration") == 0)		//find out what appropriate units are.
			if(input > 0)
				return true;

		if(type.compareToIgnoreCase("angle") == 0)
			if(input >= 0 || input <= 360)
				return true;

		if(type.compareToIgnoreCase("percent") == 0 || type.compareToIgnoreCase("power") == 0)
			if(input >= 0 && input <= 100 )
				return true;

		if(type.compareToIgnoreCase("speed") == 0)			//find out what appropriate units are.
			if(input > 0)
				return true;
		return false;

	}

	static boolean isValid(String type, int input)
	{
		if(type.compareToIgnoreCase("rate") == 0)
			if(input > 0)
				return true;

		if(type.compareToIgnoreCase("position") == 0)
			if(input >= 1 && input <= 4)
				return true;

		return false;
	}

	static boolean isValid(String type, String input)
	{
		if(type.compareToIgnoreCase("id") == 0)  //Depends on what he wants here, if only alphaNumeric, some REGEX would probably work.
			return true;

		if(type.compareToIgnoreCase("position") == 0)
			if(input.compareToIgnoreCase("up") == 0)
				return true;

		return false;
	}
}

//   public CommandParser(final ActionSet _actionSet, final String command)
//   {
//      System.err.println("You got a command: " + command);
//   }
//
//   public void parse() throws ParseException // you don't need this, but to redirect my example processInput() in CommandLineInterface to your parser here, it
//                                             // is included
//   {
//      System.err.println("parsing");
//   }
//}