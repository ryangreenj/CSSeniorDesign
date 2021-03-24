import requests, json
import random
import string

url_base = 'http://localhost:8080'

def random_string(x):
    letters = string.ascii_letters
    return ''.join(random.choice(letters) for i in range(x))

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

def get_profile(profileId, jwt):
    end_point = '/profile'
    body = json.dumps({"id":profileId})
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt}

    response = requests.put(url_base + end_point, headers=headers, data = body)
    return response.json()

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

def answer_multi(activeSessionId, promptletId, profileId, responses,jwt):
    
    end_point = '/course/session/promptlet/answer'
    body = json.dumps({"activeSessionId":activeSessionId,"promptletId":promptletId,"profileId":profileId,"response":responses})
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt}

    response = requests.post(url_base + end_point, headers=headers, data = body)
    # return response.json()


# class_id - id for the class you want to spoof
# promptlet_index - the index of the promptlet you want to spoof (multichoice or single answer multiresponse only)
# class_size - number of accounts to spoof with
# answer_index - the index of the 'answer pool' in which to answer with, omit parameter for random 
def spoof_promptlet_responses(class_id, promptlet_index, class_size, answer_index = -1):
    password = "password" # for new accounts, can be whatever
    
    # this block gathers all the needed class data -- do not change
    temp_jwt = create_user_if_not_exist(str(0) + "@a.com", password)['jwt']
    activeSessionId = get_course(class_id, temp_jwt)[0]['activeSessionId']
    promptletIds = get_session(activeSessionId, temp_jwt)[0]['promptletIds']
    answerPool = get_promptlet(promptletIds[promptlet_index], temp_jwt)[0]['answerPool']

    # this is the index of the answer pool that each student will answer
    if answer_index < 0:
        answer_set = [random.randint(0, len(answerPool) - 1) for _ in range(class_size)]
    else:
        answer_set = [answer_index for _ in range(class_size)]
    
    # Joins the desired class (if neccesary) and submites an answer
    # the usernames just iterate from 0@a.com to wherever it finished
    for xx in range(0,class_size):
        auth = create_user_if_not_exist(str(xx) + "@a.com", password)
        jwt = auth['jwt']
        profile_id = auth['userResponse']['profileId']
        profile = get_profile(profile_id, jwt)

        if class_id not in profile['coursesEnrolled']:
            print('Joining course')
            join_course(profile_id,class_id , jwt)

        answer_multi(activeSessionId, promptletIds[promptlet_index], profile_id, [answerPool[answer_set[xx]]], jwt)

spoof_promptlet_responses("6058acecbbd2fa766e8ae3d0", 2, 20)
spoof_promptlet_responses("6051016aa2ef9f3055c4bc5e", 1, 20)