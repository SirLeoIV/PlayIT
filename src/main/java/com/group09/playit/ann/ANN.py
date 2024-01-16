import numpy as np
import os
import sys
import tensorflow as tf
from keras import *

os.environ["KERAS_BACKEND"] = "tensorflow"
import keras
import time

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
tf.keras.utils.disable_interactive_logging()


def predict(input_layer):
    # print("input_layer: ", input_layer)

    # print("loading model")
    startTime = time.time()
    # print("loading model")
    dir = os.getcwd()
    model = keras.models.load_model(dir + "/src/main/java/com/group09/playit/ann/ANN-3Layers.keras")
    # print("model loaded")
    endTime = time.time()
    # print("model loaded in ", endTime - startTime, " seconds")

    # print("predicting")
    prediction = model.predict(input_layer)
    print(prediction[0][0])


if __name__ == "__main__":
    input_layer = np.zeros(294)
    for i in range(0, 294):
        try:
            input_layer[i] = float(sys.argv[i+1])
        except:
            input_layer[i] = 0
    input_layer = input_layer.reshape(1, 294)
    predict(input_layer)