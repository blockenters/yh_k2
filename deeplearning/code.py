import matplotlib.pyplot as plt
def plot_history(history) :
  hist = pd.DataFrame(history.history)
  hist['epoch'] = history.epoch

  plt.figure(figsize = (8, 12))

  plt.subplot(2, 1, 1)
  plt.xlabel('Eopoch')
  plt.ylabel('Mean Abs Error [MPG]')
  plt.plot(hist['epoch'], hist['mae'], label = 'Train Error')
  plt.plot(hist['epoch'], hist['val_mae'], label = 'Val Error')
  plt.ylim([0,5])
  plt.legend()

  plt.subplot(2, 1, 2)
  plt.xlabel('Eopoch')
  plt.ylabel('Mean Squared Error [MPG]')
  plt.plot(hist['epoch'], hist['mse'], label = 'Train Error')
  plt.plot(hist['epoch'], hist['val_mse'], label = 'Val Error')
  plt.ylim([0,20])
  plt.legend()

  plt.show()
