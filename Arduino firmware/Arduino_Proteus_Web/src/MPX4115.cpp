#include "MPX4115.h"

float get_pressure()
{
	return (analogRead(A2) / 1023.0 + 0.095) / 0.009;
}