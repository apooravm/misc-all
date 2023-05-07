#include <DHT.h>
DHT dht(4, DHT11);

void setup() {
	Serial.begin(9600);
	dht.begin();
}

void loop() {
	Serial.println("Temperature (%): ");
	Serial.println(dht.readTemperature());
	Serial.println("Humidity (%): ");
	Serial.println(dht.readHumidity());
	delay(3000);
}
