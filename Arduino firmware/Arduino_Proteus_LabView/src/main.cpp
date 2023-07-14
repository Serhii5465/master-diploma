#include <main.h>

LiquidCrystal_I2C lcd(I2C_LCD1_ADDRESS, 20, 4);
LiquidCrystal_I2C lcd2(I2C_LCD2_ADDRESS, 20, 4);

char buff[SIZE_BUFFER];
unsigned long last_connect = 0;

void setup() 
{
	lcd.begin();
	lcd2.begin();
	Serial.begin(9600);
	init_DS18B20();
	init_curr_sens();
	init_dac();
}


void loop() 
{
	float arr[NUM_SENSORS];

	float (*func_arr[NUM_SENSORS / 2])(void) = {
		get_pressure,
		get_amper,
		get_volt,
		get_pres_vac
	};

	get_temp(arr);

	for (size_t i = 0, j = NUM_SENSORS / 2; 
					i < NUM_SENSORS / 2, j < NUM_SENSORS; i++, j++){
		arr[j] = func_arr[i]();
	}

	char str_val_sens[15];
	int offset = 0;
	byte row = 0;
	
	for (size_t i = 0; i < NUM_SENSORS ; i++)
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
				lcd2.print("Press:");
				lcd2.print(str_val_sens);
				lcd2.print(" kPa");
				break;
			case 5:
				lcd2.print("Curr:");
				lcd2.print(str_val_sens);
				lcd2.print(" mA");
				break;
			case 6:
				lcd2.print("Volt:");
				lcd2.print(str_val_sens);
				lcd2.print(" V");
				break;
			case 7:
				lcd2.print("Vac:");
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