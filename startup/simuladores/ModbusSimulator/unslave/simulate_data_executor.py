#!/usr/bin/python
import time
import datetime
import os 
from subprocess import call
dirname = os.path.dirname(__file__)

while True:
    starttime = time.time()
    now = datetime.datetime.now()
    print(now.year, now.month, now.day, now.hour, now.minute, now.second)
    if now.second == 0 :
        os.system('python3.6 simulate_data.py')      
    time.sleep(1.0 - ((time.time() - starttime) % 1.0))
    
    

