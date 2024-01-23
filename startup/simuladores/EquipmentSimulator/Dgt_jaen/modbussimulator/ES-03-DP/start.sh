#!/bin/bash

nohup /usr/bin/python3 /home/admin/app/modbussimulator/ES-03-DP/SimulatorCommands.py > salida_commands.log 2>&1 &
nohup /usr/bin/python3 /home/admin/app/modbussimulator/ES-03-DP/SimulatorMeasures.py > salida_measures.log 2>&1 &
nohup /usr/bin/python3 /home/admin/app/modbussimulator/ES-03-DP/SimulatorTrafficMeasures.py > salida_measures.log 2>&1 &
