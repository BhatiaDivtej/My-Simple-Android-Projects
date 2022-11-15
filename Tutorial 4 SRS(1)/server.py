import flask
import sqlite3
from flask import request

# Task 2.3 
app = flask.Flask(__name__)
#app.config["DEBUG"] = True # Enable debug mode to enable hot-reloader.
@app.route('/tutorial')


def tutorial():

    students = []

    # Task 4.2
    con = sqlite3.connect('my-db.db') #connection to database

    cursor = con.execute("SELECT NAME FROM STUDENT;")

    for row in cursor:
        students.append(row[0])

    students.append("Divtej Singh Bhatia")
    con.close()

    

    # Task 4.3
    outdata = {
        "course_code": "COMP3330",
        "course_name": "Interactive Mobile Application Design and Programming",
        "teachers": ["Dr. T.W. Chim", "Mr. C.K. Lai", "Mr. X. Wang"], 
        "students": students
    }
    return outdata

# adds host="0.0.0.0" to make the server publicly available 
app.run(host="0.0.0.0")
