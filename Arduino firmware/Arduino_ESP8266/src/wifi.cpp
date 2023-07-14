#include "wifi.h"
const static char ssid_wifi[] PROGMEM  = "TP-LINK_86E4";
const static char password_wifi[] PROGMEM = "6U3CMRT89S";      

PGM_P const wifi_setting[] PROGMEM = 
{
   ssid_wifi,
   password_wifi
};

WiFiEspClient c;
HttpClient thingsboard_cl = HttpClient(c,"192.168.0.101",8080);

//const int capacity = JSON_OBJECT_SIZE(3);

void init_wifi(SoftwareSerial* espSerial)
{
  int status = WL_IDLE_STATUS;  
    
  WiFi.init(espSerial);

  if (WiFi.status() == WL_NO_SHIELD) 
  {
    Serial.println(F("WiFi shield not present"));
    while (true);
  }

  char ssid[20];
  char pass[20];

  strcpy_P(ssid,(PGM_P)pgm_read_word(&(wifi_setting[0])));
  strcpy_P(pass,(PGM_P)pgm_read_word(&(wifi_setting[1])));

  while (status != WL_CONNECTED) 
  {
    Serial.print(F("Attempting to connect to WPA SSID: "));
    status = WiFi.begin(ssid, pass);
  }

  Serial.println("Connected to the network");

  Serial.print(F("SSID: "));
  Serial.println(WiFi.SSID());

  IPAddress ip = WiFi.localIP();
  Serial.print(F("IP: "));
  Serial.println(ip);
}



void send_data(float tempr)
{
  /*
  WiFiEspClient client;
  
  StaticJsonDocument<capacity> doc;
  String buf;

  doc["temperature"].set(value);
  doc["latitude"].set(latitude);
  doc["longitude"].set(longitude);

  serializeJson(doc, buf);

  if (client.connect("192.168.0.101", 8080))
  {
    Serial.println(F("Connected..."));
    Serial.println(F("Preparing request..."));

    client.println("POST /api/v1/scRD7wCYthVN5i5x3cV4/telemetry HTTP/1.1");
    client.println("Host: 192.168.0.101:8080");
    client.println("Content-Type: application/json");
    client.print("Content-Length: ");
    client.println(buf.length());
    client.println("Connection: keep-alive");
    client.println();
    client.println(buf);

    *lastCon = millis();
    Serial.println(F("Success..."));
  } else 
  {
    Serial.println(F("Connection failed"));
  }
  unsigned long t = millis();
  while (millis() - t < 10000)
  {
      while (client.available() ) 
  {
    char c = client.read();
    Serial.println(c);
  }
  }
  client.stop();
  */

  size_t capacity = JSON_OBJECT_SIZE(1);
  DynamicJsonDocument doc(capacity);

  doc["temperature"] = tempr;
  //doc["latitude"] = 50.02419071;
  //doc["longitude"] = 36.36844507;

  String post_data;
  serializeJson(doc, post_data);

  thingsboard_cl.beginRequest();
  thingsboard_cl.post("/api/v1/TYFdvwR11i9eemn5FAXB/attributes");
  thingsboard_cl.sendHeader("Content-Type", "application/json");
  thingsboard_cl.sendHeader("Content-Length", post_data.length());
  thingsboard_cl.beginBody();
  thingsboard_cl.print(post_data);
  thingsboard_cl.endRequest();

  int statusCode = thingsboard_cl.responseStatusCode();
  Serial.println(statusCode);

  thingsboard_cl.flush();
  thingsboard_cl.stop();

  if(statusCode == 200)
  {
    Serial.println(F("OK..."));
  } 
    else 
  {
    Serial.println(F("Connection failed..."));
  }
}


uint8_t get_stat_led()
{
  uint8_t led_on = -1;
  thingsboard_cl.get("/api/v1/TYFdvwR11i9eemn5FAXB/attributes?clientKeys=led_on");

  int statusCode = thingsboard_cl.responseStatusCode();
  String response = thingsboard_cl.responseBody();

  Serial.print("Status code: ");
  Serial.println(statusCode);
  Serial.print("Response: ");
  Serial.println(response);

  thingsboard_cl.flush();
  thingsboard_cl.stop();

  if(statusCode == 200)
  {
    Serial.println(F("OK..."));
    size_t capacity = 2*JSON_OBJECT_SIZE(1) + 20;
    DynamicJsonDocument doc(capacity);

    deserializeJson(doc, response);

    led_on = doc["client"]["led_on"]; 
    Serial.println(led_on);
  } 
    else 
  {
    Serial.println(F("Connection failed..."));
  }
  return led_on;
}