package sbw.project.cli.parser;

import java.util.ArrayList;

import sbw.architecture.datatype.*;
import sbw.project.cli.action.*;
import sbw.project.cli.action.command.behavioral.*;
import sbw.project.cli.action.command.misc.*;
import sbw.project.components.actuator.aileron.ActuatorAileron;
import sbw.project.components.actuator.elevator.ActuatorElevator;
import sbw.project.components.actuator.engine.ActuatorEngine;
import sbw.project.components.actuator.flap.A_ActuatorFlap;
import sbw.project.components.actuator.flap.ActuatorFlapFowler;
import sbw.project.components.actuator.gear.ActuatorGearMain;

/**
 * Parser for the Command Line Interface 
 * 
 * @author Frank Kepler
 * @author Peter Griffin
 * @author Ryan Young
 *
 */
public class CommandParser 
{

	public CommandParser(final ActionSet _actionSet, final String command)
	{
		String delims = "[;//]+";

		String[] commands = command.split(delims);

		for(int i = 0; i < commands.length; i++)
			parse(commands[i], _actionSet);
	}


	public void parse(String singleCommand, ActionSet actionSet)
	{
		//Temporary String Command for testing, this is where the String that comes in will be placed.
		//String command = "CREATE MAIN GEAR cat WITH SPEED 138 ACCELERATION 500";

		//This sets the delims to any space " ".
		String delims = "[ ]+";
		singleCommand = singleCommand.trim();
		//Creates an array of Strings based on the delimiters chosen above, which is the " " space character.
		String[] tokens = singleCommand.split(delims);

		double acceleration, angle1, angle2, pow, speed;
		int rate;


		try
		{
			if(tokens[0].compareToIgnoreCase("CREATE") == 0)	//CREATIONAL COMMANDS
			{
				if(tokens[1].compareToIgnoreCase("RUDDER") == 0)
				{
					Identifier finalID = new Identifier(tokens[2]);
					if(isValid(finalID, actionSet))
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

												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);


												actionSet.getActionCreational().doCreateRudder(finalID, finalAngle1, finalSpeed, finalAcceleration);
											}

										}
								}
						}
				}
				else if(tokens[1].compareToIgnoreCase("ELEVATOR") == 0)
				{
					Identifier finalID = new Identifier(tokens[2]);
					if(isValid(finalID, actionSet))
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
												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);

												actionSet.getActionCreational().doCreateElevator(finalID, finalAngle1, finalSpeed, finalAcceleration);
											}
										}
								}
						}
				}
				else if(tokens[1].compareToIgnoreCase("AILERON") == 0)
				{
					Identifier finalID = new Identifier(tokens[2]);
					if(isValid(finalID, actionSet))
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

														Angle finalAngle1 = new Angle(angle1);
														Angle finalAngle2 = new Angle(angle2);
														Speed finalSpeed = new Speed(speed);
														Acceleration finalAcceleration = new Acceleration(acceleration);

														actionSet.getActionCreational().doCreateAileron(finalID, finalAngle1, finalAngle2, finalSpeed, finalAcceleration);
													}
												}
										}
								}
						}
				}
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("SPLITFLAP") == 0)
				{
					Identifier finalID = new Identifier(tokens[3]);
					if(isValid(finalID, actionSet))
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
												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);

												actionSet.getActionCreational().doCreateFlap(finalID, false, finalAngle1, finalSpeed, finalAcceleration);
											}
										}
								}
						}
				}			
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("FOWLERFLAP") == 0)
				{
					Identifier finalID = new Identifier(tokens[3]);
					if(isValid(finalID, actionSet))
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
												Angle finalAngle1 = new Angle(angle1);
												Speed finalSpeed = new Speed(speed);
												Acceleration finalAcceleration = new Acceleration(acceleration);

												actionSet.getActionCreational().doCreateFlap(finalID, true, finalAngle1, finalSpeed, finalAcceleration);
											}
										}
								}
						}
				}
				else if(tokens[1].compareToIgnoreCase("ENGINE") == 0)
				{
					Identifier finalID = new Identifier(tokens[2]);
					if(isValid(finalID, actionSet))
						if(tokens[3].concat(tokens[4]).compareToIgnoreCase("WITHSPEED") == 0)
						{
							speed = Double.parseDouble(tokens[5]);
							if(isValid("speed", speed))
								if(tokens[6].compareToIgnoreCase("ACCELERATION") == 0)
								{
									acceleration = Double.parseDouble(tokens[7]);
									if(isValid("acceleration", acceleration))
									{
										Speed finalSpeed = new Speed(speed);
										Acceleration finalAcceleration = new Acceleration(acceleration);

										actionSet.getActionCreational().doCreateEngine(finalID, finalSpeed, finalAcceleration);
									}
								}
						}
				}
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("NOSEGEAR") == 0)
				{
					Identifier finalID = new Identifier(tokens[3]);
					if(isValid(finalID, actionSet))
						if(tokens[4].concat(tokens[5]).compareToIgnoreCase("WITHSPEED") == 0)
						{
							speed = Double.parseDouble(tokens[6]);
							if(isValid("speed", speed))
								if(tokens[7].compareToIgnoreCase("ACCELERATION") == 0)
								{
									acceleration = Double.parseDouble(tokens[8]);
									if(isValid("acceleration", acceleration))
									{
										Speed finalSpeed = new Speed(speed);
										Acceleration finalAcceleration = new Acceleration(acceleration);

										actionSet.getActionCreational().doCreateGearNose(finalID, finalSpeed, finalAcceleration);
									}
								}
						}
				}
				else if(tokens[1].concat(tokens[2]).compareToIgnoreCase("MAINGEAR") == 0)
				{
					Identifier finalID = new Identifier(tokens[3]);
					if(isValid(finalID, actionSet))
						if(tokens[4].concat(tokens[5]).compareToIgnoreCase("WITHSPEED") == 0)
						{
							speed = Double.parseDouble(tokens[6]);
							if(isValid("speed", speed))
								if(tokens[7].compareToIgnoreCase("ACCELERATION") == 0)
								{
									acceleration = Double.parseDouble(tokens[8]);
									if(isValid("acceleration", acceleration))
									{
										Speed finalSpeed = new Speed(speed);
										Acceleration finalAcceleration = new Acceleration(acceleration);

										actionSet.getActionCreational().doCreateGearMain(finalID, finalSpeed, finalAcceleration);
									}
								}
						}
				}
			}


			/*____________________________________________________________________________________________________________________________________________*/
			else if(tokens[0].compareToIgnoreCase("DECLARE") == 0 || tokens[0].compareToIgnoreCase("COMMIT") == 0)	//STRUCTURAL COMMANDS
			{	
				if (tokens[0].compareToIgnoreCase("COMMIT")==0)											// COMMIT
					commitState(actionSet);
				else if (tokens[1].compareToIgnoreCase("ELEVATOR")==0 && tokens[2].compareToIgnoreCase("CONTROLLER")==0)		// DECLARE ELEVATOR
					declareElevator(tokens, actionSet);
				else if (tokens[1].compareToIgnoreCase("AILERON")==0 && tokens[2].compareToIgnoreCase("CONTROLLER")==0)			// DECLARE AILERON
					declareAileron(tokens, actionSet);
				else if (tokens[1].compareToIgnoreCase("FLAP")==0 && tokens[2].compareToIgnoreCase("CONTROLLER")==0)			// DECLARE FLAP
					declareFlap(tokens, actionSet);
				else if (tokens[1].compareToIgnoreCase("ENGINE")==0 && tokens[2].compareToIgnoreCase("CONTROLLER")==0)			// DECLARE ENGINE
					declareEngine(tokens, actionSet);
				else if (tokens[1].compareToIgnoreCase("GEAR")==0 && tokens[2].compareToIgnoreCase("CONTROLLER")==0)			// DECLARE GEAR
					declareGear(tokens, actionSet);
				else if (tokens[1].compareToIgnoreCase("BUS")==0)												// DECLARE BUS
					declareBus(tokens, actionSet);
				else if (tokens[1].compareToIgnoreCase("RUDDER")==0 && tokens[2].compareToIgnoreCase("CONTROLLER")==0)				// DECLARE RUDDER
					declareRudder(tokens, actionSet);
			}


			/*____________________________________________________________________________________________________________________________________________*/

			else if(tokens[0].compareToIgnoreCase("DO") == 0 || tokens[0].compareToIgnoreCase("HALT") == 0)	//BEHAVIORAL COMMANDS
			{
				if(tokens[0].compareToIgnoreCase("DO") == 0)
				{
					Identifier controllerName = new Identifier(tokens[1]);

					if(tokens[2].compareToIgnoreCase("DEFLECT") == 0)
					{
						if(tokens[3].compareToIgnoreCase("RUDDER") == 0)
						{
							boolean isRight;

							if(tokens[5].compareToIgnoreCase("RIGHT") == 0)
								isRight = true;

							else
								isRight = false;

							angle1 = Double.parseDouble(tokens[4]);

							if(isValid("angle", angle1))
							{
								Angle deflectAngle = new Angle(angle1);
								actionSet.getActionBehavioral().submitCommand(new CommandDoDeflectRudder(controllerName, deflectAngle, isRight));
							}
						}
						else if(tokens[3].compareToIgnoreCase("ELEVATOR") == 0)
						{
							boolean isDown;

							if(tokens[5].compareToIgnoreCase("DOWN") == 0)
								isDown = true;
							else
								isDown = false;

							angle1 = Double.parseDouble(tokens[4]);

							if(isValid("angle", angle1))
							{
								Angle deflectAngle = new Angle(angle1);
								actionSet.getActionBehavioral().submitCommand(new CommandDoDeflectElevator(controllerName, deflectAngle, isDown));
							}
						}
						else if(tokens[3].compareToIgnoreCase("AILERONS") == 0)
						{
							boolean isDown;

							if(tokens[5].compareToIgnoreCase("DOWN") == 0)
								isDown = true;
							else
								isDown = false;

							angle1 = Double.parseDouble(tokens[4]);

							if(isValid("angle", angle1))
							{
								Angle deflectAngle = new Angle(angle1);
								actionSet.getActionBehavioral().submitCommand(new CommandDoDeflectAilerons(controllerName, deflectAngle, isDown));
							}
						}
						else if(tokens[3].compareToIgnoreCase("FLAP") == 0)
						{
							String posString = tokens[4].toUpperCase();
							Position.E_Position posEnum = Position.E_Position.valueOf(posString);
							if(posEnum != null)
							{
								Position position = new Position(posEnum);
								actionSet.getActionBehavioral().submitCommand(new CommandDoSetFlaps(controllerName, position));
							}
						}
					}
					else if(tokens[2].compareToIgnoreCase("SPEED") == 0 && tokens[3].compareToIgnoreCase("BRAKE") == 0)
					{
						boolean isDeployed;

						if(tokens[4].compareToIgnoreCase("ON") == 0)
							isDeployed = true;
						else
							isDeployed = false;

						actionSet.getActionBehavioral().submitCommand(new CommandDoDeploySpeedBrake(controllerName, isDeployed));

					}
					else if(tokens[2].compareToIgnoreCase("GEAR") == 0)
					{
						boolean isDown;

						if(tokens[3].compareToIgnoreCase("DOWN") == 0)
							isDown = true;
						else
							isDown = false;

						actionSet.getActionBehavioral().submitCommand(new CommandDoSelectGear(controllerName, isDown));

					}
					else if(tokens[2].compareToIgnoreCase("SET") == 0 && tokens[3].compareToIgnoreCase("POWER") == 0)
					{
						if(tokens.length == 5)
						{
							pow = Double.parseDouble(tokens[4]);
							if(isValid("power", pow))
							{
								Power power1 = new Power(pow);
								actionSet.getActionBehavioral().submitCommand(new CommandDoSetEnginePowerAll(controllerName, power1));
							}
						}
						else if(tokens[5].compareToIgnoreCase("ENGINE") == 0)
						{
							pow = Double.parseDouble(tokens[4]);
							if(isValid("power", pow))
							{
								Power power1 = new Power(pow);
								Identifier engineID = new Identifier(tokens[6]);
								actionSet.getActionBehavioral().submitCommand(new CommandDoSetEnginePowerSingle(controllerName, power1, engineID));
							}
						}
					}
				}
				else if(tokens[0].compareToIgnoreCase("HALT") == 0)
				{
					Identifier haltID = new Identifier(tokens[1]);
					actionSet.getActionBehavioral().submitCommand(new CommandDoHalt(haltID));
				}
			}

			else if(tokens[0].charAt(0) == '@')	//MISCELLANEOUS COMMANDS
			{
				if(tokens[0].compareToIgnoreCase("@CLOCK") == 0)
					if(tokens.length == 1)
						actionSet.getActionMiscellaneous().submitCommand(new CommandDoShowClock());
					else if(tokens[1].compareToIgnoreCase("PAUSE") == 0) 
						actionSet.getActionMiscellaneous().submitCommand(new CommandDoSetClockRunning(false));
					else if(tokens[1].compareToIgnoreCase("RESUME") == 0)
						actionSet.getActionMiscellaneous().submitCommand(new CommandDoSetClockRunning(true));
					else if(tokens[1].compareToIgnoreCase("UPDATE") == 0 )
						actionSet.getActionMiscellaneous().submitCommand(new CommandDoClockUpdate());
					else
					{
						rate = Integer.parseInt(tokens[1]);
						if(isValid("rate", rate))
						{
							Rate finalRate = new Rate(rate);
							actionSet.getActionMiscellaneous().submitCommand(new CommandDoSetClockRate(finalRate));
						}
					}

				else if(tokens[0].compareToIgnoreCase("@RUN") == 0)
				{
					if(tokens.length == 2)

					{
						String fn = tokens[1];
						fn = fn.substring(1, fn.length()-1);
						actionSet.getActionMiscellaneous().submitCommand(new CommandDoRunCommandFile(fn));
					}	
				}

				else if(tokens[0].compareToIgnoreCase("@EXIT") == 0)
					actionSet.getActionMiscellaneous().submitCommand(new CommandDoExit());

				else if(tokens[0].compareToIgnoreCase("@WAIT") == 0)
					if(tokens.length == 2)
					{
						rate = Integer.parseInt(tokens[1]);
						if(isValid("rate", rate))
						{
							Rate finalRate = new Rate(rate);
							actionSet.getActionMiscellaneous().submitCommand(new CommandDoWait(finalRate));
						}
					}
			}


			else	//Incorrect input.
				System.out.println("Incorrect Command Declaration or Comment.");
		}

		catch(Exception e)	//Catch all for incorrect input causing exceptions. Will change with further clarification.
		{
			System.out.println(e.toString() + " Incorrect Command Declaration.");
		}
	}	

	/*___________________________________________________________________________________________________________________________________________*/

	/**
	 * Validation Methods
	 * 
	 * @param 	type		String stating what type of object we are intending to make if boolean returns true.
	 * @param 	input		The value we are attempting to check for validity.
	 * @return	boolean 	Returns true/false based on validity.
	 */

	static boolean isValid(String type, double input)
	{
		if(type.compareToIgnoreCase("acceleration") == 0)		//find out what appropriate units are.
			if(input > 0 && input <= 5)
				return true;

		if(type.compareToIgnoreCase("angle") == 0)
			if(input >= 0 && input <= 90)
				return true;

		if(type.compareToIgnoreCase("percent") == 0 || type.compareToIgnoreCase("power") == 0)
			if(input >= 0 && input <= 100 )
				return true;

		if(type.compareToIgnoreCase("speed") == 0)			//find out what appropriate units are.
			if(input > 0 && input <= 10)
				return true;
		System.out.println("Sorry, incorrect " + type + ".");
		return false;

	}

	static boolean isValid(String type, int input)
	{
		if(type.compareToIgnoreCase("rate") == 0)
			if(input > 0)
				return true;
		System.out.println("Sorry, incorrect " + type + ".");
		return false;
	}

	static boolean isValid(Identifier finalID, ActionSet actionSet)
	{
		if(!actionSet.getMapActuator().hasEntry(finalID) && !actionSet.getMapController().hasEntry(finalID))
			return true;

		System.out.println("Sorry, there is already an actuator with " + finalID + " as the name.");
		return false;
	}
	/*____________________________________________________________________________________________________________________________________________*/



	// This Method helps DECLARE a RUDDER object
	public void declareRudder(String [] statementArray, ActionSet aS)							// RUDDER
	{

		// at statementArray[3]

		if (statementArray[4].toUpperCase().compareToIgnoreCase("WITH")==0 && statementArray[5].toUpperCase().compareToIgnoreCase("RUDDER")==0) 
		{	
			Identifier cName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(cName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();

			Identifier rName = new Identifier(statementArray[6]);

			aS.getActionStructural().doDeclareRudderController(cName, rName);

		}

	} // end declareRudder

	// This Method helps DECLARE a ELEVATOR object
	public void declareElevator(String [] statementArray, ActionSet aS)						// ELEVATOR
	{

		// at statementArray[3]

		if (statementArray[4].toUpperCase().compareToIgnoreCase("WITH")==0 && statementArray[5].toUpperCase().compareToIgnoreCase("ELEVATORS")==0)
		{
			Identifier cName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(cName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();

			Identifier eNameL = new Identifier(statementArray[6]);
			Identifier eNameR = new Identifier(statementArray[7]);

			if (!aS.getActionCreational().getMapActuator().getEntry(eNameL).getClass().equals(ActuatorElevator.class) ||
					!aS.getActionCreational().getMapActuator().getEntry(eNameR).getClass().equals(ActuatorElevator.class))
				throw new RuntimeException();

			ActuatorElevator first = (ActuatorElevator)aS.getActionCreational().getMapActuator().getEntry(eNameL);
			ActuatorElevator second = (ActuatorElevator)aS.getActionCreational().getMapActuator().getEntry(eNameR);

			// check acceleration, interval length, speed max
			if (!first.getAcceleration().equals(second.getAcceleration()) || !first.getIntervalLength().equals(second.getIntervalLength()) ||	// POSSIBLY NOT FINAL CHECK
					!first.getSpeedMax().equals(second.getSpeedMax()) || !first.getLimit().equals(second.getLimit())) 
				throw new RuntimeException();	

			// takes 2 identifiers
			aS.getActionStructural().doDeclareElevatorController(cName, eNameL, eNameR);

		}
		else
			throw new RuntimeException();

	} // end declareElevator

	// This Method helps DECLARE a AILERON object
	public void declareAileron(String [] statementArray, ActionSet aS)						// AILERON >>NOT FINISHED<<
	{

		// at statementArray[3]
		boolean noSlave = false;

		if (statementArray[4].toUpperCase().equals("WITH") && statementArray[5].toUpperCase().equals("AILERONS"))
		{
			int counter = 6;
			ArrayList<Identifier> idListMain = new ArrayList<Identifier>();
			ArrayList<AileronSlaveMix> idListSlave = new ArrayList<AileronSlaveMix>();

			Identifier cName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(cName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();

			// This loop creates two identifier objects per pair sent in and adds to the ArrayList<Identifier> idListMain, throws RuntimeException if odd number of Identifiers is found.
			while (true)
			{		
				if (counter == statementArray.length) // regardless of current element, if next element is null, there is no pair or Primary
					throw new RuntimeException();
				if (statementArray[counter].toUpperCase().equals("PRIMARY") && (counter+1) == statementArray.length-1) // if at end, there is Primary identifier, no slaves
				{
					noSlave = true;
					break;
				}
				if (statementArray[counter].toUpperCase().equals("PRIMARY") && (counter+1) < statementArray.length) // if at end, but there are slave(s)
					break;

				Identifier aName1 = new Identifier(statementArray[counter]);
				Identifier aName2 = new Identifier(statementArray[counter+1]);

				if (!aS.getActionCreational().getMapActuator().getEntry(aName1).getClass().equals(ActuatorAileron.class) ||	
						!aS.getActionCreational().getMapActuator().getEntry(aName2).getClass().equals(ActuatorAileron.class))
					throw new RuntimeException();		

				// add identifiers to ArrayList<Identifier> idList
				idListMain.add(aName1);
				idListMain.add(aName2);
				counter+=2;
			} // end while	

			int cL = 0, cR = idListMain.size()-1;
			ActuatorAileron left, right;

			// this loop checks for symmetry by going to first and last, checking, then 2nd and 2nd to last, then 3rd and 3rd to last until all pairs are checked
			while (true)
			{
				if (cL > cR)
					break;

				left = (ActuatorAileron)aS.getActionCreational().getMapActuator().getEntry(idListMain.get(cL));
				right = (ActuatorAileron)aS.getActionCreational().getMapActuator().getEntry(idListMain.get(cR));

				// check for symmetry
				if (!left.getAcceleration().equals(right.getAcceleration()) || !left.getIntervalLength().equals(right.getIntervalLength()) ||	
						!left.getSpeedMax().equals(right.getSpeedMax()) || !left.getLimit().equals(right.getLimit()))
					throw new RuntimeException();

				cL++;
				cR--;
			} // end while

			// else grab primary and 
			counter++;
			Identifier pName = new Identifier(statementArray[counter]);
			counter++;

			if (noSlave)
				return;

			// this loop grabs each slave mix IF THERE ARE ANY and inserts them into idListSlave
			while (true)
			{
				if (counter == statementArray.length)
					break;

				// check for proper grammar
				if (counter < statementArray.length-1 && !statementArray[counter].toUpperCase().equals("SLAVE") || 
						counter+2 < statementArray.length-1 && !statementArray[counter+2].toUpperCase().equals("TO") ||
						counter+4 < statementArray.length-1 && !statementArray[counter+4].toUpperCase().equals("BY") || 
						counter+6 < statementArray.length-1 && !statementArray[counter+6].toUpperCase().equals("PERCENT"))
					throw new RuntimeException();

				//TODO MAY NEED MORE ERROR CHECKING THAN THIS
				Identifier slave = new Identifier(statementArray[counter+1]);
				Identifier master = new Identifier(statementArray[counter+3]);
				Percent percent = new Percent(Double.parseDouble(statementArray[counter+5]));
				if (percent.getValue() < 0 || percent.getValue() > 100)
					throw new RuntimeException();

				// create and add slaveMix to idListSlave
				AileronSlaveMix aSM = new AileronSlaveMix(slave, master, percent);
				idListSlave.add(aSM);
				counter+=7;
			}

			//create controller
			aS.getActionStructural().doDeclareAileronController(cName, idListMain, pName, idListSlave);     

		} // end outer if
		else
			throw new RuntimeException();

	} // end declareAileron

	// This Method helps DECLARE a FLAP object
	public void declareFlap(String [] statementArray, ActionSet aS)							// FLAP
	{

		// at statementArray[3]
		// each flap <id2>+ & <id3>+ must be symmetrical to one another within each pair

		if (statementArray[4].toUpperCase().equals("WITH") && statementArray[5].toUpperCase().equals("FLAPS"))
		{
			if (statementArray.length%2 != 0)  // there is not a pair, destroy universe
				throw new RuntimeException();

			Identifier cName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(cName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();			

			ArrayList<Identifier> idList = new ArrayList<Identifier>();
			int counter = 6;

			if (counter == statementArray.length) // if no identifiers, given, throw exception
				throw new RuntimeException();

			// This loop creates two identifier objects per pair sent in and adds to the Identifier ArrayList<Identifier>, throws RuntimeException if odd number of Identifiers is found.
			while (true)
			{		
				if (counter == statementArray.length) // end of pairs, break;
					break;

				Identifier fNameL = new Identifier(statementArray[counter]);
				Identifier fNameR = new Identifier(statementArray[counter+1]);

				A_ActuatorFlap<?> first = (A_ActuatorFlap<?>)aS.getActionCreational().getMapActuator().getEntry(fNameL);   
				A_ActuatorFlap<?> second = (A_ActuatorFlap<?>)aS.getActionCreational().getMapActuator().getEntry(fNameR);

				// check acceleration, interval length, speed max
				if (!first.getAcceleration().equals(second.getAcceleration()) || !first.getIntervalLength().equals(second.getIntervalLength()) ||	// POSSIBLY NOT FINAL CHECK
						!first.getSpeedMax().equals(second.getSpeedMax()) || !first.getLimit().equals(second.getLimit()))
					throw new RuntimeException();

				// add identifiers to ArrayList<Identifier> idList
				idList.add(fNameL);
				idList.add(fNameR);
				counter+=2;
			} // end while

			// takes n (where n is even) identifiers, may implement while loop for each doDeclareFlapController(<id1>, <id2>)
			aS.getActionStructural().doDeclareFlapController(cName, idList);

		}
		else
			throw new RuntimeException();

	} // end declareFlap

	// This Method helps DECLARE a ENGINE object
	public void declareEngine(String [] statementArray, ActionSet aS)							// ENGINE
	{

		// at statementArray[3]
		// all engines <id2>+ must be identical

		if (statementArray[4].toUpperCase().compareToIgnoreCase("WITH")==0 && statementArray[5].toUpperCase().compareToIgnoreCase("ENGINES")==0) // EMPLOY HELPER METHOD FOR ENGINE or ENGINES
		{
			Identifier cName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(cName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();

			ArrayList<Identifier>idList = new ArrayList<Identifier>();
			ActuatorEngine test = null;

			int counter = 6;
			while (counter < statementArray.length)
			{
				// Create Identifier
				Identifier eName = new Identifier(statementArray[counter]);

				if (!aS.getActionCreational().getMapActuator().getEntry(eName).getClass().equals(ActuatorEngine.class)) 
					throw new RuntimeException();

				ActuatorEngine current = (ActuatorEngine)aS.getActionCreational().getMapActuator().getEntry(eName);

				if (counter == 6)
					test = (ActuatorEngine)aS.getActionCreational().getMapActuator().getEntry(eName);

				if (counter > 6)
					if (!current.getAcceleration().equals(test.getAcceleration()) || !current.getIntervalLength().equals(test.getIntervalLength()) ||	// NOT FINAL CHECK
							!current.getSpeedMax().equals(test.getSpeedMax()))
						throw new RuntimeException();

				// add Identifier to ArrayList<Identifier> idList
				idList.add(eName);

				// move to next element in statementArray if there is one
				counter++;
			} // end while

			// takes n identifiers, may implement while loop for each doDeclareEngineController(<id2>)
			aS.getActionStructural().doDeclareEngineController(cName, idList);

		}
		else
			throw new RuntimeException();

	} // end declareEngine

	// This Method helps DECLARE a GEAR object
	public void declareGear(String [] statementArray, ActionSet aS)							// GEAR
	{

		// at statementArray[3]
		// <id3> & <id4> must be identical in configuration (BOTH MAIN GEARS)

		if (statementArray[4].toUpperCase().compareToIgnoreCase("WITH")==0 && statementArray[5].toUpperCase().compareToIgnoreCase("GEAR")==0 
				&& statementArray[6].toUpperCase().compareToIgnoreCase("NOSE")==0 && statementArray[8].toUpperCase().compareToIgnoreCase("MAIN")==0)
		{
			Identifier cName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(cName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();

			Identifier nName = new Identifier(statementArray[7]);

			// check to make sure identical config
			Identifier mNameL = new Identifier(statementArray[9]);
			Identifier mNameR = new Identifier(statementArray[10]);

			if (!aS.getActionCreational().getMapActuator().getEntry(mNameL).getClass().equals(ActuatorGearMain.class) ||
					!aS.getActionCreational().getMapActuator().getEntry(mNameR).getClass().equals(ActuatorGearMain.class))
				throw new RuntimeException();

			ActuatorGearMain first = (ActuatorGearMain)aS.getActionCreational().getMapActuator().getEntry(mNameL);
			ActuatorGearMain second = (ActuatorGearMain)aS.getActionCreational().getMapActuator().getEntry(mNameR);

			if (!first.getAcceleration().equals(second.getAcceleration()) || !first.getIntervalLength().equals(second.getIntervalLength()) ||	// NOT FINAL CHECK
					!first.getSpeedMax().equals(second.getSpeedMax()))
				throw new RuntimeException();

			// takes 3 identifiers, <id2> is NOSE, <id3>(LEFT) & <id4>(RIGHT) are MAIN, may need to check LEFT & RIGHT
			aS.getActionStructural().doDeclareGearController(cName, nName, mNameL, mNameR);

		}
		else
			throw new RuntimeException();

	} // end declareGear

	// This Method helps DECLARE a BUS
	public void declareBus(String [] statementArray, ActionSet aS)							// BUS
	{

		// at statementArray[2]

		if (statementArray[3].toUpperCase().compareToIgnoreCase("WITH")==0 && statementArray[4].toUpperCase().compareToIgnoreCase("CONTROLLERS")==0) // EMPLOY HELPER METHOD FOR CONTROLLER or CONTROLLERS
		{
			Identifier bName = new Identifier(statementArray[2]);
			if (aS.getActionStructural().getMapController().hasEntry(bName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();

			ArrayList<Identifier> idList = new ArrayList<Identifier>();

			int counter = 5;
			while (counter < statementArray.length)
			{
				// Create Identifier
				Identifier cName = new Identifier(statementArray[counter]);

				// add Identifier to ArrayList<Identifier> idList
				idList.add(cName);

				// move to next element in statementArray if there is one
				counter++;
			}

			// takes n identifiers, may implement while loop for each doDeclareBus(<id2>)
			aS.getActionStructural().doDeclareBus(bName, idList);

		}
		else
			throw new RuntimeException();

	} // end declareBus

	// This Method helps COMMIT the current STATE
	public void commitState(ActionSet aS)													// COMMIT
	{

		aS.getActionStructural().doCommit();

	} // end commitState
}