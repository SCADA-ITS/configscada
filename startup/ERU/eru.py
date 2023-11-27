#!/usr/bin/python
# -*- coding: utf-8 -*-

import struct
import time
import sys
import os
import json
import pandas as pd

if __name__ == '__main__':
    
    if len(sys.argv) < 2:
        print("Usage:")
        print("  arg1 = File Name csv ")
        print("  arg2 = Project ")
        exit()
        
    csv_name = sys.argv[1];
    fileName= sys.argv[2];
    
    dirname = os.path.dirname(__file__)
    font = open(os.path.join(dirname + "/" + fileName, csv_name + ".csv"));
 
    
    data = pd.read_csv (font, sep=";",header = 0,skip_blank_lines=True)
    print(data.head)
    destination = dirname + "/" + csv_name + ".json"
    data.to_json(destination,orient = "records",date_format = "epoch", double_precision = 10, force_ascii = True, date_unit = "ms")
    
 
    
    source = open(destination);
    f_original = source.read()
    source.close()
    
   
    parsed = json.loads(f_original)
    f_final ={};
    f_final[csv_name] = parsed
    resultado=json.dumps(f_final, indent=4)       
    
    with open(destination, "w") as f:
        f.write(resultado)
        f.close()
    
  

        

    
    
    

