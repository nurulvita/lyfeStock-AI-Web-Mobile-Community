from flask import Flask, request, jsonify
from flask_cors import CORS
import tensorflow as tf
import numpy as np
from PIL import Image
import os
import logging
import requests
import pandas as pd
from prometheus_flask_exporter import PrometheusMetrics

app = Flask(__name__)
CORS(app)  # Enable CORS

# Enable logging for debugging
logging.basicConfig(level=logging.DEBUG)

# Initialize Prometheus metrics
metrics = PrometheusMetrics(app)

# Load model architecture and weights
try:
    model = tf.keras.models.load_model('model/sequential-Chicken Disease-92per.h5')
    model.load_weights('model/sequential-Chicken Disease-weights.h5')
    logging.info("Model and weights loaded successfully.")
except Exception as e:
    logging.error(f"Error loading model or weights: {e}")
    raise

# Class names (adjust according to your labels)
class_names = ['Coccidiosis', 'Salmonella', 'New Castle Disease', 'Healthy']

# Image preprocessing function
def preprocess_image(image_path):
    try:
        # Open and process the image
        img = Image.open(image_path).convert('RGB')
        logging.debug(f"Image opened: {image_path}, size: {img.size}")
        img = img.resize((224, 224))  # Resize image to fit model input size
        img_array = np.array(img) / 255.0  # Normalize image
        img_array = np.expand_dims(img_array, axis=0)  # Add batch dimension
        return img_array
    except Exception as e:
        logging.error(f"Error processing image: {e}")
        return None

# Endpoint untuk prediksi penyakit ayam
@app.route('/predict', methods=['POST'])
def predict():
    if 'file' not in request.files:
        return jsonify({"error": "No file uploaded"}), 400
    
    file = request.files['file']
    
    # Validate file extension and size (max 5 MB)
    if not file.filename.lower().endswith(('.png', '.jpg', '.jpeg')): 
        return jsonify({"error": "Invalid file format. Only PNG, JPG, or JPEG allowed."}), 400
    
    if file.mimetype not in ['image/jpeg', 'image/png']:
        return jsonify({"error": "File is not a valid image."}), 400
    
    if file.content_length > 5 * 1024 * 1024:  # Limit file size to 5MB
        return jsonify({"error": "File size exceeds limit (5MB)."}), 400

    # Save the uploaded file
    upload_folder = 'temp'
    os.makedirs(upload_folder, exist_ok=True)  # Create folder if it doesn't exist
    file_path = os.path.join(upload_folder, file.filename)
    try:
        file.save(file_path)  # Save the file
        logging.info(f"File saved: {file_path}")
    except Exception as e:
        logging.error(f"Failed to save file: {e}")
        return jsonify({"error": f"Failed to save file: {e}"}), 500

    # Preprocess the image and make predictions
    img_array = preprocess_image(file_path)
    if img_array is None:
        return jsonify({"error": "Failed to process the image."}), 500

    try:
        # Make prediction
        predictions = model.predict(img_array)
        class_idx = np.argmax(predictions)  # Get the index of the highest probability
        predicted_class = class_names[class_idx]
        confidence_score = float(predictions[0][class_idx])
        logging.debug(f"Predicted class: {predicted_class}, confidence: {confidence_score}")
    except Exception as e:
        logging.error(f"Prediction failed: {e}")
        return jsonify({"error": f"Prediction failed: {e}"}), 500

    # Clean up temporary file
    os.remove(file_path)
    logging.info(f"Temporary file {file_path} removed.")

    return jsonify({
        "class": predicted_class,
        "confidence": confidence_score  # Return confidence score
    })

# Predefined city coordinates for kecamatan in Batam and major cities in Indonesia
city_coordinates = {
    # Kecamatan di Batam
    'Barelang': {'lat': 1.1150, 'lon': 104.0200},
    'Sekupang': {'lat': 1.1076, 'lon': 103.9265},
    'Batam Center': {'lat': 1.1322, 'lon': 104.0244},
    'Nongsa': {'lat': 1.1532, 'lon': 104.0687},
    'Tanjung Uncang': {'lat': 1.0616, 'lon': 103.9157},
    # Kota besar di Indonesia
    'Jakarta': {'lat': -6.2088, 'lon': 106.8456},
    'Surabaya': {'lat': -7.2575, 'lon': 112.7521},
    'Bandung': {'lat': -6.9175, 'lon': 107.6191},
    'Yogyakarta': {'lat': -7.7975, 'lon': 110.3708},
    'Samarinda': {'lat': -0.5022, 'lon': 117.1536},
}

# Function to get weather data from OpenWeather API (5 Day / 3 Hour Forecast)
def get_weather_data(cities, api_key):
    base_url = "https://api.openweathermap.org/data/2.5/forecast"
    weather_data = []
    
    for city in cities:
        params = {
            'lat': city['lat'],
            'lon': city['lon'],
            'appid': api_key,  # Your OpenWeather API key
            'units': 'metric',  # Using metric units (Celsius)
        }
        
        try:
            response = requests.get(base_url, params=params)
            if response.status_code == 200:
                data = response.json()
                for forecast in data['list']:
                    weather_data.append({
                        'city': city['name'],
                        'timestamp': forecast['dt_txt'],  # Timestamp of the forecast
                        'temperature': forecast['main']['temp'],  # Temperature in Celsius
                        'humidity': forecast['main']['humidity'],  # Humidity percentage
                        'windspeed': forecast['wind']['speed'],  # Wind speed in m/s
                        'description': forecast['weather'][0]['description'],  # Weather description
                    })
            else:
                logging.warning(f"Failed to fetch data for {city['name']}. Status code: {response.status_code}")
        except Exception as e:
            logging.error(f"Error fetching data for {city['name']}: {e}")
    
    return pd.DataFrame(weather_data)

# Endpoint to get weather data (5 Day / 3 Hour Forecast)
@app.route('/weather', methods=['POST'])
def weather():
    # Get the list of cities from the request body
    data = request.get_json()
    cities = data.get('cities')

    if not cities:
        return jsonify({"error": "Cities are required"}), 400

    # Filter out cities that are not in the predefined coordinates
    valid_cities = [city for city in cities if city in city_coordinates]

    if not valid_cities:
        return jsonify({"error": "None of the provided cities were found in the list."}), 400

    # Prepare the list of cities with their coordinates
    city_data_list = [{'name': city, **city_coordinates[city]} for city in valid_cities]
    
    api_key = '5420b6058393d8f1bf8de220869f344b'  # Replace with your OpenWeather API key
    weather_df = get_weather_data(city_data_list, api_key)
    logging.debug(f"Weather data: {weather_df}")

    # Return the weather data in JSON format
    return weather_df.to_json(orient='records')

# Endpoint monitoring untuk Prometheus
@app.route('/metrics')
def metrics_endpoint():
    return metrics.export_metrics()

@app.route('/location-type', methods=['POST'])
def location_type():
    data = request.get_json()
    cities = data.get('cities')

    if not cities or not isinstance(cities, list):
        return jsonify({"error": "Cities are required and should be a list."}), 400

    # Separate the cities into "Kecamatan Batam" and "Kota Indonesia"
    kecamatan_batam = [
        city for city in cities
        if city in city_coordinates and city_coordinates[city]['lat'] >= 1.0
    ]
    
    kota_indonesia = [
        city for city in cities
        if city in city_coordinates and city_coordinates[city]['lat'] < 1.0
    ]

    response = {
        "kecamatan_batam": kecamatan_batam,
        "kota_indonesia": kota_indonesia
    }

    return jsonify({
        "message": "Location types identified successfully.",
        "data": response
    })

if __name__ == '__main__':
    app.run(debug=True)
