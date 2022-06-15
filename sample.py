import numpy as np
from google.colab import files
from tensorflow.keras.preprocessing import image

uploaded = files.upload()

for fn in uploaded.keys() :
  path = '/content/' + fn
  img = image.load_img(path, target_size=(300,300))
  x = image.img_to_array(img) / 255.0

  print(x.shape)

  x = np.expand_dims(x, axis = 0)

  print(x.shape)

  images = np.vstack( [x] )
  classes = model.predict( images, batch_size = 10 )
  
  print(classes)

  if classes[0] > 0.5 :
    print(fn + " is a human")
  else :
    print(fn + " is a horse")



---

# 디렉토리 정보와 파일을 알려주면, 해당 디렉토리에
# 파일을 저장하는 함수
def save_uploaded_file(directory, file) :
    # 1.디렉토리가 있는지 확인하여, 없으면 디렉토리부터만든다.
    if not os.path.exists(directory) :
        os.makedirs(directory)
    # 2. 디렉토리가 있으니, 파일을 저장.
    with open(os.path.join(directory, file.name), 'wb') as f :
        f.write(file.getbuffer())
    return st.success("Saved file : {} in {}".format(file.name, directory))



--------


# 문장 클리닝하는 파이프라이닝 함수

import string
import nltk
nltk.download('stopwords')
from nltk.corpus import stopwords
my_stopwords = stopwords.words('english')

def message_cleaning(sentence) :
  # 1. 구두점 제거
  Test_punc_removed = [char for char in sentence if char not in string.punctuation ]
  # 2. 각 글자들을 하나의 문자열로 합친다.
  Test_punc_removed_join = ''.join(Test_punc_removed)
  # 3. 문자열에 불용어가 포함되어있는지 확인해서, 불용어 제거한다.
  Test_punc_removed_join_clean = [word for word in Test_punc_removed_join.split() if word.lower() not in my_stopwords ]
  # 4. 결과로 남은 단어들만 리턴한다.
  return Test_punc_removed_join_clean




# hc 용
plt.scatter(X.values[y_pred == 0, 0], X.values[y_pred == 0, 1], s = 100, c = 'red', label = 'Cluster 1')
plt.scatter(X.values[y_pred == 1, 0], X.values[y_pred == 1, 1], s = 100, c = 'blue', label = 'Cluster 2')
plt.scatter(X.values[y_pred == 2, 0], X.values[y_pred == 2, 1], s = 100, c = 'green', label = 'Cluster 3')
plt.scatter(X.values[y_pred == 3, 0], X.values[y_pred == 3, 1], s = 100, c = 'cyan', label = 'Cluster 4')
plt.scatter(X.values[y_pred == 4, 0], X.values[y_pred == 4, 1], s = 100, c = 'magenta', label = 'Cluster 5')
plt.title('Clusters of customers')
plt.xlabel('Annual Income (k$)')
plt.ylabel('Spending Score (1-100)')
plt.legend()
plt.show()

# k means 용
plt.figure(figsize=[12,8])
plt.scatter(X.values[y_pred == 0, 0], X.values[y_pred == 0, 1], s = 100, c = 'red', label = 'Cluster 1')
plt.scatter(X.values[y_pred == 1, 0], X.values[y_pred == 1, 1], s = 100, c = 'blue', label = 'Cluster 2')
plt.scatter(X.values[y_pred == 2, 0], X.values[y_pred == 2, 1], s = 100, c = 'green', label = 'Cluster 3')
plt.scatter(X.values[y_pred == 3, 0], X.values[y_pred == 3, 1], s = 100, c = 'cyan', label = 'Cluster 4')
plt.scatter(X.values[y_pred == 4, 0], X.values[y_pred == 4, 1], s = 100, c = 'magenta', label = 'Cluster 5')
plt.scatter(kmeans.cluster_centers_[:, 0], kmeans.cluster_centers_[:, 1], s = 300, c = 'yellow', label = 'Centroids')
plt.title('Clusters of customers')
plt.xlabel('Annual Income (k$)')
plt.ylabel('Spending Score (1-100)')
plt.legend()
plt.show()




