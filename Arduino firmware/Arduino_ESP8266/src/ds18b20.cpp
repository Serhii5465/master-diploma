#include "ds18b20.h"

OneWire ds(DS18B20_PIN);

byte data[2];

float get_temp()
{
	while (ds.reset() != 1){}

	ds.skip();
	ds.write(0x44);
	
	delay(1000);

	while (ds.reset() != 1){}

	ds.skip();
	ds.write(0xBE);

	data[0] = ds.read();
	data[1] = ds.read();

	int16_t raw = ((data[1] << 8) | data[0]);
	float cel = (float)raw / 16;

	return cel;
}