#include "vacuum_sensor.h"

float get_pres_vac()
{
	return (analogRead(PIN_VAC_SENS) * (5.0 / 1023.0)) / K;
}