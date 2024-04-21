# Introduction
This section highlights some constructive software optimization that can be done for the mobile application software.

## Sensors:
•	Sensor Integration: The app seamlessly interacts with both the DHT11 sensor for room tem-perature and humidity and the TDS sensor for measuring nutrient concentration. Thus by us-ing optimized code ensures efficient data retrieval from both sensors, minimizing latency and resource consumption.

•	Real-time Updates: Values from the sensors are displayed on the app in real-time and pro-vide immediate insights into the hydroponic environment.

## Interpretive Data Visualization:
•	Value Ranges: Clear indicators categorize sensor values as "good," "high," or "low" based on pre-defined thresholds. This simplifies interpretation and provides actionable information for adjustments based on recommendation that the application gives.

•	Visual Cues: Color-coded displays and intuitive icons visually represent the range of each value, making it easy to identify potential concerns at a glance.


The TDS sensor and DHT11 sensors which was forwarded to the NodeMCU which then pushes it the cloud storage (Firebase). The app then retrieve those data and display them through th UI to the user.

