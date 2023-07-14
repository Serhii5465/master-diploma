#include "main.h"

#ifndef HAVE_HWSERIAL1
#include "SoftwareSerial.h"
    SoftwareSerial ESP8266(RX, TX);
#endif

unsigned long last_con = 0;           
unsigned long last_check_led = 0;


void setup()
{
  Serial.begin(115200);
  while (!Serial) {}

  ESP8266.begin(9600);
  init_wifi(&ESP8266);

  pinMode(PIN_CAUTION_LED,OUTPUT);
  pinMode(PIN_BLINK_LED,OUTPUT);
}


void loop()
{  
  
  if (millis() - last_con > postInterval) 
  {
    float temperature = get_temp();

    check_val_tempr(temperature);
    send_data(temperature);
    
    last_con = millis();
  }

  if(millis() - last_check_led > checkLEDInterval)
  {
    check_val_led(get_stat_led());
    last_check_led = millis();
  }
}


void check_val_tempr(float data)
{
    if(data < LOW_VAL_DS18B20 || data > HIGH_VAL_DS18B20)
    {
        digitalWrite(PIN_CAUTION_LED,HIGH);
    } 
      else 
    {
        digitalWrite(PIN_CAUTION_LED,LOW);
    }
}


void check_val_led(uint8_t stat)
{
  switch (stat)
  {
  case -1:
    Serial.println(F("LED check error..."));
    break;
  case 0:
    digitalWrite(PIN_BLINK_LED,LOW);
    break;
  case 1:
    digitalWrite(PIN_BLINK_LED,HIGH);
    break;
  default:
    break;
  }
}