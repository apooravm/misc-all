import Adafruit_DHT
DHT11 = Adafruit_DHT.DHT11

while True:
	try:
		temp, humid = Adafruit_DHT.read_retry(DHT11, 4)
		print(f"Temperature: {temp}°C")
		print(f"Humidity: {humid}%")
	except:
		break