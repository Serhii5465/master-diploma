#include "ds18b20.h"
#include "wifi.h"

#define RX 10
#define TX 11

#define PIN_BLINK_LED 3

#define postInterval 2000
#define checkLEDInterval 4000

void preparePost(float value);
void check_val_tempr(float data);
void check_val_led(uint8_t stat);