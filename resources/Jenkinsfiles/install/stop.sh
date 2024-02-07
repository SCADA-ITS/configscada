#!/bin/bash
pkill --signal SIGTERM -f openits 
pkill --signal SIGTERM -f server-launcher
