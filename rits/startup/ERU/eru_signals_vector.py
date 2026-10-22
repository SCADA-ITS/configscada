#!/usr/bin/python
# -*- coding: utf-8 -*-

import struct
import time
import sys
import os
import json
import pandas as pd

if __name__ == '__main__':
    
    if len(sys.argv) < 3:
        print("Usage:")
        print("  arg1 = File Name csv ")
        print("  arg3 = File Name signal csv ")
        print("  arg3 = Project ")
        exit()
        
    csv_name = sys.argv[1];
    csv_signal_name = sys.argv[2];
    fileName= sys.argv[3];
    
    dirname = os.path.dirname(__file__)
    font = open(os.path.join(dirname + "/" + fileName, csv_name + ".csv"));
    
    font_signal = open(os.path.join(dirname + "/" + fileName, csv_signal_name + ".csv"));
 
    
    data = pd.read_csv (font, sep=";",header = 0,skip_blank_lines=True)
    print(data.head)
    
    data_signal = pd.read_csv (font_signal, sep=";",header = 0,skip_blank_lines=True)
    print(data_signal.head)
    
    destination = dirname + "/" + csv_name + ".json"
    
    data.to_json(destination,orient = "records",date_format = "epoch", double_precision = 10, force_ascii = True, date_unit = "ms")
    
    destination_signal = dirname + "/" + csv_signal_name + ".json"
    
    data_signal.to_json(destination_signal,orient = "records",date_format = "epoch", double_precision = 10, force_ascii = True, date_unit = "ms")
    
    
    source = open(destination);
    f_original = source.read()
    source.close()
    
    source_signal = open(destination_signal);
    f_original_signal = source_signal.read()
    source_signal.close()
    
   
    parsed = json.loads(f_original)
    f_final ={};
    f_final[csv_name] = parsed
    
    parsed_signal = json.loads(f_original_signal)
    
    f_final_signal ={};
    f_final_signal = parsed_signal
    
    signal_object = {}; 
    signal_object_vec = []
    signalStr = "";
    
    index = 0;
    for element in f_final[csv_name]:
        for elementSig in f_final_signal:
            if "root_device" in elementSig:
                if elementSig["root_device"] == element["description"]:
                    if(signalStr != elementSig["signal"]):
                        signal_object[elementSig["signal"]] = [];
                        signal_object[elementSig["signal"]].append(elementSig);
                        signalStr = elementSig["signal"];
                    else :
                        signal_object[elementSig["signal"]].append(elementSig);  
                            
                    if "root_device" in elementSig:
                        del elementSig["root_device"]
                        
                    if "signal" in elementSig:
                        del elementSig["signal"]
                    
                    element.update(signal_object)
    
    resultado=json.dumps(f_final, indent=4)       
    
    with open(destination, "w") as f:
        f.write(resultado)
        f.close()
    
  

        

    
    
    

