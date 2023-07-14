#ifndef _VOLT_SENSOR_h
#define _VOLT_SENSOR_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#define PIN_VOLT_SENS A0

float get_volt();

#endif

