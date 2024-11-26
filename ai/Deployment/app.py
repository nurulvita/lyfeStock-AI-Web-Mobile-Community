from flask import Flask, request, jsonify
from flask_cors import CORS
import tensorflow as tf
import numpy as np
from PIL import Image
import os
import logging

app = Flask(__name__)
CORS(app)  # Enable CORS

# Enable logging for debugging
logging.basicConfig(level=logging.DEBUG)

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

if __name__ == '__main__':
    app.run(debug=True)
