# !python --version
import tensorflow as tf
import pandas as pd
# print(tf.__version__)
from sklearn.model_selection import train_test_split
from keras import *
from keras.layers import Dense
import pandas as pd
import numpy as np
import os


os.environ["KERAS_BACKEND"] = "tensorflow"
import keras

data = pd.read_csv("/Users/apple/Desktop/PlayIT/src/main/java/com/group09/playit/ann/training_data/test-training-data2.csv")
Y = data['Y']
X = data.drop(['Y'],axis=1)
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.16, random_state=10)

# Model
num_classes = 1

model = Sequential([
    Dense(units=64, input_shape= (294,), activation = 'relu'),
    Dense(units=64, activation = 'relu'),
    Dense(units=num_classes, activation='relu'),
])


model.summary()

model.compile(
    loss=keras.losses.MeanAbsoluteError(),
    optimizer=keras.optimizers.Adam(),
    metrics=["mse"],
)

history = model.fit(X_train, Y_train, epochs=10, validation_split=0.2)

test_scores = model.evaluate(X_test, Y_test, verbose=2)
print("Test loss:", test_scores[0])
print("Test accuracy:", test_scores[1])


