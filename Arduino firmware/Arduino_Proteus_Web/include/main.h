#include <Arduino.h>
#include "ds18b20.h"
#include "MPX4115.h"
#include "volt_sensor.h"
#include "vacuum_sensor.h"
#include "current_sens.h"
#include <LiquidCrystal_I2C.h>
#include <mcp4725.h>
#include <Wire.h>    
#include "PCF8574.h"

#define NUM_SENSORS 9

#define SIZE_BUFFER 170

#define INTERVAL_CHECK_VALUES 2500

#define I2C_LCD1_ADDRESS 0x20
#define I2C_LCD2_ADDRESS 0x21


struct sensRanges
{
    float low;
    float high;
};


void remove_spaces(char* s);