#ifndef _CURRENT_SENS_h
#define _CURRENT_SENS_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#include <Adafruit_INA219.h>

void init_curr_sens();
float get_amper();

#endif
