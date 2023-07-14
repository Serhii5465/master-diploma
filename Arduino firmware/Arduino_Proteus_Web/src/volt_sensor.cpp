#include "volt_sensor.h"

float get_volt()
{
	return analogRead(PIN_VOLT_SENS) * (5.0 / 1023.0);
}