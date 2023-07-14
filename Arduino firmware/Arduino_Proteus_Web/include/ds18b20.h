#ifndef _DS18B20_h
#define _DS18B20_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#include "OW.h"

#define PIN_DS18B20 8
#define COUNT_TEMP_SENS 4

void init_DS18B20();
void get_temp(float *arr);

#endif

