#include <Adafruit_MCP4725.h>

#define LSB 0.001220703125

#define POLLING_ANALOG_PIN A3

void init_dac();
void read_serial();
void set_out_volt(float val);
float poll_dac();