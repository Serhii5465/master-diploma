#include <Arduino.h>
#include "ds18b20.h"
#include "MPX4115.h"
#include "volt_sensor.h"
#include "vacuum_sensor.h"
#include "current_sens.h"
#include <LiquidCrystal_I2C.h>
#include <mcp4725.h>

#define NUM_SENSORS 8

#define SIZE_BUFFER 150

#define INTERVAL_CHECK_VALUES 3000

#define I2C_LCD1_ADDRESS 0x20
#define I2C_LCD2_ADDRESS 0x21

void remove_spaces(char* s);