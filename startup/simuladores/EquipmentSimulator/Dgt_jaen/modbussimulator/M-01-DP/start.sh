#!/bin/bash

nohup /usr/bin/python3 /home/admin/app/modbussimulator/M-01-DP/SimulatorCommands.py > salida_commands.log 2>&1 &
nohup /usr/bin/python3 /home/admin/app/modbussimulator/M-01-DP/SimulatorMeasures.py > salida_measures.log 2>&1 &
