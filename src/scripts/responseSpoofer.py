import requests, json
import random
import string

def random_string(x):
    letters = string.ascii_letters
    return ''.join(random.choice(letters) for i in range(x))

def get_user_list():

    url_base = 'http://localhost:8080'
    end_point = '/user/all'
    body = {'username':'jakeocinco@me.com','password':'password'}
    headers = {'Content-Type': 'application/json'}
    # headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWtlb2NpbmNvQG1lLmNvbSIsImV4cCI6MTYxNjYzOTM5NSwiaWF0IjoxNjE2NTUyOTk1fQ.aWcWZ_wGk1slky4l4z8hxN73kd0YmCidi1kft3xBSJrYbPLjClaEddvnv8sJ0PisC7HqvNADxZzoM8bUp-xA8Q'}



    response = requests.get(url_base + end_point, headers=headers)
    print(response.json())

def create_user_if_not_exist(username, password):
    auth = auth_user(username, password)

    if 'jwt' not in auth.keys():
        # profile_id = create_profile(username)
        id = create_profile(username)
        create_user(username, password, id)
        auth = auth_user(username, password)
    return auth

def create_profile(username):
    url_base = 'http://localhost:8080'
    end_point = '/profile'
    body = json.dumps({"username":username})
    headers = {'Content-Type': 'application/json'}

    response = requests.post(url_base + end_point, headers=headers, data = body)
    return response.json()['id']

def create_user(username, password, profile_id):
    url_base = 'http://localhost:8080'
    end_point = '/user'
    body = json.dumps({"username":username, "password":password, "profileId":profile_id})
    headers = {'Content-Type': 'application/json'}


    response = requests.post(url_base + end_point, headers=headers, data = body)

def auth_user(username, password):

    url_base = 'http://localhost:8080'
    end_point = '/authenticate'
    body = json.dumps({"username":username,"password":password})
    headers = {'Content-Type': 'application/json', 'Secret':'SECRET'}
    # headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWtlb2NpbmNvQG1lLmNvbSIsImV4cCI6MTYxNjYzOTM5NSwiaWF0IjoxNjE2NTUyOTk1fQ.aWcWZ_wGk1slky4l4z8hxN73kd0YmCidi1kft3xBSJrYbPLjClaEddvnv8sJ0PisC7HqvNADxZzoM8bUp-xA8Q'}



    response = requests.post(url_base + end_point, headers=headers, data = body)
    return response.json()

def join_course(profileId, class_code, jwt):
    url_base = 'http://localhost:8080'
    end_point = '/profile/join'
    body = json.dumps({"profileId":profileId, "courseId":class_code})
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt}

    response = requests.post(url_base + end_point, headers=headers, data = body)

def get_course(courseId, jwt):
    url_base = 'http://localhost:8080'
    end_point = '/course'
    body = json.dumps({"ids":[courseId]})
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt}

    response = requests.put(url_base + end_point, headers=headers, data = body)
    return response.json()

def get_session(sessionId,jwt):
    url_base = 'http://localhost:8080'
    end_point = '/course/session'
    body = json.dumps({"sessionIds":[sessionId]})
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt}

    response = requests.put(url_base + end_point, headers=headers, data = body)
    return response.json()

def get_promptlet(promptletId,jwt):
    url_base = 'http://localhost:8080'
    end_point = '/course/session/promptlet'
    body = json.dumps({"ids":[promptletId]})
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt}

    response = requests.put(url_base + end_point, headers=headers, data = body)
    return response.json()

def answer_multi(promptletId, profileId, responses,jwt):
    url_base = 'http://localhost:8080'
    end_point = '/course/session/promptlet/answer'
    body = json.dumps({"promptletId":promptletId,"profileId":profileId,"response":responses})
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt}

    response = requests.post(url_base + end_point, headers=headers, data = body)
    # return response.json()

# auth_user()
# create_user_if_not_exist("1", "password")
# create_user_if_not_exist("jakeocinco@me.com", "password")

# username = random_string(15)

# id = create_profile(username)
# create_user(username, password, id)

# print(create_user_if_not_exist(username, password))

password = "password"
class_id = "6051016aa2ef9f3055c4bc5e"
activeSessionId = "" # Leave alone
promptletIds = [] # Leave alone
answerPool = [] # Leave alone
for xx in range(20)[0:1]:
    print("--")
    auth = create_user_if_not_exist(str(xx) + "@a.com", password)
    jwt = auth['jwt']
    profile_id = auth['userResponse']['profileId']
    join_course(profile_id,class_id , jwt)
    
    activeSessionId = get_course(class_id, jwt)[0]['activeSessionId']
    promptletIds = get_session(activeSessionId, jwt)[0]['promptletIds']
    promptlet = get_promptlet(promptletIds[0], jwt)[0]
    answerPool = promptlet['answerPool']
    # answer_multi(promptletIds[0], profile_id, ['3'], jwt)
    print(promptlet)
    # {{api}}/profile/join

print(activeSessionId)
print(promptletIds)
print(answerPool)

for xx in range(20):
    auth = create_user_if_not_exist(str(xx) + "@a.com", password)
    jwt = auth['jwt']
    profile_id = auth['userResponse']['profileId']
    # join_course(profile_id,class_id , jwt)
    print(profile_id)
    # activeSessionId = get_course(class_id, jwt)[0]['activeSessionId']
    # promptletIds = get_session(activeSessionId, jwt)[0]['promptletIds']
    # promptlet = get_promptlet(promptletIds[0], jwt)[0]
    # answerPool = promptlet['answerPool']
    answer_multi(promptletIds[0], profile_id, [answerPool[3]], jwt)
    # print(promptlet)
    # {{api}}/profile/join