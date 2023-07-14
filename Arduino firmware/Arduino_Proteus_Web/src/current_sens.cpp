#include "current_sens.h"

Adafruit_INA219 ina219;

void init_curr_sens()
{
	ina219.begin();
}

float get_amper()
{
	return ina219.getCurrent_mA() * 10;
}