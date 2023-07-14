#ifndef _VACUUM_SENSOR_h
#define _VACUUM_SENSOR_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#define PIN_VAC_SENS A1

#define K 9.345

float get_pres_vac();

#endif

