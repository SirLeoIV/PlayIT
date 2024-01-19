import keras
import numpy as np
import os
import tensorflow as tf
from flask import Flask
from keras import *

# Disable Tensorflow logging
tf.keras.utils.disable_interactive_logging()

# Load model from file
dir = os.getcwd()
model = keras.models.load_model(dir + "/ANN-3Layers-Sigmoid-Leaky-2Dropout-40.keras")

# Predict function: input_layer is a numpy array
def predict(input_layer):
    prediction = model.predict(input_layer)
    print(prediction[0][0])
    return prediction[0][0]

# Flask app
app = Flask(__name__)
app.config['MAX_COOKIE_SIZE'] = 10 * 1024 * 1024
app.config['MAX_CONTENT_LENGTH'] = 10 * 1024 * 1024

# Health check
@app.route("/")
def health():
    return "Ok"

# Predict endpoint (POST)
@app.route("/predict/<input_layer>", methods=['GET'])
def predict_get(input_layer):
    input_layer = np.fromstring(input_layer, dtype=float, sep=',').reshape(1, 294)
    prediction = predict(input_layer).astype('str')
    return prediction

# run the app on localhost on port 8080
if __name__ == "__main__":
    from waitress import serve
    serve(app, host="0.0.0.0", port=8080)
