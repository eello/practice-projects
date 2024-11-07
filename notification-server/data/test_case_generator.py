import random
from datetime import datetime, timedelta
# import csv
import json

def random_yeardate():
    # 시작 날짜와 종료 날짜 설정 (원하는 범위 내에서 날짜를 생성할 수 있도록)
    start_date = datetime(2024, 6, 1)
    end_date = datetime(2024, 11, 5)

    # 시작 날짜와 종료 날짜 사이에서 랜덤한 날짜 생성
    random_days = random.randint(0, (end_date - start_date).days)
    random_date = start_date + timedelta(days=random_days)

    return random_date.strftime("%Y-%m-%dT00:00:00")

def random_data(i):
    random_user_id = random.randrange(1, 101) # userId = 1 ~ 100
    random_request_at = random_yeardate()
    title = f"🙌 title {i}"
    body = f"📝 body {i}"

    return {
        'user_id' : random_user_id,
        'request_at' : random_request_at,
        'title' : title,
        'body' : body
    }


repeat = 0
test_data = []
while repeat <= 5000:
    test_data.append(random_data(repeat))
    repeat += 1

### Workbench import error ###
# CSV 파일로 데이터 저장
# with open('./test_data.csv', mode = 'w', newline = '', encoding = 'utf-8') as file:
#     writer = csv.writer(file)
#     writer.writerow(['user_id', 'request_at', 'title', 'body']) # 열 이름 쓰기
#     for row in test_data:
#         writer.writerow(row) # 각 행 쓰기

# JSON 파일로 저장
with open('./test_data.json', mode = 'w', encoding = 'utf-8') as file:
    file.write(json.dumps(test_data, ensure_ascii = False, indent = 2))