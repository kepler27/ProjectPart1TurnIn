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
								submitCommand(new CommandDoDeflectRudder(controllerName, deflectAngle, isRight));
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
								submitCommand(new CommandDoDeflectElevator(controllerName, deflectAngle, isDown));
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
								submitCommand(new CommandDoDeflectAilerons(controllerName, deflectAngle, isDown));
							}
						}
						else if(tokens[3].compareToIgnoreCase("FLAP") == 0)
						{
							String posString = tokens[4].toUpperCase();
							Position.E_Position posEnum = Position.E_Position.valueOf(posString);
							if(posEnum != null)
							{
								Position position = new Position(posEnum);
								submitCommand(new CommandDoSetFlaps(controllerName, position));
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
						
						_actionSet.getActionBehavioral().submitCommand(new CommandDoDeploySpeedBrake(controllerName, isDeployed));
						
					}
					else if(tokens[2].compareToIgnoreCase("GEAR") == 0)
					{
						boolean isDown;
						
						if(tokens[3].compareToIgnoreCase("DOWN") == 0)
							isDown = true;
						else
							isDown = false;
						
						submitCommand(new CommandDoSelectGear(controllerName, isDown));
						
					}
					else if(tokens[2].compareToIgnoreCase("SET") == 0 && tokens[3].compareToIgnoreCase("POWER") == 0)
					{
						if(tokens.length == 5)
						{
							double pow = Double.parseDouble(tokens[4]);
							if(isValid("power", pow))
							{
								Power power = new Power(pow);
								submitCommand(new CommandDoSetEnginePowerAll(controllerName, power));
							}
							
						}
						else if(tokens[5].compareToIgnoreCase("ENGINE") == 0)
						{
							double pow = Double.parseDouble(tokens[4]);
							if(isValid("power", pow))
							{
								Power power = new Power(pow);
								Identifier engineID = new Identifier(tokens[6]);
								submitCommand(new CommandDoSetEnginePowerSingle(controllerName, power, engineID));
							}
							
						}
					}
					
				}
				else if(tokens[0].compareToIgnoreCase("HALT") == 0)
				{
					Identifier haltID = new Identifier(tokens[1]);
					submitCommand(new CommandDoHalt(haltID));
				}
			}