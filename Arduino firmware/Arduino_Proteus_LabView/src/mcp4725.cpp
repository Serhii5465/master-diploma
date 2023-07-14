#include <mcp4725.h>

Adafruit_MCP4725 dac;

uint8_t last_stat_led = 0;
float last_val_vol = 0.0;

void init_dac()
{
    dac.begin(0x61);
}


void read_serial()
{
    char buff[10];
    size_t size = 0;

    while (Serial.available() > 0)
    {
        size = Serial.readBytesUntil('\r\n',buff,sizeof(buff) - 1);
        buff[size] = '\0';
    }

    set_out_volt(atof(buff));
}


void set_out_volt(float val)
{
    if(val != last_val_vol)
    {
        last_val_vol = val;
        uint16_t data = (val - LSB) / LSB;
        dac.setVoltage(round(data),false);
    }
}