#include <avr/pgmspace.h>
#include <WiFiEsp.h>
#include <ArduinoJson.h>
#include <ArduinoHttpClient.h>

#ifndef HAVE_HWSERIAL1
    #include "SoftwareSerial.h"
#endif

void init_wifi(SoftwareSerial* espSerial);
void status_wifi();
void send_data(float tempr);
uint8_t get_stat_led();