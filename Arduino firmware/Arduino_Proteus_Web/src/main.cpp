#include <main.h>

LiquidCrystal_I2C lcd(I2C_LCD1_ADDRESS, 20, 4);
LiquidCrystal_I2C lcd2(I2C_LCD2_ADDRESS, 20, 4);

PCF8574 expander;

struct sensRanges ranges[NUM_SENSORS - 1]; 

char buff[SIZE_BUFFER];
unsigned long last_connect = 0;

void setup() 
{
	lcd.begin();
	lcd2.begin();

	Serial.begin(9600);

	expander.begin(0x27);

	expander.pinMode(0, OUTPUT);
 	expander.pinMode(1, OUTPUT);
 	expander.pinMode(2, OUTPUT);
	expander.pinMode(3, OUTPUT);
 	expander.pinMode(4, OUTPUT);
 	expander.pinMode(5, OUTPUT);
	expander.pinMode(6, OUTPUT);
 	expander.pinMode(7, OUTPUT);

	init_DS18B20();
	init_curr_sens();
	init_dac();

	ranges[0].low = 3.25;
	ranges[0].high = 55.35;

	ranges[1].low = 5.45;
	ranges[1].high = 65.35;

	ranges[2].low = 7.35;
	ranges[2].high = 70.35;

	ranges[3].low = 18.35;
	ranges[3].high = 75.35;

	ranges[4].low = 2.15;
	ranges[4].high = 11.5;

	ranges[5].low = 2.25;
	ranges[5].high = 20.5;

	ranges[6].low = 0.35;
	ranges[6].high = 3.35;

	ranges[7].low = 0.15;
	ranges[7].high = 0.5;
}


void loop() 
{
	float arr[NUM_SENSORS];

	float (*func_arr[NUM_SENSORS-1/2])(void) = {
		get_pressure,
		get_amper,
		get_volt,
		get_pres_vac,
	};

	get_temp(arr);

	for (size_t i = 0, j = (NUM_SENSORS-1)/2; i < (NUM_SENSORS-1)/2, j < NUM_SENSORS-1; i++, j++)
	{
		arr[j] = func_arr[i]();
	}


	for (size_t i = 0; i < NUM_SENSORS-1; i++)
	{
		if(arr[i] <= ranges[i].low || arr[i] >= ranges[i].high)
		{
			expander.digitalWrite(i,HIGH);
		} else
		{
			expander.digitalWrite(i,LOW);
		}
	}


	char str_val_sens[15];
	int offset = 0;
	byte row = 0;
	
	for (size_t i = 0; i < NUM_SENSORS-1 ; i++)
	{
		dtostrf(arr[i], 6, 2, str_val_sens);	
		if (i < 4)
		{
			lcd.setCursor(0, i);
			lcd.print("Temp" + String(i + 1) + ": ");
			lcd.print(str_val_sens);
			lcd.print(" \xDF" "C");
		}
		else 
		{
			lcd2.setCursor(0, row);
			switch (i)
			{
			case 4:
				lcd2.print("Pressure:");
				lcd2.print(str_val_sens);
				lcd2.print(" kPa");
				break;
			case 5:
				lcd2.print("Current:");
				lcd2.print(str_val_sens);
				lcd2.print(" mA");
				break;
			case 6:
				lcd2.print("Voltage:");
				lcd2.print(str_val_sens);
				lcd2.print(" V");
				break;
			case 7:
				lcd2.print("Vacuum:");
				lcd2.print(str_val_sens);
				lcd2.print(" hPa");
				break;
			default:
				break;
			}
			row++;
		}
		
		offset += snprintf(buff + offset,
							SIZE_BUFFER - offset, "%s\t", 
							str_val_sens);
	}

	dtostrf(poll_dac(), 6, 2, str_val_sens);	
			offset += snprintf(buff + offset,
							SIZE_BUFFER - offset, "!@!%s\t", 
							str_val_sens);

	remove_spaces(buff);
	Serial.println(buff);

	row = 0;
	
	if (millis() - last_connect > INTERVAL_CHECK_VALUES) 
	{
		read_serial();
		last_connect = millis();
	}
	
}

void remove_spaces(char* s) {
	const char* d = s;
	do {
		while (*d == ' ') {
			++d;
		}
	} while (*s++ = *d++);
}