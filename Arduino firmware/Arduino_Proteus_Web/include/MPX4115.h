#ifndef _MPX4115_h
#define _MPX4115_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#define MPX4115_PIN A2

float get_pressure();

#endif