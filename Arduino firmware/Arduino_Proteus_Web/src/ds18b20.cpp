#include "ds18b20.h"

OneWire ds(PIN_DS18B20);
byte addr[COUNT_TEMP_SENS][8];
byte data[2];

float HIGH_VAL_DS18B20[4];
float LOW_VAL_DS18B20[4];

void init_DS18B20()
{
	for (byte i = 0; i < COUNT_TEMP_SENS; i++)
	{
		if (!ds.search(addr[i]))
		{
			ds.reset_search();
			delay(1000);
			return;
		}
		/*else
		{
			
			//**********вывести номер датчика и его ROM ******     
			      Serial.print("Temp. Sensor ");
			      Serial.print(i);
			      Serial.print(": ");
			      for (int j = 0; j < 8; j++)
			      {
			        Serial.print(addr[i][j], HEX);
			        Serial.print(addr[i][j], HEX);
			      }
				  Serial.println();
				 
		} 
		
		if (OneWire::crc8(addr[i], 7) != addr[i][7])
		{
			Serial.println("ErrCRC");
			return;
		}
		if (addr[i][0] != 0x28)
		{
			Serial.println("notDS18B20");
		}
		*/
	}
}


void get_temp(float *arr)
{
	for (byte i = 0; i < COUNT_TEMP_SENS; i++)
	{
		while (ds.reset() != 1) {}
		ds.select(addr[i]);
		ds.write(0x44);
	}

	delay(850);

	for (byte i = 0; i < COUNT_TEMP_SENS; i++)
	{
		while (ds.reset() != 1) {}
		ds.select(addr[i]);
		ds.write(0xBE);

		data[0] = ds.read();
		data[1] = ds.read();

		int16_t raw = ((data[1] << 8) | data[0]);
		float cel = (float)raw / 16;

		arr[i] = cel;
	}
}
