package sbw.project.cli.parser;

//@author	Ryan Young

import sbw.architecture.datatype.*;
import sbw.project.cli.action.*;
import sbw.project.components.actuator.aileron.ActuatorAileron;
import sbw.project.components.actuator.elevator.*;
import sbw.project.components.actuator.engine.ActuatorEngine;
import sbw.project.components.actuator.flap.*;
import sbw.project.components.actuator.gear.ActuatorGearMain;

import java.util.*;

/*
acceleration 	Acceleration	A nonnegative real in appropriate units per clock tick
angle 			Angle 			A nonnegative real in degrees
id 				Identifier 		An arbitrary alphanumeric identifier, like Java variables
percent 		Percent 		A real in percent [0,100]
position 		Position 		A closed set of flap positions [up,1,2,3,4]
power 			Power 			A real in percent [0,100]
rate 			Rate 			A positive integer clock rate in milliseconds
speed 			Speed 			A positive real in appropriate units per clock tick
 */

// This Class is used for command line DECLARATION statements
public class DeclareParser
{
	
	// This Method takes the DECLARE String and figures out what to do with it. 
	public void declare(String statement, ActionSet _actionset) 
	{
		
		//delimiter
		String delim = " ";
		
		//split the statement String into an array based off the " " delimiter
		String [] statementArray = statement.split(delim);
		
		// assign the passed in ActionSet locally
		ActionSet aS = _actionset;
		
		// These methods can throw runtime exceptions based off command line input errors
		try
		{
			if (statementArray[1].equals("RUDDER") && statementArray[2].equals("CONTROLLER"))				// DECLARE RUDDER
				declareRudder(statementArray, aS);
			else if (statementArray[1].equals("ELEVATOR") && statementArray[2].equals("CONTROLLER"))		// DECLARE ELEVATOR
				declareElevator(statementArray, aS);
			else if (statementArray[1].equals("AILERON") && statementArray[2].equals("CONTROLLER"))			// DECLARE AILERON
				declareAileron(statementArray, aS);
			else if (statementArray[1].equals("FLAP") && statementArray[2].equals("CONTROLLER"))			// DECLARE FLAP
				declareFlap(statementArray, aS);
			else if (statementArray[1].equals("ENGINE") && statementArray[2].equals("CONTROLLER"))			// DECLARE ENGINE
				declareEngine(statementArray, aS);
			else if (statementArray[1].equals("GEAR") && statementArray[2].equals("CONTROLLER"))			// DECLARE GEAR
				declareGear(statementArray, aS);
			else if (statementArray[1].equals("BUS"))														// DECLARE BUS
				declareBus(statementArray, aS);
			else if (statementArray[0].equals("COMMIT"))													// COMMIT
				commitState(aS);
			else											
			{
				// throw runtime exception -> statement String does not match required criteria
				System.out.println("The entered statement String -" + statementArray[0] + "- does not match the required command criteria");
				throw new RuntimeException();
			}
		} catch (RuntimeException e)
		{
			// Bail to main parser
			e.toString();
			throw new RuntimeException(); 
		} // end catch
	
	} // end declare

	// ><><><><><><><><><><><><><><><><><><><><><><><>< METHODS AFTER INITIAL STATEMENT PARSE ><><><><><><><><><><><><><><><><><><><><><><><>< START
	
	// Identifiers must be unique
	
	// This Method helps DECLARE a RUDDER object
	public void declareRudder(String [] statementArray, ActionSet aS)							// RUDDER
	{
		
		// at statementArray[3]
		
		if (statementArray[4].toUpperCase().equals("WITH") && statementArray[5].toUpperCase().equals("RUDDER")) 
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
		
		if (statementArray[4].toUpperCase().equals("WITH") && statementArray[5].toUpperCase().equals("ELEVATOR"))
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
			if (!first.getAcceleration().equals(second.getAcceleration()) || !first.getIntervalLength().equals(second.getIntervalLength()) ||	
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
				
				A_ActuatorFlap first = (A_ActuatorFlap)aS.getActionCreational().getMapActuator().getEntry(fNameL);   
				A_ActuatorFlap second = (A_ActuatorFlap)aS.getActionCreational().getMapActuator().getEntry(fNameR);
				
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
		
		if (statementArray[4].toUpperCase().equals("WITH") && statementArray[5].toUpperCase().equals("ENGINE")) // EMPLOY HELPER METHOD FOR ENGINE or ENGINES
		{
			Identifier cName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(cName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();
			
			ArrayList<Identifier>idList = new ArrayList<Identifier>();
			ActuatorEngine test = null;
			
			int counter = 6;
			while (statementArray[counter] != null)
			{
				// Create Identifier
				Identifier eName = new Identifier(statementArray[counter]);
				
				if (!aS.getActionCreational().getMapActuator().getEntry(eName).getClass().equals(ActuatorEngine.class)) 
						throw new RuntimeException();
				
				ActuatorEngine current = (ActuatorEngine)aS.getActionCreational().getMapActuator().getEntry(eName);
				
				if (counter == 6)
					test = (ActuatorEngine)aS.getActionCreational().getMapActuator().getEntry(eName);
				
				if (counter > 6)
					if (!current.getAcceleration().equals(test.getAcceleration()) || !current.getIntervalLength().equals(test.getIntervalLength()) ||
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
		
		if (statementArray[4].toUpperCase().equals("WITH") && statementArray[5].toUpperCase().equals("GEAR") 
		 && statementArray[6].toUpperCase().equals("NOSE") && statementArray[8].toUpperCase().equals("MAIN"))
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
		
		if (statementArray[4].toUpperCase().equals("WITH") && statementArray[5].toUpperCase().equals("CONTROLLER")) // EMPLOY HELPER METHOD FOR CONTROLLER or CONTROLLERS
		{
			Identifier bName = new Identifier(statementArray[3]);
			if (aS.getActionStructural().getMapController().hasEntry(bName)) // check if provided controller identifier is duplicate
				throw new RuntimeException();
			
			ArrayList<Identifier> idList = new ArrayList<Identifier>();
			
			int counter = 6;
			while (statementArray[counter] != null)
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
	
	// ><><><><><><><><><><><><><><><><><><><><><><><>< METHODS AFTER INITIAL STATEMENT PARSE ><><><><><><><><><><><><><><><><><><><><><><><>< END
	
} // end DeclareParser
